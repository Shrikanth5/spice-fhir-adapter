package com.mdtlabs.fhir.commonservice.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * StringUtilTest class has the test methods for the StringUtil class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
class StringUtilTest {

    @Test
    void testGetDateString() {
        // then
        assertNull(StringUtil.getDateString(null));
        assertNull(StringUtil.getDateString(
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "Format Str"));
        assertNull(StringUtil.getDateString(null, "Format Str"));
        assertEquals(TestConstants.FOURTY_TWO, StringUtil
                .getDateString(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42"));
        assertNull(StringUtil.getDateString(mock(java.sql.Date.class), "Format Str"));
    }

    @Test
    void testConstructString() {
      // then
      assertEquals(TestConstants.ARGS, StringUtil.constructString(TestConstants.ARGS));
    }
}
