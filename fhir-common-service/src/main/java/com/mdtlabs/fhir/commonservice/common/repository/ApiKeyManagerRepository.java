package com.mdtlabs.fhir.commonservice.common.repository;

import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * The {@code ApiKeyManagerRepository} interface provides CRUD operations for {@link ApiKeyManager} entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Repository
public interface ApiKeyManagerRepository extends JpaRepository<ApiKeyManager, Long> {

    /**
     * <p>
     * Retrieve a list of API key managers associated with a specific user ID.
     * </p>
     *
     * @param userId The unique identifier of the user.
     * @return A list of API key manager entities associated with the user.
     */
    List<ApiKeyManager> findByUserId(Long userId);

    /**
     * <p>
     * Check if there are any API key managers associated with a specific user ID.
     * </p>
     *
     * @param userId The unique identifier of the user.
     * @return true if API key managers exist for the user, false otherwise.
     */
    boolean existsByUserId(Long userId);

    /**
     * <p>
     * Delete API key managers associated with a specific user ID.
     * </p>
     *
     * @param userId The unique identifier of the user.
     */
    void deleteByUserId(Long userId);

    /**
     * <p>
     * Finds an {@link ApiKeyManager} entity based on the provided access key ID and secret access key.
     * </p>
     *
     * @param accessKeyId     The access key ID to search for.
     * @param secretAccessKey The secret access key to search for.
     * @return The {@link ApiKeyManager} entity if found, or {@code null} if not found.
     */
    ApiKeyManager findByAccessKeyIdAndSecretAccessKey(String accessKeyId, String secretAccessKey);

}