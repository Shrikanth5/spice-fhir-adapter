package com.mdtlabs.fhir.authservice.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {AuthenticationFailure.class})
@ExtendWith(SpringExtension.class)
class AuthenticationFailureTest {
    @Autowired
    private AuthenticationFailure authenticationFailure;

    /**
     * Method under test:
     * {@link AuthenticationFailure#onAuthenticationFailure(HttpServletRequest, HttpServletResponse, AuthenticationException)}
     */
    @Test
    void testOnAuthenticationFailure() throws IOException {
        //given
        AuthenticationException authenticationException = new AuthenticationException("exception") {
        };
        HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest();

        //then
        authenticationFailure.onAuthenticationFailure(httpServletRequest, httpServletResponse, authenticationException);
        assertEquals("exception", authenticationException.getMessage());
    }
}
