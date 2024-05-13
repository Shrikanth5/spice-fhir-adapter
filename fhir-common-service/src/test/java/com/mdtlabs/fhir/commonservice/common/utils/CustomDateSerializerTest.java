package com.mdtlabs.fhir.commonservice.common.utils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

/**
 * <p>
 * CustomDateSerializerTest class has the test methods for the CustomDateSerializer class.
 * </p>
 *
 * @author Dilip N created on Mar 25, 2024
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CustomDateSerializerTest {

    @InjectMocks
    CustomDateSerializer customDateSerializer;

    @Test
    void testSerialize() throws IOException {
        // given
        Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        CustomDateSerializer customDateSerializer = new CustomDateSerializer();
        doNothing().when(jsonGenerator).writeString(Mockito.<String>any());
        JsonGeneratorDelegate gen = new JsonGeneratorDelegate(new JsonGeneratorDelegate(jsonGenerator), true);
        customDateSerializer.serialize(date, gen, new DefaultSerializerProvider.Impl());
        // then
        verify(jsonGenerator).writeString(eq("1970-01-01T00:00:00+00:00"));
    }

    @Test
    void serializeDifferentZoneIdTest() throws IOException {
        // Arrange
        customDateSerializer.setUserZoneId("+01:00");
        Date date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        doNothing().when(jsonGenerator).writeString(Mockito.<String>any());
        JsonGeneratorDelegate gen = new JsonGeneratorDelegate(new JsonGeneratorDelegate(jsonGenerator), true);
        customDateSerializer.serialize(date, gen, new DefaultSerializerProvider.Impl());
        // then
        verify(jsonGenerator).writeString(eq("1970-01-01T01:00:00+01:00"));
    }
}
