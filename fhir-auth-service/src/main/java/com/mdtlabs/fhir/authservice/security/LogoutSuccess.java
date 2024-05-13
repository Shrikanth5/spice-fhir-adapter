package com.mdtlabs.fhir.authservice.security;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.Validation;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import com.mdtlabs.fhir.commonservice.common.service.UserTokenService;
import com.mdtlabs.fhir.commonservice.common.utils.CommonUtil;
import com.mdtlabs.fhir.commonservice.common.utils.DateUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * The {@code LogoutSuccess} class implements the {@link LogoutHandler} interface and is responsible for logging out a user by deleting
 * their token from the userTokenService.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
public class LogoutSuccess implements LogoutHandler {

    @Autowired
    private UserTokenService userTokenService;

    /**
     * <p>
     * Logs out a user by deleting their token from the userTokenService.
     * </p>
     *
     * @param request        An object of the HttpServletRequest class, representing the HTTP request sent to the server.
     * @param response       The HttpServletResponse used to set response headers, status codes, and write response data.
     * @param authentication The authentication object used to check if the user is currently authenticated and authorized.
     */
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(token)) {
            Logger.logError(String.valueOf(20002));
            throw new DataNotFoundException(20002);
        }

        UserToken userTokenRecord = userTokenService.findByAuthToken(token);
        if (Objects.nonNull(userTokenRecord)) {
            Date lastSessionTime = userTokenRecord.getLastSessionTime();
            Date expDate = CommonUtil.formatDate(lastSessionTime);
            Date currentDate = CommonUtil.formatDate(new Date());
            long expiryTimeInMinutes = DateUtility.getDateDiffInMinutes(expDate, currentDate);
            if (expiryTimeInMinutes > Constants.EXPIRY_MINUTES) {
                Logger.logError("Token has been expired");
                throw new Validation(20007);
            } else {
                userTokenService.deleteUserTokenByToken(token);
            }
        } else {
            Logger.logError(String.valueOf(20007));
            throw new Validation(20007);
        }
    }

}