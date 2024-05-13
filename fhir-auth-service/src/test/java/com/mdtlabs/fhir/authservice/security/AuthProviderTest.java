package com.mdtlabs.fhir.authservice.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mdtlabs.fhir.authservice.repository.UserRepository;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.TimezoneDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.RoleRepository;
import com.mdtlabs.fhir.commonservice.common.utils.CustomDateSerializer;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * <p>
 * AuthProviderTest class has the test methods for the AuthProvider class.
 * </p>
 *
 * @author Dilip N created on April 02, 2024
 */
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(SpringExtension.class)
class AuthProviderTest {

   @InjectMocks
    AuthProvider authProvider;

    @Mock
    UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CustomDateSerializer customDateSerializer;

    @Test
    void authenticateBlankUsernamePasswordTest() throws AuthenticationException {
        // then
        assertThrows(BadCredentialsException.class,
            () -> authProvider.authenticate(new RememberMeAuthenticationToken("Key", "Principal", new ArrayList<>())));
    }

    @Test
    void authenticateInvalidUserTest() throws AuthenticationException {
        // given
        Authentication authentication = new UsernamePasswordAuthenticationToken(TestConstants.USER_NAME, TestConstants.PASSWORD);
        // then
        assertThrows(BadCredentialsException.class,
                () -> authProvider.authenticate(authentication));
    }

    @Test
    void testSupports() {
        Class<Object> authentication = Object.class;
        // then
        assertFalse(authProvider.supports(authentication));
    }
    @Test
    void testAuthenticateIsActiveRole() {
        //given
        Authentication authentication = mock(Authentication.class);
        Timezone timezone = new Timezone();
        timezone.setOffset("00:00:00");
        TimezoneDTO timezoneDTO = new TimezoneDTO();
        timezoneDTO.setOffset("00:00:00");
        List<Role> roles = List.of(TestDataProvider.getRole());
        User user = TestDataProvider.getUser();
        user.setTimezone(timezone);
        user.setInvalidLoginAttempts(TestConstants.FOUR);
        ModelMapper modelMapper = new ModelMapper();
        Timezone convertedTimeZone = modelMapper.map(user.getTimezone(), Timezone.class);

        ReflectionTestUtils.setField(authProvider,"loginCountLimit",10);
        //when
        doNothing().when(customDateSerializer).setUserZoneId(convertedTimeZone.getOffset());
        when(userRepository.getUserByUsername("superuser@test.com", Boolean.TRUE)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.getAllRoles(Boolean.TRUE)).thenReturn(roles);
        when(authentication.getCredentials()).thenReturn("spice123");
        when(authentication.getPrincipal()).thenReturn("superuser@test.com");

        //then
        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));
    }

    @Test
    void testAuthenticateInvalidAttempts() {
        //given
        Authentication authentication = mock(Authentication.class);
        Timezone timezone = new Timezone();
        timezone.setOffset("00:00:00");
        TimezoneDTO timezoneDTO = new TimezoneDTO();
        timezoneDTO.setOffset("00:00:00");
        List<Role> roles = List.of(TestDataProvider.getRole());
        User user = TestDataProvider.getUser();
        user.setTimezone(timezone);
        user.setInvalidLoginAttempts(TestConstants.FOUR);
        ModelMapper modelMapper = new ModelMapper();
        Timezone convertedTimeZone = modelMapper.map(user.getTimezone(), Timezone.class);

        //when
        doNothing().when(customDateSerializer).setUserZoneId(convertedTimeZone.getOffset());
        when(userRepository.getUserByUsername("superuser@test.com", Boolean.TRUE)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.getAllRoles(Boolean.TRUE)).thenReturn(roles);
        when(authentication.getCredentials()).thenReturn("spice123");
        when(authentication.getPrincipal()).thenReturn("superuser@test.com");

        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));
    }

    @Test
    void testAuthenticateInvalidLoginAttempts() {
        //given
        Authentication authentication = mock(Authentication.class);
        Timezone timezone = new Timezone();
        timezone.setOffset("00:00:00");
        TimezoneDTO timezoneDTO = new TimezoneDTO();
        timezoneDTO.setOffset("00:00:00");
        List<Role> roles = List.of(TestDataProvider.getRole());
        User user = TestDataProvider.getUser();
        user.setTimezone(timezone);
        user.setInvalidLoginAttempts(TestConstants.FOUR);
        Set<GrantedAuthority> authorityList = user.getAuthorities();
        ModelMapper modelMapper = new ModelMapper();
        Timezone convertedTimeZone = modelMapper.map(user.getTimezone(), Timezone.class);
        ReflectionTestUtils.setField(authProvider,"loginCountLimit",5);
        //when
        doNothing().when(customDateSerializer).setUserZoneId(convertedTimeZone.getOffset());

        when(userRepository.getUserByUsername("superuser@test.com", Boolean.TRUE)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(roleRepository.getAllRoles(Boolean.TRUE)).thenReturn(roles);
        when(authentication.getCredentials()).thenReturn("spice123");
        when(authentication.getPrincipal()).thenReturn("superuser@test.com");

        //then
        assertThrows(BadCredentialsException.class, () -> authProvider.authenticate(authentication));
    }

}
