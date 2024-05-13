package com.mdtlabs.fhir.authservice.security;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.model.entity.UserToken;
import com.mdtlabs.fhir.commonservice.common.service.UserTokenService;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {LogoutSuccess.class})
@ExtendWith(SpringExtension.class)
class LogoutSuccessTest {
    @Autowired
    private LogoutSuccess logoutSuccess;

    @MockBean
    private UserTokenService userTokenService;

    /**
     * Method under test:
     * {@link LogoutSuccess#logout(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testLogout() {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        Response response = new Response();

        // Act and Assert
        assertThrows(DataNotFoundException.class,
                () -> logoutSuccess.logout(request, response, new TestingAuthenticationToken("Principal", "Credentials")));
    }

    /**
     * Method under test:
     * {@link LogoutSuccess#logout(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testLogoutFailure() {
        // Arrange
        HttpServletRequestWrapper request = new HttpServletRequestWrapper(new MockHttpServletRequest());
        Response response = new Response();

        // Act and Assert
        assertThrows(DataNotFoundException.class,
                () -> logoutSuccess.logout(request, response, new TestingAuthenticationToken("Principal", "Credentials")));
    }

    /**
     * Method under test:
     * {@link LogoutSuccess#logout(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testLogoutSuccess() {
        UserToken userToken = TestDataProvider.getUserToken();
        doNothing().when(userTokenService).deleteUserTokenByToken(Mockito.<String>any());
        when(userTokenService.findByAuthToken(Mockito.<String>any())).thenReturn(userToken);
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        Response response = new Response();

        // Act
        logoutSuccess.logout(request, response, new TestingAuthenticationToken("Principal", "Credentials"));

        verify(userTokenService).deleteUserTokenByToken(eq("https://example.org/example"));
        verify(userTokenService).findByAuthToken(eq("https://example.org/example"));
        verify(request).getHeader(eq("Authorization"));
    }
}
