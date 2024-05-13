package com.mdtlabs.fhir.commonservice.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import org.junit.jupiter.api.Test;

class DateUtilityTest {

    @Test
    void testGetDateDiffInMinutes() {
        Date dateFrom = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        // then
        assertEquals(0L, DateUtility.getDateDiffInMinutes(dateFrom,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    }
}
