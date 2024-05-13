package com.mdtlabs.fhir.commonservice.common.utils;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;

import static com.mdtlabs.fhir.commonservice.common.utils.StringUtil.getDateString;

/**
 * <p>
 * Utility class for common functionalities related to date operations.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
public class CommonUtil {

    /*
     * Logger instance for logging date conversion details
     */
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /*
     * Private constructor to prevent instantiation of the utility class
     */
    private CommonUtil() {
    }

    /*
     * Get the logged-in employee as a formatted string.
     */
    public static String getLoggedInEmployeeLog() {
        UserDTO user = getLoggedInUser();
        String userId = String.valueOf(user.getId());
        String username = user.getUsername();
        return constructString(Constants.OPEN_SQUARE_BRACKET, userId, Constants.HYPHEN, username,
                Constants.CLOSE_SQUARE_BRACKET);
    }

    /*
     * Get the logged-in user object.
     */
    public static UserDTO getLoggedInUser() {
        return new UserDTO();
    }

    /*
     * Common method to concatenate strings using StringJoiner.
     */
    public static String constructString(String... args) {
        StringJoiner buildString = new StringJoiner(Constants.SPACE);
        for (String arg : args) {
            buildString.add(arg);
        }
        return buildString.toString();
    }

    /*
     * Used to check null condition for multiple objects.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNull(Object... pObj) {
        boolean isNull = false;
        if (pObj == null) {
            return false;
        }

        for (Object lObj : pObj) {
            if (lObj == null || (lObj instanceof String && ((String) lObj).trim().isEmpty()) ||
                    (lObj instanceof Collection && ((Collection) lObj).isEmpty()) ||
                    (lObj instanceof Map && ((Map) lObj).isEmpty())) {
                isNull = true;
            }
        }

        return !isNull;
    }

    /*
     * Used to format the date using the default (yyyy-MM-dd HH:mm:ss) format.
     */
    public static Date formatDate(Date date) {
        return isNull(date) ? formatDate(getDateString(date), Constants.JSON_DATE_FORMAT) : null;
    }

    /*
     * Used to format the date using a specific format.
     */
    public static Date formatDate(String dateStr, String formatStr) {
        DateFormat df;
        Date date = null;
        try {
            if (isNull(dateStr, formatStr)) {
                df = new SimpleDateFormat(formatStr);
                date = df.parse(dateStr);
                date = new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
            }
        } catch (ParseException exception) {
            logger.error(String.valueOf(exception));
            return null;
        }
        return date;
    }

    public static String getAuthToken() {
        return Constants.BEARER + UserContextHolder.getUserDto().getAuthorization();
    }
}
