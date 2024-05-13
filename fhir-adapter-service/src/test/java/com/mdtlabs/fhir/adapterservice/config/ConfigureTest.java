package com.mdtlabs.fhir.adapterservice.config;

import static org.junit.jupiter.api.Assertions.assertSame;

import ca.uhn.fhir.context.FhirContext;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Configure.class})
@ExtendWith(SpringExtension.class)
class ConfigureTest {
    @Autowired
    private Configure configure;

    /**
     * Method under test: {@link Configure#fhirContext()}
     */
    @Test
    void testFhirContext() {
        FhirContext actualFhirContextResult = configure.fhirContext();

        Set<String> expectedUnicodeLocaleKeys = actualFhirContextResult.getParserOptions()
                .getDontStripVersionsFromReferencesAtPaths();
        assertSame(expectedUnicodeLocaleKeys, actualFhirContextResult.getLocalizer().getLocale().getUnicodeLocaleKeys());
    }
}
