package com.mdtlabs.fhir.fhiruserservice.controller;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserProfileDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessCode;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessResponse;
import com.mdtlabs.fhir.fhiruserservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * The {@code UserController} class represents a RESTFUL controller for managing user-related operations.
 * It provides endpoints for creating, retrieving, updating, and deleting user entities.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@RestController
@RequestMapping(Constants.USERS_BASE_URI)
public class UserController {

    private final ModelMapper mapper = new ModelMapper();
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     * Add a new user.
     * </p>
     *
     * @param requestUserDto {@link UserDTO} User information to be added.
     * @return {@link SuccessResponse<UserDTO>} Added user information along with HTTP status.
     */
    @PostMapping
    public SuccessResponse<UserDTO> addUser(@RequestBody UserRequestDTO requestUserDto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User addUser = userService.addUser(mapper.map(requestUserDto, User.class));
        if (addUser != null) {
            UserDTO userDto = mapper.map(addUser, UserDTO.class);
            return new SuccessResponse<>(SuccessCode.USER_SAVE, userDto, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.USER_NOT_SAVED, false, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * <p>
     * Retrieve user by ID.
     * </p>
     *
     * @param id {@link Long} User ID to retrieve.
     * @return {@link SuccessResponse<UserDTO>} User information along with HTTP status.
     */
    @GetMapping(Constants.USER_BY_ID_URI)
    public SuccessResponse<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            UserDTO userDto = mapper.map(user, UserDTO.class);
            return new SuccessResponse<>(SuccessCode.GET_USER, userDto, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.USER_NOT_FOUND, new UserDTO(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>
     * Retrieve user by username.
     * </p>
     *
     * @param request {@link Map} Request body containing the "userName" parameter.
     * @return {@link SuccessResponse<User>} User information along with HTTP status.
     */
    @GetMapping(Constants.USERNAME_URI)
    public SuccessResponse<User> getUserByUsername(@RequestBody Map<String, String> request) {
        String username = request.get(Constants.USERNAME);
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return new SuccessResponse<>(SuccessCode.GET_USER, user, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.USER_NOT_FOUND, Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>
     * Retrieve a paginated list of users.
     * </p>
     *
     * @param pageNumber {@link int} Page number for pagination.
     * @return {@link SuccessResponse} List of users along with HTTP status.
     */
    @GetMapping(Constants.ALL_PAGE_NUMBER_URI)
    public SuccessResponse<List<UserDTO>> getUsers(@PathVariable(Constants.PAGE_NUMBER) int pageNumber) {
        List<User> allUsers = userService.getUsers(pageNumber);
        if (allUsers.isEmpty()) {
            return new SuccessResponse(SuccessCode.GET_USERS, Constants.NO_DATA_LIST, HttpStatus.OK);
        }
        List<UserDTO> users = mapper.map(allUsers, new TypeToken<List<UserDTO>>() {
        }.getType());
        return new SuccessResponse(SuccessCode.GET_USERS, users, userService.getTotalSize(), HttpStatus.OK);
    }

    /**
     * <p>
     * Retrieve user profile by user ID.
     * </p>
     *
     * @param request {@link Map} Request body containing the "userId" parameter.
     * @return {@link SuccessResponse<UserProfileDTO>} User profile information along with HTTP status.
     */
    @GetMapping(Constants.PROFILE_URI)
    public SuccessResponse<UserProfileDTO> getUserProfileById(@RequestBody Map<String, String> request) {
        Long userId = Long.valueOf(request.get(Constants.USER_ID_PARAM));
        User user = userService.getUserById(userId);
        if (user != null) {
            UserProfileDTO userProfileDto = mapper.map(user, UserProfileDTO.class);
            return new SuccessResponse<>(SuccessCode.GET_USER, userProfileDto, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.USER_NOT_FOUND, Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>
     * Update user information.
     * </p>
     *
     * @param userInfo {@link Map} Request body containing the "user" parameter.
     * @return {@link SuccessResponse<UserDTO>} Updated user information along with HTTP status.
     */
    @PutMapping(Constants.UPDATE_USER_URI)
    public SuccessResponse<UserDTO> updateUser(@RequestBody Map<String, User> userInfo) {
        User user = userInfo.get(Constants.USER);
        UserDTO updatedUserDto = mapper.map(userService.updateUser(mapper.map(user, User.class)), UserDTO.class);
        return new SuccessResponse<>(SuccessCode.USER_UPDATE, updatedUserDto, HttpStatus.OK);
    }

    /**
     * <p>
     * Reset user password.
     * </p>
     *
     * @param userInfo {@link Map} Request body containing "token" and "password" parameters.
     * @return {@link SuccessResponse} Password reset status along with HTTP status.
     */
    @PutMapping(Constants.RESET_PASSWORD_URI)
    public SuccessResponse<Map<String, Boolean>> resetUserPassword(@RequestBody Map<String, String> userInfo) {
        String token = userInfo.get(FieldConstants.TOKEN);
        String password = userInfo.get(FieldConstants.PASSWORD);
        Map<String, Object> res = userService.resetUserPassword(token, password);
        return new SuccessResponse<>(SuccessCode.SET_PASSWORD, res, HttpStatus.OK);
    }

    /**
     * <p>
     * Delete user by ID.
     * </p>
     *
     * @param user {@link UserDTO} User information to be deleted.
     * @return {@link SuccessResponse<Boolean>} Deletion status along with HTTP status.
     */
    @DeleteMapping(Constants.DELETE_USER_URI)
    public SuccessResponse<Boolean> deleteUserById(@RequestBody UserDTO user) {
        return new SuccessResponse<>(SuccessCode.USER_DELETE, userService.deleteUserById(user.getId()), HttpStatus.OK);
    }

    /**
     * <p>
     * Handle forgot password validation.
     * </p>
     *
     * @param requestBody {@link Map} Request body containing the "username" parameter.
     * @return {@link SuccessResponse} Response message along with HTTP status.
     */
    @PostMapping(Constants.FORGOT_PASSWORD_URI)
    public SuccessResponse<String> forgotPasswordValidation(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get(FieldConstants.USERNAME);
        String response = userService.forgotPassword(username, null, Constants.BOOLEAN_FALSE);
        if (response != null && !response.equals(Constants.EMPTY)) {
            return new SuccessResponse<>(SuccessCode.SEND_EMAIL_USING_SMTP, response, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.FORGOT_LIMIT_EXCEEDED, response, HttpStatus.OK);
        }
    }

    /**
     * <p>
     * Retrieve user by username.
     * </p>
     *
     * @param username {@link String} The username for which the user needs to be retrieved.
     * @return {@link SuccessResponse<User>} Retrieved user information along with HTTP status.
     */
    @GetMapping(Constants.USER_BY_USERNAME_URI)
    public SuccessResponse<User> getUserByUsername(@PathVariable String username) {
        User userByUsername = userService.getUserByUsername(username);
        UserDTO updatedUserDto = mapper.map(userService.updateUser(mapper.map(userByUsername, User.class)), UserDTO.class);
        return new SuccessResponse<>(SuccessCode.GET_USER, updatedUserDto, HttpStatus.OK);
    }

    /**
     * <p>
     * Handle HTTP OPTIONS request for the controller.
     * </p>
     *
     * @return {@link ResponseEntity <Void>} HTTP response with allowed methods.
     */
    @RequestMapping(value = "/options", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS).build();
    }

}
