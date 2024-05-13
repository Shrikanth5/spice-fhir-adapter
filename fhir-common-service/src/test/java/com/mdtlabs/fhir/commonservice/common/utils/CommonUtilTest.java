package com.mdtlabs.fhir.commonservice.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * <p>
 * CommonUtilTest class has the test methods for the CommonUtil class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommonUtilTest {

    @Test
    void testGetLoggedInEmployeeLog() {
        // then
        assertEquals("[ null - null ]", CommonUtil.getLoggedInEmployeeLog());
    }

    @Test
    void testGetLoggedInUser() {
        UserDTO actualLoggedInUser = CommonUtil.getLoggedInUser();
        //then
        assertNotNull(actualLoggedInUser);
    }

    @Test
    void testIsNull() {
        // then
        assertTrue(CommonUtil.isNull("P Obj"));
        assertTrue(CommonUtil.isNull(TestConstants.ONE));
        assertFalse(CommonUtil.isNull(null));
        assertFalse(CommonUtil.isNull(new ArrayList<>()));
        assertFalse(CommonUtil.isNull(new HashMap<>()));
        assertFalse(CommonUtil.isNull("    "));
    }

    @Test
    void FormatDateNullTest() {
        // then
        assertNull(CommonUtil.formatDate(null, "Format Str"));
        assertNull(CommonUtil.formatDate("2020-03-01", "42"));
        assertNull(CommonUtil.formatDate(null));
    }

    @Test
    void actualFormatDateTest() {
        Date actualFormatDateResult = CommonUtil
                .formatDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        //then
        assertEquals("1970-01-01", (new SimpleDateFormat(TestConstants.SIMPLE_DATE_FORMATE)).format(actualFormatDateResult));
    }
}
