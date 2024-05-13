package com.mdtlabs.fhir.fhiruserservice.service;

import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;

import java.util.List;

/**
 * <p>
 * The {@code ApiKeyManagerService} interface defines the contract for managing API key entities.
 * It includes methods for retrieving, creating, updating, and deleting API key entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public interface ApiKeyManagerService {

    /**
     * <p>
     * Retrieve a list of API key managers associated with a specific user.
     * </p>
     *
     * @param userId The unique identifier of the user.
     * @return A list of API key manager entities associated with the user.
     */
    List<ApiKeyManager> getApiKeysByUserId(Long userId);

    /**
     * <p>
     * Delete an API key manager by its unique identifier (ID).
     * </p>
     *
     * @param id The unique identifier of the API key manager to be deleted.
     * @return true if the API key manager is deleted successfully, false otherwise.
     */
    boolean deleteApiKeyManagerByUserId(Long id);

    /**
     * <p>
     * Generate a new API key and associate it with the specified user ID.
     * </p>
     *
     * @param user The unique identifier of the user.
     * @return The newly generated API key manager entity.
     */
    ApiKeyManager generateApiKey(User user);

    /**
     * <p>
     * Validate the given access key and secret access key.
     * </p>
     *
     * @param accessKeyId     The access key ID to be validated.
     * @param secretAccessKey The secret access key to be validated.
     * @return true if the API key is valid, false otherwise.
     */
    boolean validateApiKey(String accessKeyId, String secretAccessKey);
}