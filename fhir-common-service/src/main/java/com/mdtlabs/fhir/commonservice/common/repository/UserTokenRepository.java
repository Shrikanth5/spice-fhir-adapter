package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * This is the repository class for communicate link between server side and
 * database. This class used to perform all the user module action in database.
 * In query annotation (nativeQuery = true) the below query perform like SQL.
 * Otherwise its perform like HQL default value for nativeQuery FALSE
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    String DELETE_BY_AUTH_TOKENS = "delete from UserToken as userToken where " +
            "userToken.authToken IN (:authTokenList) and userToken.isActive = true";


    /**
     * <p>
     * Finds user token by user authentication token.
     * </p>
     *
     * @param token authentication token
     * @return {@link UserToken}
     */
    UserToken findByAuthToken(String token);

    /**
     * <p>
     * Finds user token by user ID.
     * </p>
     *
     * @param userId user ID
     * @return {@link List<UserToken>}  List  of UserToken
     */
    List<UserToken> findByUserIdAndIsActiveTrue(Long userId);

    /**
     * <p>
     * Delete User token by user id
     * </p>
     *
     * @param userId user Id
     */
    void deleteByUserId(Long userId);

    /**
     * <p>
     * Delete User token by auth token
     * </p>
     */
    void deleteByAuthToken(String token);


    /**
     * <p>
     * Used to delete the userToken(s) by authTokens.
     * </p>
     */
    @Query(value = DELETE_BY_AUTH_TOKENS)
    @Modifying
    @Transactional
    void deleteByAuthTokens(List<String> authTokenList);

}
