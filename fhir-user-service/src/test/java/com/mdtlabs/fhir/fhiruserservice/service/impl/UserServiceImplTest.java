package com.mdtlabs.fhir.fhiruserservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.UserTokenRepository;
import com.mdtlabs.fhir.commonservice.common.service.impl.UserTokenServiceImpl;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import com.mdtlabs.fhir.fhiruserservice.mapper.UserMapper;
import com.mdtlabs.fhir.fhiruserservice.repository.UserRepository;
import com.mdtlabs.fhir.fhiruserservice.service.RoleService;
import com.mdtlabs.fhir.fhiruserservice.service.UserApiInteractionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    UserTokenRepository userTokenRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    UserApiInteractionService userApiInteractionService;

    @Mock
    UserTokenServiceImpl userTokenService;

    @Mock
    RoleService roleService;

    @Test
    void testAddUserSample() {

        // Arrange
        User user = new User();

        when(userRepository.save(user)).thenReturn(TestDataProvider.getUser());
        when(roleService.getRoleById(TestConstants.TWO_LONG)).thenReturn(TestDataProvider.getRole());
        userServiceImpl.addUser(user);

        verify(userRepository, atLeastOnce()).save(user);

    }

    @Test
    void testAddExistingUser() {

        // Arrange
        User user = TestDataProvider.getUser();

        when(userRepository.findByUsernameAndIsDeletedFalse(user.getUsername())).thenReturn(user);

        assertThrows(FhirValidation.class, () -> userServiceImpl.addUser(user));

    }
    @Test
    void testGetTotalSize(){
        List<User> users = List.of(TestDataProvider.getUser());
        when(userRepository.getUsers(Boolean.TRUE)).thenReturn(users);
        int expected = TestConstants.ONE.intValue();
        int actual = userServiceImpl.getTotalSize();
        assertEquals(expected,actual);
    }

    /**
     * Method under test: {@link UserServiceImpl#findByUserIdAndIsActiveTrue(Long)}
     */
    @Test
    void testFindByUserIdAndIsActiveTrue() {

        // Arrange
        User user = TestDataProvider.getUser();
        when(userRepository.findByIdAndIsActiveTrue(Mockito.<Long>any())).thenReturn(user);

        // Act
        User actualFindByUserIdAndIsActiveTrueResult = (new UserServiceImpl(userRepository, userMapper,
                new UserTokenServiceImpl(userTokenRepository), userApiInteractionService,
                roleService)).findByUserIdAndIsActiveTrue(TestConstants.ONE_LONG);

        // Assert
        verify(userRepository).findByIdAndIsActiveTrue(Mockito.<Long>any());
        assertSame(user, actualFindByUserIdAndIsActiveTrueResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(Long)}
     */
    @Test
    void deleteUserByIdFhirValidationTest() {

        assertThrows(FhirValidation.class,
                () -> (new UserServiceImpl(userRepository, userMapper,
                        new UserTokenServiceImpl(userTokenRepository), userApiInteractionService,
                        roleService)).deleteUserById(TestConstants.ZERO_LONG));
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(Long)}
     */
    @Test
    void testDeleteUserById() {

        // Arrange
        User user = TestDataProvider.getUser();
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        doNothing().when(userTokenRepository).deleteByUserId(Mockito.<Long>any());

        doNothing().when(userApiInteractionService).deleteUserApiKeys(Mockito.<Long>any());

        // Act
        boolean actualDeleteUserByIdResult = (new UserServiceImpl(userRepository, userMapper, userTokenService,
                userApiInteractionService, roleService)).deleteUserById(TestConstants.ONE_LONG);

        // Assert
        assertTrue(actualDeleteUserByIdResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByUsername(String)}
     */
    @Test
    void testGetUserByUsername() {

        // Arrange
        User user = TestDataProvider.getUser();
        when(userRepository.getUserByUsername(Mockito.<String>any(), Mockito.<Boolean>any())).thenReturn(user);
        // Act
        User actualUserByUsername = (new UserServiceImpl(userRepository, userMapper,
                new UserTokenServiceImpl(userTokenRepository), userApiInteractionService,
                roleService)).getUserByUsername(TestConstants.NAME);
        // Assert
        verify(userRepository).getUserByUsername(eq(TestConstants.NAME), Mockito.<Boolean>any());
        assertSame(user, actualUserByUsername);
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers(int)}
     */

    @Test
    void testGetUsers() {
        //given
        ReflectionTestUtils.setField(userServiceImpl, TestConstants.GRID_DISPLAY_VALUE, TestConstants.TEN);
        Pageable pageable = PageRequest.of(Constants.ZERO, TestConstants.TEN);
        List<User> usersList = TestDataProvider.getUsers();
        Page<User> users = new PageImpl<>(usersList);

        //when
        when(userRepository.getUsers(Boolean.TRUE, pageable)).thenReturn(users);

        //then
        List<User> actualUsers = userServiceImpl.getUsers(TestConstants.ONE);
        assertNotNull(actualUsers);
        assertFalse(actualUsers.isEmpty());
        assertEquals(usersList.get(Constants.ZERO), actualUsers.get(Constants.ZERO));
    }

    /**
     * Method under test: {@link UserServiceImpl#getUsers(int)}
     */
    @Test
    void testGetUsersWithNull() {
        //given
        ReflectionTestUtils.setField(userServiceImpl, TestConstants.GRID_DISPLAY_VALUE, TestConstants.TEN);
        Pageable pageable = PageRequest.of(Constants.ZERO, TestConstants.TEN);

        //when
        when(userRepository.getUsers(Boolean.TRUE, pageable)).thenReturn(null);

        //then
        List<User> actualUsers = userServiceImpl.getUsers(TestConstants.ONE);
        assertNotNull(actualUsers);
        assertTrue(actualUsers.isEmpty());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(User)}
     */
    @Test
    void testUpdateUser() {
        // Arrange
        User user = TestDataProvider.getUser();
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));

        UserServiceImpl userServiceImpl = new UserServiceImpl(userRepository, userMapper,
                new UserTokenServiceImpl(userTokenRepository), userApiInteractionService,
                roleService);

        // Act
        User actualUpdateUserResult = userServiceImpl.updateUser(user);

        // Assert
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
        assertSame(user, actualUpdateUserResult);
    }


    @Test
    void testUpdateIdNull(){
        User user = new User();
        assertThrows(DataNotAcceptableException.class,() -> userServiceImpl.updateUser(user));
    }

    @Test
    void testUpdateIdCheck(){
        User user = TestDataProvider.getUser();
        User user1 = new User();
        user1.setId(TestConstants.TWO_LONG);
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user));
        assertThrows(FhirValidation.class,() -> userServiceImpl.updateUser(user1));
    }

    @Test
    void resetUserPasswordDataNotFoundExceptionTest() {
        // Arrange
        String token = "validToken";
        String password = TestConstants.PASSWORD;

        assertThrows(DataNotFoundException.class, () -> userServiceImpl.resetUserPassword(token, password));
    }

    @Test
    void testResetUserPasswordSamePassword() {
        // Arrange
        String token = "validToken";
        String password = "samePassword";
        User user = TestDataProvider.getUser();
        user.setPassword(password); // Set the same password
        when(userRepository.getUserByUsername(user.getUsername(),Boolean.TRUE)).thenReturn(user);

        assertThrows(DataNotFoundException.class,() -> userServiceImpl.resetUserPassword(token, password));
    }

    @Test
    void testForgotPasswordUserExistsAndLimitNotExceeded() {
        //given
        User user = TestDataProvider.getUser();
        userServiceImpl.forgotPassword("testEmail@example.com", user, false);
        //then
        verify(userRepository,atLeastOnce()).save(user);
    }

    @Test
    void testForgotPasswordForgotPasswordLimitExceeded() {
        // given
        User user = TestDataProvider.getUser();
        user.setForgetPasswordCount(0);
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.HOUR_OF_DAY, +1);
        Date oneHourBefore = calendar.getTime();
        user.setForgetPasswordTime(oneHourBefore);
        userServiceImpl.forgotPassword("testEmail@example.com", user, false);
        //then
        verify(userRepository,atLeast(0)).save(user);
    }

    @Test
    void deleteUserByIdNullTest() {
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        assertFalse(userServiceImpl.deleteUserById(1L));
    }

    @Test
    void resetUserPasswordDataNotAcceptableExceptionTest() {
        // Arrange
        String token = TestConstants.KEY;
        // then
        assertThrows(DataNotAcceptableException.class, () -> userServiceImpl.resetUserPassword(token, null));
    }

    @Test
    void forgotPasswordUserNotExistTest() {
        // when
        when(userRepository.getUserByUsername(TestConstants.EMAIL, Boolean.TRUE)).thenReturn(null);
        String result = userServiceImpl.forgotPassword(TestConstants.EMAIL, null, Boolean.FALSE);
        // then
        assertNull(result);
    }
}
