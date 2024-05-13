package com.mdtlabs.fhir.commonservice.common.utils;

import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for handling date-related operations.
 */
@Slf4j
public class DateUtility {

    private DateUtility() {
    }

    /*
     * This method is used to get the user preferred
     * timezone.
     *
     */
    public static long getDateDiffInMinutes(Date dateFrom, Date dateTo) {

        if (null == dateFrom || null == dateTo) {
            throw new FhirValidation(2203);
        }

        long differenceInTime = dateTo.getTime() - dateFrom.getTime();

        long differenceInYears = TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365;
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInTime) % 365;
        long differenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInTime) % 24;
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60;

        return ((differenceInYears * 365 * 24 * 60)
                + (differenceInDays * 24 * 60)
                + (differenceInHours * 60)
                + differenceInMinutes);
    }

}