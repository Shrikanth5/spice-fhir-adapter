package com.mdtlabs.fhir.authservice.repository;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * The {@code UserRepository} interface defines the repository for user entities.
 * It provides methods for interacting with user data in the database.
 * </p>
 *
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    String GET_USER_BY_USERNAME = "select user from User as user where user.username = :user_name and user.isActive = :status and user.isDeleted = false";

    /**
     * <p>
     * Retrieves user data by username and active status.
     * </p>
     *
     * @param username The username of the user.
     * @param status   The active status of the user.
     * @return {@link User} User information matching the provided username and status.
     */
    @Query(value = GET_USER_BY_USERNAME)
    User getUserByUsername(@Param(FieldConstants.USERNAME) String username, @Param(FieldConstants.STATUS) Boolean status);
}