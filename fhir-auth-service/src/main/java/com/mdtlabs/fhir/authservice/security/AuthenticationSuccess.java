package com.mdtlabs.fhir.authservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.AuthUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.service.UserTokenService;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * The {@code AuthenticationSuccess} class extends {@link SimpleUrlAuthenticationSuccessHandler} and is responsible for handling
 * successful authentication in the Spring Security framework. It returns user information in JSON format and creates an
 * authentication token for the user.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
public class AuthenticationSuccess extends SimpleUrlAuthenticationSuccessHandler {

    private RSAPublicKey publicRsaKey;

    @Value("${app.public-key-file-name}")
    private String publicKey;

    @Autowired
    private UserTokenService userTokenService;

    /**
     * <p>
     * Initializes a public RSA key from a file resource.
     * </p>
     */
    @PostConstruct
    public void init() {
        try {
            Resource resource = new ClassPathResource(publicKey);
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
            KeyFactory kf = KeyFactory.getInstance(Constants.RSA);
            this.publicRsaKey = (RSAPublicKey) kf.generatePublic(spec);
        } catch (Exception e) {
            Logger.logError(ErrorConstants.EXCEPTION_TOKEN_UTILS, e);
        }
    }

    /**
     * <p>
     * Handles successful authentication and returns user information in JSON format.
     * </p>
     *
     * @param request        The {@link HttpServletRequest} representing the client's HTTP request.
     * @param response       The {@link HttpServletResponse} for sending the response.
     * @param authentication The {@link Authentication} object containing user authentication information.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        if (!response.isCommitted()) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(Constants.CONTENT_TEXT_TYPE);
            response.setHeader(Constants.CACHE_HEADER_NAME, Constants.CACHE_HEADER_VALUE);
            response.setHeader(Constants.ACCESS_CONTROL_EXPOSE_HEADERS, Constants.AUTHORIZATION);
            try {
                AuthUserDTO user = getLoggedInUser();
                if (user != null) {
                    user.setCurrentDate(new Date().getTime());
                    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
                    String json = objectWriter.writeValueAsString(user);
                    response.getWriter().write(json);
                    responseHeaderUser(response, user);
                } else {
                    response.getWriter().write(ErrorConstants.INVALID_USER_ERROR);
                }
            } catch (IOException exception) {
                Logger.logError(ErrorConstants.LOGIN_ERROR + exception);
            }
        }
        clearAuthenticationAttributes(request);
    }

    /**
     * <p>
     * Sets the response headers for a user's authentication token and tenant ID.
     * </p>
     *
     * @param response The {@link HttpServletResponse} for sending the response.
     * @param user     An {@link AuthUserDTO} object representing the authenticated user.
     */
    private void responseHeaderUser(HttpServletResponse response, AuthUserDTO user) {
        Map<String, Object> userInfo = new ObjectMapper().convertValue(user, Map.class);
        String authToken = null;
        try {
            authToken = authTokenCreation(user, userInfo);
        } catch (JOSEException exception) {
            Logger.logError(ErrorConstants.ERROR_JWE_TOKEN, exception);
        }
        if (authToken != null) {
            createUserToken(authToken);
        }
        response.setHeader(Constants.AUTHORIZATION, authToken);
    }

    /**
     * <p>
     * Creates an encrypted JWT authentication token with user and organization information.
     * </p>
     *
     * @param user     An {@link AuthUserDTO} representing the authenticated user.
     * @param userInfo A {@link Map} containing additional user information.
     * @return A string representing an encrypted JWT.
     */
    private String authTokenCreation(AuthUserDTO user, Map<String, Object> userInfo) throws JOSEException {
        JWTClaimsSet.Builder claimsSet = new JWTClaimsSet.Builder();
        claimsSet.issuer(Constants.TOKEN_ISSUER);
        claimsSet.subject(Constants.AUTH_TOKEN_SUBJECT);
        claimsSet.claim(Constants.USER_ID_PARAM, user.getId());
        claimsSet.claim(Constants.USER_DATA, userInfo);
        claimsSet.expirationTime(
                Date.from(ZonedDateTime.now().plusMinutes(Constants.AUTH_TOKEN_EXPIRY_MINUTES).toInstant()));
        claimsSet.notBeforeTime(new Date());
        claimsSet.jwtID(UUID.randomUUID().toString());
        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);
        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet.build());
        RSAEncrypter rsaEncrypter = new RSAEncrypter(this.publicRsaKey);
        jwt.encrypt(rsaEncrypter);
        return Constants.BEARER.concat(jwt.serialize());
    }

    /**
     * <p>
     * Creates a user token by saving the JWT token, username, client, and user ID in the userTokenService.
     * </p>
     *
     * @param jwtToken The JWT token to be saved.
     */
    private void createUserToken(String jwtToken) {
        AuthUserDTO user = getLoggedInUser();
        if (user != null) {
            userTokenService.deleteInvalidUserTokensByUserId(user.getId());
            userTokenService.saveUserToken(jwtToken.substring(Constants.BEARER.length()),
                    user.getUsername(), user.getId());
        }
    }

    /**
     * <p>
     * Retrieves the currently logged-in user's information and maps it to an AuthUserDTO object.
     * </p>
     *
     * @return An instance of {@link AuthUserDTO} representing the currently logged-in user.
     */
    private AuthUserDTO getLoggedInUser() {
        if (null == SecurityContextHolder.getContext() || null == SecurityContextHolder.getContext().getAuthentication()
                || null == SecurityContextHolder.getContext().getAuthentication().getPrincipal()) {
            return null;
        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(Constants.ANONYMOUS_USER)) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUserDTO authUserDTO;
        authUserDTO = new ModelMapper().map(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                AuthUserDTO.class);
        User user = (User) principal;
        authUserDTO.setIsSuperUser(user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Constants.ROLE_SUPER_USER)));
        return authUserDTO;
    }

}