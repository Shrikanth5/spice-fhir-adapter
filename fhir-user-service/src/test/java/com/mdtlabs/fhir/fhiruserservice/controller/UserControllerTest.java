package com.mdtlabs.fhir.fhiruserservice.controller;

import com.mdtlabs.fhir.commonservice.common.model.dto.UserProfileDTO;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserRequestDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessCode;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessResponse;
import com.mdtlabs.fhir.fhiruserservice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.InheritingConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    @Test
    void addUserTest() {
        //given
        UserRequestDTO userRequestDTO = TestDataProvider.getUserRequestDTO();
        UserDTO userDTO = TestDataProvider.getUserDTO();
        User user = TestDataProvider.getUser();

        //when
        when(mapper.getConfiguration()).thenReturn(new InheritingConfiguration());
        when(userService.addUser(user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);

        //then
        SuccessResponse<UserDTO> actualResponse = userController.addUser(userRequestDTO);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    void getUserByIdTest() {
        //given
        Long userId = 1l;
        User user = TestDataProvider.getUser();
        UserDTO userDTO = TestDataProvider.getUserDTO();
        SuccessResponse<UserDTO> response = new SuccessResponse<>(SuccessCode.GET_USER,
                userDTO, HttpStatus.OK);
        //when
        when(userService.getUserById(userId)).thenReturn(user);
        //then
        SuccessResponse<UserDTO> actualResponse = userController.getUserById(userId);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    void getUserByIdNullTest() {
        //given
        Long userId = 1l;
        SuccessResponse<UserDTO> response = new SuccessResponse<>(SuccessCode.USER_NOT_FOUND,
                new UserDTO(), HttpStatus.NOT_FOUND);
        //when
        when(userService.getUserById(userId)).thenReturn(null);
        //then
        SuccessResponse<UserDTO> actualResponse = userController.getUserById(userId);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    void getUserByNameTest() {
        //given
        String userName = Constants.USERNAME;
        Map<String, String> request = Map.of("username", Constants.USERNAME);
        User user = TestDataProvider.getUser();
        UserDTO userDTO = TestDataProvider.getUserDTO();
        SuccessResponse<UserDTO> response = new SuccessResponse<>(SuccessCode.GET_USER,
                userDTO, HttpStatus.OK);
        //when
        when(userService.getUserByUsername(userName)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        //then
        SuccessResponse<User> actualResponse = userController.getUserByUsername(request);
      //  Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    void getUserByNameNullTest() {
        //given
        String userName = Constants.USERNAME;
        Map<String, String> request = Map.of("userName", Constants.USERNAME);
        SuccessResponse<UserDTO> response = new SuccessResponse<>(SuccessCode.USER_NOT_FOUND,
                Optional.empty(), HttpStatus.NOT_FOUND);
        //when
        when(userService.getUserByUsername(userName)).thenReturn(null);
        //then
        SuccessResponse<User> actualResponse = userController.getUserByUsername(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    void getUsersTest() {
        //given
        int pageNumber = 1;
        User user = TestDataProvider.getUser();
        List<User> users = List.of(user);
        List<UserDTO> userDTOS = List.of(TestDataProvider.getUserDTO());
        SuccessResponse<List<UserDTO>> response = new SuccessResponse(SuccessCode.GET_USERS,
                userDTOS, 1, HttpStatus.OK);

        //when
        when(mapper.map(users, new TypeToken<List<UserDTO>>() {
        }.getType())).thenReturn(userDTOS);
        when(userService.getUsers(pageNumber)).thenReturn(users);
        when(userService.getTotalSize()).thenReturn(1);
        //then
        SuccessResponse<List<UserDTO>> actualResponse = userController.getUsers(pageNumber);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    void getUsersWithEmptyTest() {
        //given
        int pageNumber = 1;
        List<User> users = List.of();
        SuccessResponse<List<UserDTO>> response = new SuccessResponse(SuccessCode.GET_USERS,
                Constants.NO_DATA_LIST, HttpStatus.OK);

        //when
        when(userService.getUsers(pageNumber)).thenReturn(users);
        //then
        SuccessResponse<List<UserDTO>> actualResponse = userController.getUsers(pageNumber);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    void updateUserTest() {
        //given
        User user = TestDataProvider.getUser();
        UserDTO userDTO = TestDataProvider.getUserDTO();
        Map<String, User> request = Map.of(Constants.USER, user);
        SuccessResponse<UserDTO> response = new SuccessResponse<>(SuccessCode.USER_UPDATE, userDTO, HttpStatus.OK);
        //when
        when(mapper.map(user, User.class)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(userService.updateUser(user)).thenReturn(user);
        //then
        SuccessResponse<UserDTO> actualResponse = userController.updateUser(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void resetUserPassword() {
        //given
        Map<String, String> userInfo = Map.of(FieldConstants.TOKEN, "", FieldConstants.PASSWORD, "");
        Map<String, Object> result = Map.of(Constants.USER, TestDataProvider.getUser());
        SuccessResponse<Map<String, Boolean>> response = new SuccessResponse<>(SuccessCode.SET_PASSWORD,
                result, HttpStatus.OK);
        //when
        when(userService.resetUserPassword(userInfo.get(FieldConstants.TOKEN),
                userInfo.get(FieldConstants.PASSWORD))).thenReturn(result);
        //then
        SuccessResponse<Map<String, Boolean>> actualResponse = userController.resetUserPassword(userInfo);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void deleteUserByIDTest() {
        //given
        UserDTO user = TestDataProvider.getUserDTO();
        SuccessResponse<Boolean> response = new SuccessResponse<>(SuccessCode.USER_DELETE, Boolean.TRUE, HttpStatus.OK);
        //when
        when(userService.deleteUserById(user.getId())).thenReturn(Boolean.TRUE);
        //then
        SuccessResponse<Boolean> actualResponse = userController.deleteUserById(user);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void forgotPasswordValidationTest() {
        //given
        Map<String, String> request = Map.of(Constants.USERNAME, Constants.USERNAME);
        SuccessResponse<String> response = new SuccessResponse<>(SuccessCode.SEND_EMAIL_USING_SMTP,
                "Token Validated", HttpStatus.OK);
        //when
        when(userService.forgotPassword(Constants.USERNAME, null, Boolean.FALSE)).thenReturn("Token Validated");
        //then
        SuccessResponse<String> actualResponse = userController.forgotPasswordValidation(request);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void getUserProfileByIdTest() {
        //given
        Map<String, String> request = Map.of(Constants.USER_ID_PARAM, "1");
        User user = TestDataProvider.getUser();
        UserProfileDTO userProfileDTO = TestDataProvider.getUserProfileDTO();
        //when
        when(userService.getUserById(Long.valueOf(request.get(Constants.USER_ID_PARAM)))).thenReturn(user);
        when(mapper.map(userProfileDTO, UserProfileDTO.class)).thenReturn(userProfileDTO);
        SuccessResponse<UserProfileDTO> actualResponse = userController.getUserProfileById(request);
        //then
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void getUserProfileByIdNullTest() {

        //given
        Map<String, String> request = Map.of(Constants.USER_ID_PARAM, "1");
        User user = null;
        UserProfileDTO userProfileDTO = TestDataProvider.getUserProfileDTO();

        //when
        when(userService.getUserById(Long.valueOf(request.get(Constants.USER_ID_PARAM)))).thenReturn(user);
        when(mapper.map(userProfileDTO, UserProfileDTO.class)).thenReturn(userProfileDTO);
        SuccessResponse<UserProfileDTO> actualResponse = userController.getUserProfileById(request);

        //then
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    void forgotPasswordValidationEmailTest() {

        //given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(FieldConstants.USERNAME, "exampleUser");

        //when
        when(userService.forgotPassword("exampleUser", null, Constants.BOOLEAN_FALSE))
                .thenReturn("SomeResponse");
        SuccessResponse<String> result = userController.forgotPasswordValidation(requestBody);

        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testGetUserByUsername() {

        // given
        String username = "exampleUser";
        User user = TestDataProvider.getUser();
        UserDTO userDTO = TestDataProvider.getUserDTO();

        // when
        when(userService.getUserByUsername(username)).thenReturn(user);
        when(userService.updateUser(Mockito.any(User.class))).thenReturn(user);
        when(mapper.map(Mockito.any(User.class), Mockito.eq(UserDTO.class))).thenReturn(userDTO);
        SuccessResponse<User> result = userController.getUserByUsername(username);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void testOptions() {
        ResponseEntity<Void> response = userController.options();
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getHeaders().containsKey("Allow"));
    }

    @Test
    void testAddUser() {
        // given
        UserRequestDTO requestUserDto = new UserRequestDTO();
        UserDTO mockUserDto = TestDataProvider.getUserDTO();
        User user = TestDataProvider.getUser();

        //when
        Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(UserRequestDTO.class), Mockito.eq(User.class))).thenReturn(new User());
        Mockito.when(mapper.map(Mockito.any(User.class), Mockito.eq(UserDTO.class))).thenReturn(mockUserDto);
        SuccessResponse<UserDTO> response = userController.addUser(requestUserDto);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).addUser(Mockito.any(User.class));
    }
}
