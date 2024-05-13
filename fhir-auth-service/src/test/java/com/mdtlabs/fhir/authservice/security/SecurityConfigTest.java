package com.mdtlabs.fhir.authservice.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequestWrapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private HttpSecurity http;

    @InjectMocks
    private SecurityConfig securityConfig;


    /**
     * Method under test: {@link SecurityConfig#corsConfigurationSource()}
     */
    @Test
    void testCorsConfigurationSource() {

        // Arrange and Act
        CorsConfigurationSource actualCorsConfigurationSourceResult = (new SecurityConfig()).corsConfigurationSource();

        // Assert
        assertNull(actualCorsConfigurationSourceResult.getCorsConfiguration(new MockHttpServletRequest()));
    }

    /**
     * Method under test: {@link SecurityConfig#corsConfigurationSource()}
     */
    @Test
    void testCorsConfigurationSource2() {
        // Arrange and Act
        CorsConfigurationSource actualCorsConfigurationSourceResult = (new SecurityConfig()).corsConfigurationSource();
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getAttribute(Mockito.<String>any())).thenReturn("Attribute");
        CorsConfiguration actualCorsConfiguration = actualCorsConfigurationSourceResult.getCorsConfiguration(request);

        // Assert
        verify(request, atLeast(1)).getAttribute(Mockito.<String>any());
        assertNull(actualCorsConfiguration);
    }


    @Test
    void passwordEncoder() {
        //then
        PasswordEncoder actualPasswordEncoder = securityConfig.passwordEncoder();
        assertNotNull(actualPasswordEncoder);
    }

    @Test
    void authenticationSuccess() {
        //then
        AuthenticationSuccess authenticationSuccess = securityConfig.authenticationSuccess();
        assertNotNull(authenticationSuccess);
    }
}
