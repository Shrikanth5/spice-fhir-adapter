package com.mdtlabs.fhir.commonservice.common;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.ServicesException;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

/**
 * <p>
 * ServicesExceptionHandlerTest class has the test methods for the ServicesExceptionHandler class.
 * </p>
 *
 * @author Dilip N created on April 05, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServicesExceptionHandlerTest {

    @InjectMocks
    ServicesExceptionHandler servicesExceptionHandler;
    @Mock
    private ExceptionResolver resolver;
    @Mock
    private ExceptionResolverImpl exceptionResolverImpl;

    @Test
    void testRuntimeExceptionHandler() {
        //given
        RuntimeException runtimeException = new RuntimeException();
        ErrorMessage errorMessage = TestDataProvider.getErrorMessage();
        //when
        when(resolver.resolveError(HttpStatus.INTERNAL_SERVER_ERROR, runtimeException.getMessage())).thenReturn(errorMessage);
        ErrorMessage expectedErrorMessage = servicesExceptionHandler.runtimeExceptionHandler(runtimeException);
        //then
        assertEquals(TestConstants.EXCEPTION_MESSAGE, expectedErrorMessage.getMessage());

    }

    @Test
    void testExceptionHandler() {
        //given
        Exception exception = new Exception();
        ErrorMessage errorMessage = TestDataProvider.getErrorMessage();
        //when
        when(resolver.resolveError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage())).thenReturn(errorMessage);
        ErrorMessage expectedErrorMessage = servicesExceptionHandler.exceptionHandler(exception);
        //then
        assertEquals(TestConstants.EXCEPTION_MESSAGE, expectedErrorMessage.getMessage());

    }

    @Test
    void testAuthExceptionHandler() {
        //given
        Exception exception = new Exception();
        ErrorMessage errorMessage = TestDataProvider.getErrorMessage();
        //when
        when(resolver.resolveError(HttpStatus.UNAUTHORIZED, exception.getMessage())).thenReturn(errorMessage);
        ErrorMessage expectedErrorMessage = servicesExceptionHandler.authExceptionHandler(exception);
        //then
        assertEquals(TestConstants.EXCEPTION_MESSAGE, expectedErrorMessage.getMessage());

    }

    @Test
    void handleDataConflictReturnsConflict() {
        // Arrange
        DataConflictException ex = new DataConflictException(TestConstants.MESSAGE);
        when(resolver.resolveError(eq(HttpStatus.CONFLICT), anyString())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 400, TestConstants.MESSAGE, "DataConflictException"));

        // Act
        ErrorMessage errorMessage = servicesExceptionHandler.handleDataConflict(ex);

        // Assert
        assertTrue(errorMessage.getStatus());
        verify(resolver).resolveError(eq(HttpStatus.CONFLICT), anyString());
    }

    @Test
    void testHandleException() {
        // given
        ServicesException servicesException = new ServicesException(TestConstants.MESSAGE);
        // when
        when(resolver.resolveError(HttpStatus.BAD_REQUEST, servicesException.getMessage())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 400, TestConstants.MESSAGE, "BAD_REQUEST"));
        servicesExceptionHandler.handleException(servicesException);
        // then
        verify(resolver).resolveError(HttpStatus.BAD_REQUEST, servicesException.getMessage());

    }

    @Test
    void testHandleDataNotFoundException() {
        // given
        DataNotFoundException exception = new DataNotFoundException(TestConstants.MESSAGE);
        // when
        when(resolver.resolveError(HttpStatus.NOT_FOUND, exception.getMessage())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 404, TestConstants.MESSAGE, "DataNotFoundException"));
        servicesExceptionHandler.handleDataNotFoundException(exception);
        // then
        verify(resolver).resolveError(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testHandleBadRequest() {
        // given
        BadRequestException exception = new BadRequestException(TestConstants.MESSAGE);
        // when
        when(resolver.resolveError(HttpStatus.BAD_REQUEST, exception.getMessage())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 400, TestConstants.MESSAGE, "BAD_REQUEST"));
        servicesExceptionHandler.handleBadRequest(exception);
        // then
        verify(resolver).resolveError(HttpStatus.BAD_REQUEST, exception.getMessage());

    }

    @Test
    void testHandleDataNotAcceptable() {
        // given
        DataNotAcceptableException exception = new DataNotAcceptableException(TestConstants.MESSAGE);
        // when
        when(resolver.resolveError(HttpStatus.NOT_ACCEPTABLE, exception.getMessage())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 400, TestConstants.MESSAGE, "NOT_ACCEPTABLE"));
        servicesExceptionHandler.handleDataNotAcceptable(exception);
        // then
        verify(resolver).resolveError(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());

    }

    @Test
    public void testInitiateErrorCodeToResolver() {
        // given
        ServicesExceptionHandler servicesException = new ServicesExceptionHandler();
        servicesException.initiateErrorCodeToResolver();
        // then
        verifyNoInteractions(exceptionResolverImpl);
    }

    @Test
    public void testHandleRequestTimeOutException() {
        // given
        RequestTimeOutException exception = new RequestTimeOutException(TestConstants.MESSAGE);
        // when
        when(resolver.resolveError(HttpStatus.REQUEST_TIMEOUT, exception.getMessage())).thenReturn(new ErrorMessage(TestConstants.ONE,
                Boolean.TRUE, 408, TestConstants.MESSAGE, "REQUEST_TIMEOUT"));
        servicesExceptionHandler.handleRequestTimeOutException(exception);
        // then
        verify(resolver).resolveError(HttpStatus.REQUEST_TIMEOUT, exception.getMessage());
    }
}
