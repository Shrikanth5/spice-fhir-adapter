package com.mdtlabs.fhir.commonservice.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * DateUtilTest class has the test methods for the DateUtil class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
class DateUtilTest {

    @Test
    void testFormatDate() {
        // then
        assertNull(DateUtil.formatDate("", "Format Str"));
        assertNull(DateUtil.formatDate("2020-03-01", "42"));
        assertNull(DateUtil.formatDate(null));
    }

    @Test
    void testActualDateFormats() {
        Date actualFormatDateResult = DateUtil
                .formatDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        // then
        assertEquals("1970-01-01", (new SimpleDateFormat(TestConstants.SIMPLE_DATE_FORMATE)).format(actualFormatDateResult));
    }

    @Test
    void testGetDateDiffInMinutes() {
        // Arrange
        Date dateFrom = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        // then
        assertEquals(0L, DateUtil.getDateDiffInMinutes(dateFrom,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    }

    @Test
    void getDateDiffInMinutesNullTest() {
        // then
        assertEquals(0L, DateUtil.getDateDiffInMinutes(null, null));
    }
}
