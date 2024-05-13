package com.mdtlabs.fhir.commonservice.common.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import org.junit.jupiter.api.Test;
/**
 * <p>
 * SuccessMessageTest class has the test methods for the SuccessMessage class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
class SuccessMessageTest {

    @Test
    void testNewSuccessMessage() {
        // given
        ArrayList<Object> entityList = new ArrayList<>();
        entityList.add(Constants.ENTRY);
        entityList.add(Constants.ENTRY);
        // when
        SuccessMessage<Object> actualSuccessMessage = new SuccessMessage<>(true, Constants.MESSAGE, "Entity",
                entityList, 1, 3L);
        // then
        assertEquals("Entity", actualSuccessMessage.getEntity());
        assertEquals(Constants.MESSAGE, actualSuccessMessage.getMessage());
        assertEquals(1, actualSuccessMessage.getResponseCode().intValue());
        assertEquals(2, actualSuccessMessage.getEntityList().size());
        assertEquals(3L, actualSuccessMessage.getTotalCount().longValue());
        assertTrue(actualSuccessMessage.isStatus());
    }
}
