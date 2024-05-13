package com.mdtlabs.fhir.fhiruserservice.service;

import com.mdtlabs.fhir.commonservice.common.model.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * The {@code UserService} interface defines the contract for user-related operations.
 * It includes methods for retrieving, creating, updating, and deleting user entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
public interface UserService {

    /**
     * <p>
     * Create a new user entity.
     * </p>
     *
     * @param user The user to be created.
     */
    User addUser(User user);


    /**
     * <p>
     * This method is used to get the count of all users.
     * </p>
     *
     * @return The count of users is returned.
     */
    int getTotalSize();

    /**
     * <p>
     * Retrieve a user by their unique identifier (ID).
     * </p>
     *
     * @param id The unique identifier of the user.
     * @return The user entity if found, otherwise null.
     */
    User getUserById(Long id);

    /**
     * </p>
     * Retrieve a user by their username.
     * </p>
     *
     * @param username The username of the user.
     * @return The user entity if found, otherwise null.
     */
    User getUserByUsername(String username);

    /**
     * <p>
     * This method is used to get all user information with pagination.
     * </p>
     *
     * @param pageNumber The page number of the list of users to be retrieved.
     * @return A list of users for the given pagination.
     */
    List<User> getUsers(int pageNumber);

    /**
     * <p>
     * Update an existing user by their unique identifier (ID).
     * </p>
     *
     * @param updatedUser The updated user data.
     * @return The updated user entity if found, otherwise null.
     */
    User updateUser(User updatedUser);

    /**
     * <p>
     * This method is used to reset a user's password using a token and user information.
     * </p>
     *
     * @param token    The token to identify the user for whom the password is being reset.
     * @param password A map of user's information.
     * @return A map containing the response whether the password resets or not.
     */
    Map<String, Object> resetUserPassword(String token, String password);

    /**
     * <p>
     * Delete a user by their unique identifier (ID).
     * </p>
     *
     * @param id The unique identifier of the user to be deleted.
     * @return true if the user is deleted successfully, false otherwise.
     */
    boolean deleteUserById(Long id);

    /**
     * <p>
     * This method is used to handle a forgot password validation request.
     * </p>
     *
     * @param email          The username of the user who needs to reset their password.
     * @param user           The user contains information about the user, such as their name, password,
     *                       and other details.
     * @param isFromCreation The indication whether the "forgot password" request is being made during the
     *                       user creation process or not.
     * @return A message indicating that the password has been validated.
     */
    String forgotPassword(String email, User user, boolean isFromCreation);

    /**
     * <p>
     * This method is used to get user by userId and isActive.
     * </p>
     *
     * @param userId the user's id
     * @return User object
     */
    User findByUserIdAndIsActiveTrue(Long userId);

}