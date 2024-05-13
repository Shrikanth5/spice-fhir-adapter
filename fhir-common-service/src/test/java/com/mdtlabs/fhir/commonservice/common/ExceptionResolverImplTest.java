package com.mdtlabs.fhir.commonservice.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

/**
 * <p>
 * ExceptionResolverImplTest class has the test methods for the ExceptionResolverImpl class.
 * </p>
 *
 * @author Dilip N created on April 01, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ExceptionResolverImplTest {
    @InjectMocks
    ExceptionResolverImpl exceptionResolver;

    @Test
    void testResolveError() {
        ErrorMessage actualResolveErrorResult = exceptionResolver.resolveError(HttpStatus.CONTINUE, TestConstants.MESSAGE);
        //then
        assertEquals(TestConstants.MESSAGE, actualResolveErrorResult.getException());
        assertEquals(TestConstants.MESSAGE, actualResolveErrorResult.getMessage());
        assertEquals(100, actualResolveErrorResult.getErrorCode().intValue());
        assertFalse(actualResolveErrorResult.getStatus());
    }
}
