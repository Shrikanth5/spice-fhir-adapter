package com.mdtlabs.fhir.fhiruserservice.service;

import jakarta.transaction.Transactional;

/**
 * <p>
 * The {@code UserApiInteractionService} interface defines the contract for user-Api related operations.
 * It includes methods for retrieving, creating, updating, and deleting userApiKeys.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public interface UserApiInteractionService {

    /**
     * <p>
     * This method used to delete a userApiKey by using usedId.
     * </p>
     *
     * @param userId the user's id
     */
    @Transactional
    void deleteUserApiKeys(Long userId);
}
