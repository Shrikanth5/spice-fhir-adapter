package com.mdtlabs.fhir.fhiruserservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.service.UserTokenService;
import com.mdtlabs.fhir.commonservice.common.utils.DateUtil;
import com.mdtlabs.fhir.commonservice.common.utils.StringUtil;
import com.mdtlabs.fhir.fhiruserservice.mapper.UserMapper;
import com.mdtlabs.fhir.fhiruserservice.repository.UserRepository;
import com.mdtlabs.fhir.fhiruserservice.service.RoleService;
import com.mdtlabs.fhir.fhiruserservice.service.UserApiInteractionService;
import com.mdtlabs.fhir.fhiruserservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * <p>
 * Implementation of the UserService interface.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserTokenService userTokenService;
    private final UserApiInteractionService userApiInteractionService;
    private final RoleService rolesService;
    @Value("${app.forget-password-count-limit}")
    private int forgotPasswordCountLimit;
    @Value("${app.forgot-password-time-limit-in-minutes}")
    private int forgotPasswordTimeLimitInMinutes;
    @Value("${app.page-count}")
    private int gridDisplayValue;

    public UserServiceImpl(
                           UserRepository userRepository,
                           UserMapper userMapper,
                           UserTokenService userTokenService,
                           UserApiInteractionService userApiInteractionService,
                           RoleService rolesService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userTokenService = userTokenService;
        this.userApiInteractionService = userApiInteractionService;
        this.rolesService = rolesService;
    }

    /**
     * {@inheritDoc}
     */
    public User addUser(User user) {
        User updatedUser;
        if (null != userRepository.findByUsernameAndIsDeletedFalse(user.getUsername())) {
            throw new FhirValidation(1009);
        }
        user.setForgetPasswordCount(Constants.ZERO);
        user.setActive(Boolean.TRUE);
        user.setDeleted(Boolean.FALSE);
        user.setRoles(Set.of(rolesService.getRoleById(2L)));
        updatedUser = userRepository.save(user);
        return updatedUser;
    }


    /**
     * {@inheritDoc}
     */
    public User findByUserIdAndIsActiveTrue(Long userId) {

        return userRepository.findByIdAndIsActiveTrue(userId);
    }

    /**
     * {@inheritDoc}
     */
    public int getTotalSize() {
        return userRepository.getUsers(Boolean.TRUE).size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username, Boolean.TRUE);
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - Constants.INT_ONE, gridDisplayValue);
        Page<User> users = userRepository.getUsers(Boolean.TRUE, pageable);
        return Objects.nonNull(users) ? users.toList() : Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    public User updateUser(User user) {
        if (Objects.isNull(user.getId())) {
            throw new DataNotAcceptableException(1016);
        }
        User existingUser = getUserById(user.getId());
        if (!Objects.equals(existingUser.getId(), user.getId())) {
            throw new FhirValidation(1104);
        }
        userMapper.setExistingUser(user, existingUser);
        return userRepository.save(existingUser);
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, Object> resetUserPassword(String token, String password) {
        if (Objects.isNull(password)) {
            throw new DataNotAcceptableException(1019);
        }
        User user = validateToken(token);
        if (!Objects.isNull(user.getPassword()) && user.getPassword().equals(password)) {
            Logger.logError(StringUtil.constructString(ErrorConstants.SAME_PASSWORD));
            throw new FhirValidation(1012);
        }
        user.setPassword(password);
        user.setForgetPassword(null);
        user.setForgetPasswordCount(Constants.ZERO);
        userRepository.save(user);
        return Map.of(Constants.IS_PASSWORD_SET, Constants.BOOLEAN_TRUE);
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteUserById(Long userId) {
        if (userId != 0) {
            User user = getUserById(userId);
            if (null != user) {
                user.setActive(Boolean.FALSE);
                user.setDeleted(Boolean.TRUE);
                userRepository.save(user);
                userTokenService.deleteUserTokenByUserName(user.getUsername(), user.getId());
                userApiInteractionService.deleteUserApiKeys(user.getId());
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        throw new FhirValidation(1011);
    }

    /**
     * <p>
     * The method is used to validate a token, checks if it has expired, and returns the corresponding user.
     * </p>
     *
     * @param token {@link String} The token to identify the user and to validate is given
     * @return {@link User} The user for the given token is validated and returned
     */
    private User validateToken(String token) {
        User user = userRepository.findByForgetPassword(token);
        if (Objects.isNull(user)) {
            throw new DataNotFoundException(3012);
        }
        user.setForgetPassword(null);
        Key secretKeySpec = secretKeySpecCreation();
        Claims body = Jwts.parser().setSigningKey(secretKeySpec).parseClaimsJws(token).getBody();

        if (ZonedDateTime.ofInstant(body.getExpiration().toInstant(), ZoneId.of(FieldConstants.UTC))
                .isBefore(ZonedDateTime.now(ZoneId.of(FieldConstants.UTC)))) {
            Logger.logError(StringUtil.constructString(ErrorConstants.LINK_EXPIRED));
            throw new FhirValidation(3009);
        }
        return user;
    }

    /**
     * <p>
     * Creates a secret key specification using a given signature algorithm and a base64-encoded API key secret.
     * </p>
     *
     * @return The created SecretKeySpec.
     */
    private Key secretKeySpecCreation() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.AES_KEY_TOKEN);
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    /**
     * {@inheritDoc}
     */
    public String forgotPassword(String emailId, User user, boolean isFromCreation) {
        user = (null == user) ? userRepository.getUserByUsername(emailId, Boolean.TRUE) : user;
        if (null != user) {
            boolean forgotPasswordLimitExceed = checkForgotPasswordLimitExceed(user, isFromCreation);
            if (!forgotPasswordLimitExceed) {
                String jwtToken;
                try {
                    jwtToken = forgotPasswordTokenCreation(user);
                    user.setForgetPassword(jwtToken);
                    userRepository.save(user);
                    return jwtToken;
                } catch (Exception exception) {
                    Logger.logError(String.valueOf(exception));
                }
            }
            return null;
        }
        return null;
    }

    /**
     * <p>
     * The method is used to check if the user has exceeded the limit for forgot password attempts and
     * blocks the user if necessary.
     * </p>
     *
     * @param user           {@link User} The user object for which the forgot password limit needs to be checked.
     * @param isFromCreation The indication, whether the user is being created for the first time or not is given
     * @return {@link Boolean} A boolean value is returned whether the forgot password limit exceeds or not
     */
    private Boolean checkForgotPasswordLimitExceed(User user, boolean isFromCreation) {
        int forgotPasswordCount = user.getForgetPasswordCount();
        Date forgotPasswordTime = DateUtil.formatDate(user.getForgetPasswordTime());
        Date currentDate = DateUtil.formatDate(new Date());
        long getDateDiffInMinutes = DateUtil.getDateDiffInMinutes(forgotPasswordTime, currentDate);
        if (getDateDiffInMinutes >= forgotPasswordTimeLimitInMinutes) {
            user.setForgetPasswordTime(currentDate);
            user.setForgetPasswordCount(Constants.INT_ONE);
        } else {
            if (Constants.ZERO == forgotPasswordCount) {
                user.setForgetPasswordTime(currentDate);
            }
            if (forgotPasswordCount < forgotPasswordCountLimit && forgotPasswordCount >= 0 && !isFromCreation) {
                user.setForgetPasswordCount(++forgotPasswordCount);
            }
            if (forgotPasswordCount == forgotPasswordCountLimit) {
                user.setForgetPasswordCount(Constants.ZERO);
                userRepository.save(user);
                return Boolean.TRUE;
            }
        }
        userRepository.save(user);
        return Boolean.FALSE;
    }

    /**
     * <p>
     * This method is used to create a JWT token with user information and expiration time.
     * </p>
     *
     * @param user {@link User} The user for whom the forgot password token is being constructed.
     * @return {@link String} A JSON Web Token (JWT) created using the user's information and a
     * secret key is returned
     */
    private String forgotPasswordTokenCreation(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.AES_KEY_TOKEN);
        Key secretKeySpec = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(FieldConstants.USERNAME, user.getUsername());
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(userInfo).signWith(signatureAlgorithm, secretKeySpec);
        return jwtBuilder.setId(String.valueOf(user.getId()))
                .setExpiration(Date.from(ZonedDateTime.now().plusHours(Constants.TWENTY_FOUR).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant())).setIssuer(Constants.ISSUER).compact();
    }
}