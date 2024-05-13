package com.mdtlabs.fhir.commonservice.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * ErrorMessageTest class has the test methods for the ErrorMessage class.
 * </p>
 *
 * @author Dilip N created on Mar 27, 2024
 */
class ErrorMessageTest {

    @Test
    void testEqualsAndHashCode() {
        ErrorMessage.ErrorMessageBuilder errorMessageBuilder = mock(ErrorMessage.ErrorMessageBuilder.class);
        when(errorMessageBuilder.dateTime(anyLong())).thenReturn(ErrorMessage.builder());
        ErrorMessage expectedBuildResult = TestDataProvider.getErrorMessage();
        ErrorMessage buildResult = errorMessageBuilder.dateTime(TestConstants.ONE)
                .errorCode(-1)
                .exception("Exception")
                .message(TestConstants.EXCEPTION_MESSAGE)
                .status(true)
                .build();
        int notExpectedHashCodeResult = buildResult.hashCode();
        //then
        assertEquals(expectedBuildResult,buildResult);
        assertNotEquals(notExpectedHashCodeResult, expectedBuildResult.hashCode());
        assertEquals(buildResult, buildResult);
    }

    @Test
    void testEquals() {
        ErrorMessage buildResult = TestDataProvider.getErrorMessage();
        //then
        assertNotEquals(buildResult, "Different type to ErrorMessage");
    }

    @Test
    void testToString() {
        ErrorMessage buildResult = TestDataProvider.getErrorMessage();
        String actualToStringResult = buildResult.toString();
        //then
        assertEquals(Boolean.TRUE.toString(), actualToStringResult);
    }
}
