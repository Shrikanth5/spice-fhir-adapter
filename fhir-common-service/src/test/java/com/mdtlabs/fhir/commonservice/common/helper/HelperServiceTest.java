package com.mdtlabs.fhir.commonservice.common.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.util.Map;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * <p>
 * HelperServiceTest class has the test methods for the HelperService class.
 * </p>
 *
 * @author Dilip N created on Mar 26, 2024
 */
@ContextConfiguration(classes = {HelperService.class})
@ExtendWith(SpringExtension.class)
class HelperServiceTest {
    @Autowired
    private HelperService helperService;

    @Test
    void testGetAccessSecretKey() {
        ReflectionTestUtils.setField(helperService, "accessKey", TestConstants.ACCESS_ID);
        ReflectionTestUtils.setField(helperService, "secretKey", TestConstants.ACCESS_KEY);
        // Arrange and Act
        Map<String, String> actualAccessSecretKey = helperService.getAccessSecretKey();
        // Assert
        assertEquals(2, actualAccessSecretKey.size());
        assertEquals(TestConstants.ACCESS_ID, actualAccessSecretKey.get(Constants.ACCESS_KEY_ID_PARAM));
        assertEquals(TestConstants.ACCESS_KEY, actualAccessSecretKey.get(Constants.SECRET_ACCESS_KEY_PARAM));
    }
}
