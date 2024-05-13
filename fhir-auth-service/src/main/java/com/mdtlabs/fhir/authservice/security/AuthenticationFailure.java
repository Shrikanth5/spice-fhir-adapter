package com.mdtlabs.fhir.authservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code AuthenticationFailure} class extends {@link SimpleUrlAuthenticationFailureHandler} and is responsible for handling
 * authentication failures in the Spring Security framework. It sends a response containing an error message and status code to the client.
 * Author: Akash Gopinath
 * Created on: February 28, 2024
 */
public class AuthenticationFailure extends SimpleUrlAuthenticationFailureHandler {

    /**
     * Handles authentication failure and sends a response with an error message and status code to the client.
     *
     * @param request   The {@link HttpServletRequest} containing the request information.
     * @param response  The {@link HttpServletResponse} for sending the response.
     * @param exception The {@link AuthenticationException} with exception information.
     * @throws IOException If an input or output exception occurs while sending the response.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put(Constants.MESSAGE, exception.getMessage());
        response.setContentType(Constants.CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}