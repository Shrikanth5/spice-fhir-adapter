package com.mdtlabs.fhir.commonservice.common;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mdtlabs.fhir.commonservice.common.exception.Validation;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import com.mdtlabs.fhir.commonservice.common.repository.UserTokenRepository;
import com.mdtlabs.fhir.commonservice.common.utils.CustomDateSerializer;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashSet;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {AuthenticationFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
class AuthenticationFilterTest {
    @MockBean
    private ApiKeyManagerRepository apiKeyManagerRepository;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @MockBean
    private CustomDateSerializer customDateSerializer;

    @MockBean
    private UserTokenRepository userTokenRepository;

    /**
     * Method under test:
     * {@link AuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternal() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertThrows(Validation.class,
                () -> authenticationFilter.doFilterInternal(request, new Response(), mock(FilterChain.class)));
    }

    /**
     * Method under test:
     * {@link AuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)}
     */
    @Test
    void testDoFilterInternalValidation() {
        // Arrange

        Country country = TestDataProvider.getCountry();
        Timezone timezone = TestDataProvider.getTimezone();
        User user = TestDataProvider.getUser();
        user.setCountry(country);
        user.setTimezone(timezone);
        ApiKeyManager apiKeyManager = TestDataProvider.getApiKeyManager();

        when(apiKeyManagerRepository.findByAccessKeyIdAndSecretAccessKey(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(apiKeyManager);
        HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
        when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
        when(request.getRequestURI()).thenReturn("https://example.org/example");

        // Act and Assert
        assertThrows(Validation.class,
                () -> authenticationFilter.doFilterInternal(request, new Response(), mock(FilterChain.class)));
    }
}
