package com.mdtlabs.fhir.fhiruserservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import com.mdtlabs.fhir.fhiruserservice.service.UserApiInteractionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * <p>
 * The {@code UserApiInteractionService} interface defines the contract for user-Api related operations.
 * It includes methods for retrieving, creating, updating, and deleting userApiKeys.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Service
public class UserApiInteractionServiceImpl implements UserApiInteractionService {

    private final ApiKeyManagerRepository apiKeyManagerRepository;

    public UserApiInteractionServiceImpl(ApiKeyManagerRepository apiKeyManagerRepository) {
        this.apiKeyManagerRepository = apiKeyManagerRepository;
    }

    /**
     * <p>
     * This method used to delete a userApiKey by using usedId.
     * </p>
     *
     * @param userId the user's id
     */
    @Override
    @Transactional
    public void deleteUserApiKeys(Long userId) {
        apiKeyManagerRepository.deleteByUserId(userId);
    }


}