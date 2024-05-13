package com.mdtlabs.fhir.commonservice.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.exception.Validation;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserTokenRepository;
import com.mdtlabs.fhir.commonservice.common.utils.CommonUtil;
import com.mdtlabs.fhir.commonservice.common.utils.CustomDateSerializer;
import com.mdtlabs.fhir.commonservice.common.utils.DateUtility;
import com.mdtlabs.fhir.commonservice.common.utils.UserContextHolder;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * Used to do internal filter on token validation.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final CustomDateSerializer customDateSerializer;
    private final UserTokenRepository userTokenRepository;
    private final ApiKeyManagerRepository apiKeyManagerRepository;
    org.apache.logging.log4j.Logger log = LogManager.getLogger(AuthenticationFilter.class);
    private RSAPrivateKey privateRsaKey = null;
    @Value("${app.private-key-file-name}")
    private String privateKey;

    public AuthenticationFilter(CustomDateSerializer customDateSerializer,
                                UserTokenRepository userTokenRepository,
                                ApiKeyManagerRepository apiKeyManagerRepository) {
        this.customDateSerializer = customDateSerializer;
        this.userTokenRepository = userTokenRepository;
        this.apiKeyManagerRepository = apiKeyManagerRepository;
    }

    /**
     * <p>
     * This is a Java function that filters incoming HTTP requests and checks for valid authentication
     * tokens before allowing access to protected resources.
     * </p>
     *
     * @param request     {@link HttpServletRequest} An object representing the HTTP request made by the client.
     * @param response    {@link HttpServletRequest} The HttpServletResponse object represents the response that will be sent back to
     *                    the client after the request has been processed
     * @param filterChain {@link FilterChain} The filterChain parameter is an object that represents the chain of filters
     *                    that will be applied to the request and response
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessKeyId = request.getHeader(Constants.ACCESS_KEY_ID_PARAM);
        String secretAccessKey = request.getHeader(Constants.SECRET_ACCESS_KEY_PARAM);


        if (isOpenUri(request)) {
            authenticateWithNullCredentials(filterChain, request, response);
        } else if (isValidApiKey(accessKeyId, secretAccessKey)) {
            ApiKeyManager apiKeyManager = apiKeyManagerRepository.findByAccessKeyIdAndSecretAccessKey(accessKeyId, secretAccessKey);
            if (Objects.nonNull(apiKeyManager)) {
                boolean isFhirAdmin = apiKeyManager.getUser().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Constants.FHIR_ADMIN));
                if (apiKeyManager.isActive() && isFhirAdmin) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, null);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    filterChain.doFilter(request, response);
                } else {
                    log.info("Inactive API Key/Insufficient privileges");
                    throw new Validation(20009);
                }
            } else {
                log.info("No API key pair found");
                throw new Validation(20008);
            }
        } else {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isValidBearerToken(token)) {
                String authToken = extractAuthToken(token);
                UserToken userTokenRecord = userTokenRepository.findByAuthToken(authToken);
                validateAuthentication(request, response, filterChain, userTokenRecord, authToken);
            } else {
                throw new Validation(20001);
            }
        }
    }

    /**
     * Authenticates with null credentials and continues with the filter chain.
     *
     * @param filterChain The FilterChain to execute for the request.
     * @param request     The HttpServletRequest object representing the incoming request.
     * @param response    The HttpServletResponse object representing the outgoing response.
     * @throws IOException      If an I/O error occurs during the servlet processing.
     * @throws ServletException If an error occurs during the servlet processing.
     */
    private void authenticateWithNullCredentials(FilterChain filterChain, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    /**
     * Checks if the API key and secret key are valid.
     *
     * @param accessKeyId     The API key.
     * @param secretAccessKey The secret key.
     * @return True if both API key and secret key are not blank; otherwise, false.
     */
    private boolean isValidApiKey(String accessKeyId, String secretAccessKey) {
        return StringUtils.isNotBlank(accessKeyId) && StringUtils.isNotBlank(secretAccessKey);
    }

    /**
     * Checks if the provided token is a valid Bearer token.
     *
     * @param token The token to validate.
     * @return True if the token is not blank and starts with the Bearer prefix; otherwise, false.
     */
    private boolean isValidBearerToken(String token) {
        return StringUtils.isNotBlank(token) && token.startsWith(Constants.BEARER);
    }

    /**
     * Extracts the authentication token from the Bearer token.
     *
     * @param token The Bearer token.
     * @return The extracted authentication token.
     */
    private String extractAuthToken(String token) {
        return token.substring(Constants.BEARER.length());
    }

    private void validateAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, UserToken userTokenRecord, String authToken) throws IOException, ServletException {
        if (Objects.nonNull(userTokenRecord)) {
            Date lastSessionTime = userTokenRecord.getLastSessionTime();
            Date expDate = CommonUtil.formatDate(lastSessionTime);
            Date currentDate = CommonUtil.formatDate(new Date());
            long expiryTimeInMinutes = DateUtility.getDateDiffInMinutes(expDate, currentDate);
            if (expiryTimeInMinutes < Constants.EXPIRY_MINUTES) {
                try {
                    UserDTO userDto;
                    userDto = validateAspect(authToken);
                    setAuthenticationInSecurityContext(userDto);
                    filterChain.doFilter(request, response);
                } catch (JsonProcessingException | ParseException e) {
                    Logger.logError(e);
                    throw new Validation(20001);
                }
            } else {
                throw new Validation(20007);
            }
        } else {
            throw new Validation(20007);
        }
    }

    /**
     * <p>
     * Used to validate the authentication token.
     * </p>
     *
     * @param jwtToken jwt token of the logged-in user
     * @return UserDTO {@link UserDTO}  user information
     * @throws ParseException          Parse exception
     * @throws JsonProcessingException Json processing exception
     */
    public UserDTO validateAspect(String jwtToken)
            throws ParseException, JsonProcessingException {

        if (StringUtils.isBlank(jwtToken)) {
            jwtToken = Constants.SPACE;
        }
        if (null == privateRsaKey) {
            tokenDecrypt();
        }

        EncryptedJWT jwt;
        try {
            jwt = EncryptedJWT.parse(jwtToken);
        } catch (ParseException e) {
            Logger.logError(e);
            throw new Validation(20002);
        }
        RSADecrypter rsaDecrypter = new RSADecrypter(privateRsaKey);
        try {
            jwt.decrypt(rsaDecrypter);
        } catch (JOSEException e) {
            Logger.logError(e);
            throw new Validation(20002);
        }
        UserDTO userDetail;
        String rawJson = String.valueOf(jwt.getJWTClaimsSet().getClaim(Constants.USER_DATA));
        ObjectMapper objectMapper = new ObjectMapper();
        userDetail = objectMapper.readValue(rawJson, UserDTO.class);
        userDetail.setAuthorization(jwtToken);

        if (null != userDetail.getTimezone()) {
            customDateSerializer.setUserZoneId(userDetail.getTimezone().getOffset());
        }
        UserContextHolder.setUserDto(userDetail);
        return userDetail;
    }

    /**
     * <p>
     * Decrypt given jwe token using private key.
     * </p>
     */
    @PostConstruct
    private void tokenDecrypt() {
        try {
            Resource resource = new ClassPathResource(privateKey);
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(data);
            KeyFactory kf = KeyFactory.getInstance(Constants.RSA);
            this.privateRsaKey = (RSAPrivateKey) kf.generatePrivate(privateKeySpec);
        } catch (Exception exception) {
            Logger.logError(ErrorConstants.EXCEPTION_DURING_TOKEN_UTIL, exception);
        }

    }

    /**
     * <p>
     * Used to find open uri to access directly without authorization.
     * </p>
     *
     * @param request {@link HttpServletRequest}
     * @return boolean
     */
    private boolean isOpenUri(HttpServletRequest request) {
        return request.getRequestURI().contains(Constants.API_KEY_MANAGER_BASE_URI + Constants.VALIDATE_ENDPOINT)
                || request.getRequestURI().contains(Constants.USERS_BASE_URI + Constants.RESET_PASSWORD_URI)
                || request.getRequestURI().contains(Constants.USERS_BASE_URI + Constants.FORGOT_PASSWORD_URI)
                || request.getRequestURI().contains(Constants.USER_MIGRATION_URI)
                || request.getRequestURI().contains(Constants.SITE_MIGRATION_URI);

    }

    /**
     * <p>
     * Used to set authentication in the security context.
     * </p>
     *
     * @param userDto {@link UserDTO}
     */
    private void setAuthenticationInSecurityContext(UserDTO userDto) {
        if (null != userDto.getAuthorization()) {
            String username = userDto.getUsername();
            if (username != null) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username,
                        null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    }
}
