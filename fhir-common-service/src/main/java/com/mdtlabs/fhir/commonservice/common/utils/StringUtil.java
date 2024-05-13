package com.mdtlabs.fhir.commonservice.common.utils;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;


/**
 * <p>
 * String utils for string validation etc.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    /**
     * <p>
     * This method is used to get date string
     * </p>
     *
     * @param date      input date
     * @param formatStr format type of string
     * @return String  date string
     */
    public static String getDateString(Date date, String formatStr) {
        String dateStr = null;
        DateFormat df;
        try {
            if (CommonUtil.isNull(date, formatStr)) {
                df = new SimpleDateFormat(formatStr);
                dateStr = df.format(date);
            }
        } catch (Exception ex) {
            return null;
        }
        return dateStr;
    }

    /**
     * <p>
     * This method is used to get date string
     * </p>
     *
     * @param date input date
     * @return String  date string
     */
    public static String getDateString(Date date) {
        return getDateString(date, Constants.JSON_DATE_FORMAT);
    }


    /**
     * <p>
     * Common method to append string using the ${@code StringJoiner} and return as
     * combine string.
     * </p>
     *
     * @param args Get list of string.
     * @return String  Combine the string using String Joiner and return as string.
     */
    public static String constructString(String... args) {
        StringJoiner buildString = new StringJoiner(Constants.SPACE);
        for (String arg : args) {
            buildString.add(arg);
        }
        return buildString.toString();
    }

}
