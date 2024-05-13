package com.mdtlabs.fhir.fhiruserservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import com.mdtlabs.fhir.fhiruserservice.service.ApiKeyManagerService;
import com.mdtlabs.fhir.fhiruserservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * The {@code ApiKeyManagerService} class defines the contract for managing API key entities.
 * It includes methods for retrieving, creating, updating, and deleting API key entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Service("apiKeyManagerServiceImpl")
public class ApiKeyManagerServiceImpl implements ApiKeyManagerService {

    private final ApiKeyManagerRepository apiKeyManagerRepository;
    private final UserService userService;

    public ApiKeyManagerServiceImpl(ApiKeyManagerRepository apiKeyManagerRepository, UserService userService) {
        this.apiKeyManagerRepository = apiKeyManagerRepository;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    public List<ApiKeyManager> getApiKeysByUserId(Long userId) {
        return apiKeyManagerRepository.findByUserId(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean deleteApiKeyManagerByUserId(Long id) {
        if (apiKeyManagerRepository.existsByUserId(id)) {
            apiKeyManagerRepository.deleteByUserId(id);
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public ApiKeyManager generateApiKey(User user) {
        User validUser = userService.findByUserIdAndIsActiveTrue(user.getId());
        if (Objects.nonNull(validUser)) {
            String accessKeyId = generateRandomString(60);
            String secretAccessKey = generateRandomString(120);

            ApiKeyManager apiKeyManager = new ApiKeyManager();
            apiKeyManager.setUser(validUser);
            apiKeyManager.setAccessKeyId(accessKeyId);
            apiKeyManager.setSecretAccessKey(secretAccessKey);
            apiKeyManager.setActive(Boolean.TRUE);

            return apiKeyManagerRepository.save(apiKeyManager);
        } else {
            return null;
        }
    }

    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);

        return Base64.getEncoder().encodeToString(randomBytes);
    }

    /**
     * {@inheritDoc}
     */
    public boolean validateApiKey(String accessKeyId, String secretAccessKey) {
        ApiKeyManager apiKeyManager = apiKeyManagerRepository.findByAccessKeyIdAndSecretAccessKey(accessKeyId, secretAccessKey);
        return apiKeyManager != null && apiKeyManager.isActive();
    }

}