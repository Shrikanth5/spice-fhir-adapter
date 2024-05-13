package com.mdtlabs.fhir.commonservice.common;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class MessageValidatorTest {
    @InjectMocks
    MessageValidator messageValidator;

    @Test
    void getMessage() {
        //then
        String actualMessage = messageValidator.getMessage(TestConstants.STATUS_CODE, TestConstants.ERROR);
        assertNotNull(actualMessage);
        assertEquals(TestConstants.INVALID_TOKEN, actualMessage);
    }

    @Test
    void testGetMessageWithArgs() {
        //then
        TestDataProvider.init();
        TestDataProvider.getMessageValidatorMock();
        String actualMessage = messageValidator.getMessage(TestConstants.STATUS_CODE,
                TestConstants.ERROR, TestConstants.ARGUMENT, TestConstants.MESSAGE);
        TestDataProvider.cleanUp();
        Assertions.assertNull(actualMessage);

    }

    @Test
    void testGetMessage() {
        //then
        TestDataProvider.init();
        TestDataProvider.getMessageValidatorMock();
        String actualMessage = messageValidator.getMessage(TestConstants.STATUS_CODE,
                TestConstants.ERROR, List.of(TestConstants.ARGUMENT, TestConstants.MESSAGE));
        TestDataProvider.cleanUp();
        Assertions.assertNull(actualMessage);
    }

    @Test
    void testGetMessageWithArg() {
        TestDataProvider.init();
        TestDataProvider.getMessageValidatorMock();
        String actualMessage = messageValidator.getMessage(TestConstants.STATUS_CODE, TestConstants.ERROR, TestConstants.ARGUMENT);
        TestDataProvider.cleanUp();
        assertNotNull(actualMessage);
        assertEquals(TestConstants.MESSAGE, actualMessage);
    }

    @Test
    void getMessageSuccess() {
        //then
        TestDataProvider.init();
        TestDataProvider.getMessageValidatorMock();
        String actualMessage = messageValidator.getMessage("1001", TestConstants.SUCCESS);
        TestDataProvider.cleanUp();

        assertEquals(TestConstants.USER_SUCCESS, actualMessage);
    }


}
