package com.mdtlabs.fhir.commonservice.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.io.Serial;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>
 * This is an implementation for converting date fields and its format respective to the user's timezone.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Setter
@Getter
@JsonComponent
public class CustomDateSerializer extends StdSerializer<Date> {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userZoneId;

    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class t) {
        super(t);
    }

    /**
     * <p>
     * This function serializes a given date into a string format with a specified timezone and outputs it
     * using a JSON generator.
     * </p>
     *
     * @param date A Date object representing a specific point in time.
     * @param gen  gen is an instance of the JsonGenerator class, which is used to write JSON content to a
     *             stream or a file
     * @param arg2 SerializerProvider is a class in Jackson library that provides contextual information
     *             for serializers
     */
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2)
            throws IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_TIMEZONE);
        Instant timeStamp = date.toInstant();
        String zoneId = String.valueOf(ZoneId.of(FieldConstants.UTC));
        if (StringUtils.isNotEmpty(userZoneId)
                && !userZoneId.equals("+00:00")) {
            zoneId = userZoneId;
        }
        ZonedDateTime zonedDateTime = timeStamp.atZone(ZoneId.of(zoneId));
        OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
        gen.writeString(offsetDateTime.format(dateTimeFormatter));
    }
}