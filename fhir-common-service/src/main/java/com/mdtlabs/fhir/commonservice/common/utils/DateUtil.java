package com.mdtlabs.fhir.commonservice.common.utils;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * This is used to calculate the past day from particular date
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class DateUtil {

    private DateUtil() {
    }


    /**
     * <p>
     * This method is used to format date.
     * </p>
     *
     * @param dateStr   input date
     * @param formatStr format type
     * @return Date  converted date into specified format
     */
    public static Date formatDate(String dateStr, String formatStr) {
        DateFormat df;
        Date date = null;
        try {
            if (CommonUtil.isNull(dateStr, formatStr)) {
                df = new SimpleDateFormat(formatStr);
                date = df.parse(dateStr);
                date = new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
            }
        } catch (ParseException ex) {
            return null;
        }
        return date;
    }

    /**
     * <p>
     * This method is used to get a minute difference.
     * </p>
     *
     * @param dateFrom from date
     * @param dateTo   till date
     * @return difference of minutes
     */
    public static long getDateDiffInMinutes(Date dateFrom, Date dateTo) {

        if (null == dateFrom || null == dateTo) {
            return 0;
        }

        long differenceInTime = dateTo.getTime() - dateFrom.getTime();

        long differenceInYears = TimeUnit.MILLISECONDS.toDays(differenceInTime) / 365;
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceInTime) % 365;
        long differenceInHours = TimeUnit.MILLISECONDS.toHours(differenceInTime) % 24;
        long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(differenceInTime) % 60;

        return ((differenceInYears * 365 * 24 * 60) + (differenceInDays * 24 * 60) + (differenceInHours * 60)
                + differenceInMinutes);
    }

    /**
     * <p>
     * This method is used to format date on Date.
     * </p>
     *
     * @param date date to be formatted
     * @return Date  formatted date
     */
    public static Date formatDate(Date date) {
        return CommonUtil.isNull(date) ? formatDate(StringUtil.getDateString(date), Constants.JSON_DATE_FORMAT) : null;
    }
}
