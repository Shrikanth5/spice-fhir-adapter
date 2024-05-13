package com.mdtlabs.fhir.commonservice.common.config;

import com.mdtlabs.fhir.commonservice.common.DataConflictException;
import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.exception.BadRequestException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotAcceptableException;
import com.mdtlabs.fhir.commonservice.common.exception.DataNotFoundException;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.exception.UnauthorizedException;
import feign.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * <p>
 * RetreiveMessageErrorDecoderTest class has the test methods for the RetreiveMessageErrorDecoder class.
 * </p>
 *
 * @author Dilip N created on Mar 26, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RetrieveMessageErrorDecoderTest {

    @InjectMocks
    RetreiveMessageErrorDecoder retreiveMessageErrorDecoder;

    @Test
    void decodeBadRequestExceptionTest() throws IOException {
        Exception exception = handleDecodeException(400);
        // then
        assertEquals(BadRequestException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }
    @Test
    void decodeBadRequestExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(400);
        // then
        assertEquals(BadRequestException.class, exception.getClass());
        assertEquals(ErrorConstants.BAD_REQUEST, exception.getMessage());
    }

    @Test
    void decodeUnauthorizedExceptionTest() throws IOException {
        Exception exception = handleDecodeException(401);
        // then
        assertEquals(UnauthorizedException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }
    @Test
    void decodeUnauthorizedExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(401);
        // then
        assertEquals(UnauthorizedException.class, exception.getClass());
        assertEquals(ErrorConstants.UNAUTHORIZED, exception.getMessage());
    }

    @Test
    void decodeDataNotFoundExceptionTest() throws IOException {
        Exception exception = handleDecodeException(404);
        // then
        assertEquals(DataNotFoundException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void decodeDataNotFoundExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(404);
        // then
        assertEquals(DataNotFoundException.class, exception.getClass());
        assertEquals(ErrorConstants.DATA_NOT_FOUND, exception.getMessage());
    }

    @Test
    void decodeDataNotAcceptableExceptionTest() throws IOException {
        Exception exception = handleDecodeException(406);
        // then
        assertEquals(DataNotAcceptableException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void decodeDataNotAcceptableExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(406);
        // then
        assertEquals(DataNotAcceptableException.class, exception.getClass());
        assertEquals(ErrorConstants.DATA_NOT_ACCEPTABLE, exception.getMessage());
    }

    @Test
    void decodeRequestTimeOutExceptionTest() throws IOException {
        Exception exception = handleDecodeException(408);
        // then
        assertEquals(RequestTimeOutException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void decodeRequestTimeOutExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(408);
        // then
        assertEquals(RequestTimeOutException.class, exception.getClass());
        assertEquals(ErrorConstants.REQUEST_TIME_OUT, exception.getMessage());
    }
    @Test
    void decodeDataConflictExceptionTest() throws IOException {
        Exception exception = handleDecodeException(409);
        // then
        assertEquals(DataConflictException.class, exception.getClass());
        assertEquals(TestConstants.CUSTOM_ERROR_MESSAGE, exception.getMessage());
    }
    @Test
    void decodeDataConflictExceptionMessageNullTest() throws IOException {
        Exception exception = handleDecodeExceptionMessageNull(409);
        // then
        assertEquals(DataConflictException.class, exception.getClass());
        assertEquals(ErrorConstants.DATA_CONFLICT, exception.getMessage());

    }
    private Exception handleDecodeException(Integer errorCode) throws IOException {
        // given
        Response.Body bodyMock = Mockito.mock(Response.Body.class);
        Response responseMock = Mockito.mock(Response.class);
        //when
        when(bodyMock.asInputStream()).thenReturn(new ByteArrayInputStream("{\"message\":\"Custom error message\"}"
                .getBytes(StandardCharsets.UTF_8)));
        when(responseMock.status()).thenReturn(errorCode);
        when(responseMock.body()).thenReturn(bodyMock);
        return retreiveMessageErrorDecoder.decode("methodKey", responseMock);
    }

    private Exception handleDecodeExceptionMessageNull(Integer errorCode) throws IOException {
        // given
        Response.Body bodyMock = Mockito.mock(Response.Body.class);
        Response responseMock = Mockito.mock(Response.class);
        //when
        when(bodyMock.asInputStream()).thenReturn(new ByteArrayInputStream("{\"\":\"\"}"
                .getBytes(StandardCharsets.UTF_8)));
        when(responseMock.status()).thenReturn(errorCode);
        when(responseMock.body()).thenReturn(bodyMock);
        return retreiveMessageErrorDecoder.decode("methodKey", responseMock);
    }

    @Test
    void handleIOException() throws IOException {
        // given
        Response.Body bodyMock = Mockito.mock(Response.Body.class);
        Response responseMock = Mockito.mock(Response.class);
        InputStream emptyInputStream = new ByteArrayInputStream(new byte[0]);
        //when
        when(bodyMock.asInputStream()).thenReturn(emptyInputStream);
        when(responseMock.body()).thenReturn(bodyMock);
        Exception exception = retreiveMessageErrorDecoder.decode("methodKey", responseMock);
        //then
        assertEquals(Exception.class, exception.getClass());
    }
}
