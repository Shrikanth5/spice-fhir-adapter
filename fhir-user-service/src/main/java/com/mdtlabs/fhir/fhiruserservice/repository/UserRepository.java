package com.mdtlabs.fhir.fhiruserservice.repository;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * The {@code UserRepository} interface defines repository methods for User entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String GET_USER_BY_USERNAME = "from User as user "
            + "where user.username =:user_name and user.isActive =:status and user.isDeleted=false";
    String GET_ALL_USERS = "select user from User as user where user.isActive =:status";

    /**
     * <p>
     * This method is used to get the user of the given username from the database
     * who is not deleted.
     * </p>
     *
     * @param username The username of the user to be searched.
     * @return The non-deleted user for the given username (case-insensitive) is retrieved
     * and returned from the database.
     */
    User findByUsernameAndIsDeletedFalse(String username);

    /**
     * <p>
     * This method is used to get the list of users from the database
     * who are either active or inactive.
     * </p>
     *
     * @param status The status that is used to filter the list of users that have the
     *               specified active status is given.
     * @return The list of users for the given active status is retrieved and
     * returned from the database.
     */
    @Query(value = GET_ALL_USERS)
    List<User> getUsers(@Param(FieldConstants.STATUS) Boolean status);

    /**
     * <p>
     * This method is used to get the page of users from the database
     * who are either active or inactive.
     * </p>
     *
     * @param status   The status that is used to filter the list of users
     *                 that have the specified active status is given.
     * @param pageable The pagination information that contains information such as the page number,
     *                 page size, sorting criteria, and more is given.
     * @return A Page of Users that match the search criteria for super admin users, with applied
     * pagination is retrieved and returned from the database.
     */
    @Query(value = GET_ALL_USERS)
    Page<User> getUsers(@Param(FieldConstants.STATUS) Boolean status, Pageable pageable);

    /**
     * <p>
     * This method is used to get the user of the given forgot password token from the database.
     * </p>
     *
     * @return The user for the given password token is retrieved and returned from the database.
     */
    User findByForgetPassword(String forgetPassword);

    /**
     * <p>
     * This method is used to get the user of the given username from the database
     * who is either active or inactive and not deleted.
     * </p>
     *
     * @param username The name of the user who needs to be retrieved from the database is given.
     * @param status   The status that is used to filter the list of users that have the specified active status is given.
     * @return The user for the given username and active status is retrieved and returned from the database.
     */
    @Query(value = GET_USER_BY_USERNAME)
    User getUserByUsername(@Param(FieldConstants.USERNAME) String username,
                           @Param(FieldConstants.STATUS) Boolean status);

    /**
     * <p>
     * Retrieves a user from the database by their unique user ID, considering only active users.
     * </p>
     *
     * @param userId The unique identifier of the user to be retrieved.
     * @return The user with the specified ID, only if they are marked as active (isActive = true).
     */
    User findByIdAndIsActiveTrue(Long userId);

}
