package com.mdtlabs.fhir.commonservice.common.service.impl;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import com.mdtlabs.fhir.commonservice.common.repository.UserTokenRepository;
import com.mdtlabs.fhir.commonservice.common.service.UserTokenService;
import com.mdtlabs.fhir.commonservice.common.utils.CommonUtil;
import com.mdtlabs.fhir.commonservice.common.utils.DateUtility;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This a Service class for UserToken
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Service(Constants.USER_TOKEN_SERVICE)
public class UserTokenServiceImpl implements UserTokenService {

    private final UserTokenRepository userTokenRepository;

    public UserTokenServiceImpl(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    /**
     * {@inheritDoc}
     */
    public void saveUserToken(String authToken, String username, long userId) {
        UserToken usertoken = new UserToken();
        usertoken.setAuthToken(authToken);
        usertoken.setUserId(userId);
        usertoken.setActive(Boolean.TRUE);
        userTokenRepository.save(usertoken);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteUserTokenByToken(String token) {
        userTokenRepository.deleteByAuthToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteUserTokenByUserName(String username, Long userId) {
        userTokenRepository.deleteByUserId(userId);
    }

    /**
     * {@inheritDoc}
     */
    public UserToken findByAuthToken(String authToken) {
        return userTokenRepository.findByAuthToken(authToken);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteInvalidUserTokensByUserId(Long userId) {
        List<UserToken> userTokensByUserIdAndIsActiveTrue = userTokenRepository.findByUserIdAndIsActiveTrue(userId);
        List<String> authTokens = deleteUserTokensByAuthTokens(userTokensByUserIdAndIsActiveTrue);
        userTokenRepository.deleteByAuthTokens(authTokens);
    }

    /**
     * <p>
     * This method is used to update the UserToken
     * in both User and UserToken table
     * </p>
     *
     * @param userTokensByUserId list of user tokens by user id
     */
    private List<String> deleteUserTokensByAuthTokens(List<UserToken> userTokensByUserId) {
        List<String> tokenList = new ArrayList<>();
        userTokensByUserId.forEach(userTokenRecord -> {
            String authToken = userTokenRecord.getAuthToken();
            Date lastSessionTime = userTokenRecord.getLastSessionTime();
            Date expDate = CommonUtil.formatDate(lastSessionTime);
            Date currentDate = CommonUtil.formatDate(new Date());
            long expiryTimeInMinutes = DateUtility.getDateDiffInMinutes(expDate, currentDate);
            if (expiryTimeInMinutes > Constants.EXPIRY_MINUTES) {
                tokenList.add(authToken);
            }
        });
        return tokenList;
    }

}
