package com.mdtlabs.fhir.commonservice.common.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * <p>
 * ExceptionMessageTest class has the test methods for the ExceptionMessage class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExceptionMessageTest {

    @InjectMocks
    ExceptionMessage exceptionMessage;

    @Test
    public void testToString() {
        // given
        exceptionMessage.setDateTime(1234567890L);
        exceptionMessage.setStatus(true);
        exceptionMessage.setErrorCode(500);
        exceptionMessage.setMessage("Test message");
        exceptionMessage.setException("Test exception");
        String result = exceptionMessage.toString();
        //then
        assertEquals("\"ExceptionMessage{\"dateTime=1234567890, status=true, errorCode=500, message='Test message''', exception='Test exception'''}",
                result);
    }
}
