package com.mdtlabs.fhir.commonservice.common.service;

import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;

/**
 * <p>
 * This an interface class for UserToken entity
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public interface UserTokenService {

    /**
     * <p>
     * To save the UserToken
     * </p>
     *
     * @param authToken auth token
     */
    void saveUserToken(String authToken, String username, long userId);

    /**
     * <p>
     * This method used to delete all UserTokens by given authToken.
     * </p>
     *
     * @param token auth key
     */
    void deleteUserTokenByToken(String token);

    /**
     * <p>
     * Deletes an user token.
     * </p>
     *
     * @param username user email ID
     * @param userId   user ID
     */
    void deleteUserTokenByUserName(String username, Long userId);

    /**
     * <p>
     * find by authToken
     * </p>
     *
     * @param authToken auth token
     */
    UserToken findByAuthToken(String authToken);

    /**
     * <p>
     * Delete userTokens by userId
     * </p>
     *
     * @param userId auth token
     */
    void deleteInvalidUserTokensByUserId(Long userId);


}