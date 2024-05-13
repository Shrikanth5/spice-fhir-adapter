package com.mdtlabs.fhir.authservice.security;

import com.mdtlabs.fhir.authservice.repository.UserRepository;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.RoleRepository;
import com.mdtlabs.fhir.commonservice.common.utils.CustomDateSerializer;
import com.mdtlabs.fhir.commonservice.common.utils.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * The {@code AuthProvider} class implements the Spring Security interface for authentication providers.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    CustomDateSerializer customDateSerializer;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Value("${app.login-count-limit}")
    private int loginCountLimit;

    /**
     * <p>
     * Authenticates a user's credentials and returns a token with their authorities if authenticated.
     * </p>
     *
     * @param authentication An object of type Authentication containing user authentication information.
     * @return A {@link Authentication} object, typically a {@link UsernamePasswordAuthenticationToken}.
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = String.valueOf(authentication.getPrincipal()).toLowerCase();
        String password = String.valueOf(authentication.getCredentials());
        User user = authenticationCheck(username, password);
        Set<Role> roles = user.getRoles();
        Map<String, Role> rolesAsMap = getRoleAsMap(roles);
        boolean isActiveRole = Boolean.FALSE;
        List<Role> rolesList = roleRepository.getAllRoles(Boolean.TRUE);
        for (Role role : rolesList) {
            if (null != rolesAsMap.get(role.getName())) {
                isActiveRole = Boolean.TRUE;
            }
        }
        if (!isActiveRole) {
            Logger.logError(ErrorConstants.ERROR_INVALID_ROLE);
            throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_ROLE);
        }
        boolean isAuthenticated = Boolean.FALSE;
        if (password.equals(user.getPassword())) {
            isAuthenticated = Boolean.TRUE;
        }
        if (null != user.getTimezone()) {
            ModelMapper modelMapper = new ModelMapper();
            customDateSerializer.setUserZoneId(modelMapper.map(user.getTimezone(), Timezone.class).getOffset());
        }
        if (isAuthenticated) {
            Set<GrantedAuthority> authorityList = user.getAuthorities();
            user.setRoles(roles);
            return new UsernamePasswordAuthenticationToken(user, password, authorityList);
        }
        Logger.logError(StringUtil.constructString(ErrorConstants.INFO_USER_PASSWORD_NOT_MATCH, username));
        throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_USER);
    }

    /**
     * <p>
     * Checks whether the user account is valid.
     * </p>
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A {@link User} entity representing the user.
     */
    private User authenticationCheck(String username, String password) {
        if ((isBlank(username)) || (isBlank(password))) {
            throw new BadCredentialsException(ErrorConstants.ERROR_USERNAME_PASSWORD_BLANK);
        }
        User user = userRepository.getUserByUsername(username, Constants.BOOLEAN_TRUE);
        if (null == user) {
            Logger.logError(StringUtil.constructString(ErrorConstants.INFO_USER_NOT_EXIST, username));
            throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_USER);
        }
        if (null == user.getPassword()) {
            Logger.logError(StringUtil.constructString(ErrorConstants.PASSWORD_NOT_EXIST, username));
            throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_USER);
        }

        if (user.getPassword().equals(password)) {
            setUserValues(user, Constants.ZERO);
            userRepository.save(user);
        } else if (updateAccBlockStatus(user)) {
            Logger.logError(StringUtil.constructString(ErrorConstants.ERROR_INVALID_ATTEMPTS));
            throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_ATTEMPTS);
        }

        return user;
    }

    /**
     * <p>
     * Updates the account block status of a user based on their number of invalid login attempts.
     * </p>
     *
     * @param user The user object.
     * @return A boolean value indicating whether the user account is blocked or not.
     */
    private boolean updateAccBlockStatus(User user) {
        int invalidLoginAttempts = user.getInvalidLoginAttempts();
        boolean isBlocked = Boolean.FALSE;
        if (invalidLoginAttempts >= loginCountLimit) {
            Logger.logError(StringUtil.constructString(ErrorConstants.ERROR_INVALID_ATTEMPTS));
            throw new BadCredentialsException(ErrorConstants.ERROR_INVALID_ATTEMPTS);
        }
        if (invalidLoginAttempts >= Constants.ZERO) {
            invalidLoginAttempts++;
            if (invalidLoginAttempts == loginCountLimit) {
                setUserValues(user, invalidLoginAttempts);
                isBlocked = Boolean.TRUE;
            } else {
                setUserValues(user, invalidLoginAttempts);
            }
            userRepository.save(user);
        }
        return isBlocked;
    }

    /**
     * <p>
     * Converts a set of roles into a map with role names as keys and roles as values.
     * </p>
     *
     * @param roles A Set of Role objects.
     * @return A Map with role names as keys and roles as values.
     */
    private Map<String, Role> getRoleAsMap(Set<Role> roles) {
        Map<String, Role> roleAsMap = new HashMap<>();
        roles.forEach(role -> roleAsMap.put(role.getName(), role));
        return roleAsMap;
    }

    /**
     * <p>
     * Checks if the provided authentication class is assignable from the UsernamePasswordAuthenticationToken class.
     * </p>
     *
     * @param authentication The Class object representing the type of authentication object.
     * @return A boolean value indicating whether the given authentication class is supported or not.
     */
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    /**
     * <p>
     * Checks if a given CharSequence is blank or contains only whitespace characters.
     * </p>
     *
     * @param charSequence A CharSequence representing a sequence of characters.
     * @return A boolean value indicating whether the input CharSequence is null, empty, or contains only whitespace characters.
     */
    private boolean isBlank(CharSequence charSequence) {
        int stringLength;
        if ((charSequence == null) || ((stringLength = charSequence.length()) == Constants.ZERO)) {
            return Boolean.TRUE;
        }
        for (int character = Constants.ZERO; character < stringLength; character++) {
            if (!Character.isWhitespace(charSequence.charAt(character))) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * <p>
     * Sets the values of invalid login attempts, blocked status, and blocked date for a user object and logs the blocked date.
     * </p>
     *
     * @param user                 The User object representing the user in the system.
     * @param invalidLoginAttempts The number of times a user has attempted to log in with invalid credentials.
     */
    private void setUserValues(User user, int invalidLoginAttempts) {
        user.setInvalidLoginAttempts(invalidLoginAttempts);
    }
}