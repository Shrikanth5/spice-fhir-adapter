package com.mdtlabs.fhir.adapterservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FhirAdapterServiceApplicationTests {

    private static final String ARG = "";
    private static final String[] ARGS = new String[]{ARG};

    @InjectMocks
    AdapterServiceApplication adapterServiceApplication;

    @Autowired
    ConfigurableApplicationContext context;

    @Test
    void testMain() {
        try (MockedStatic<AdapterServiceApplication> adapterServiceApplicationMockedStatic = Mockito.mockStatic(AdapterServiceApplication.class);
             MockedStatic<SpringApplication> springStatic = Mockito.mockStatic(
                     SpringApplication.class)) {
            adapterServiceApplicationMockedStatic.when(() -> AdapterServiceApplication.main(ARGS))
                    .thenCallRealMethod();
            springStatic.when(() -> SpringApplication.run(AdapterServiceApplication.class, ARGS))
                    .thenReturn(context);

            // when
            adapterServiceApplication.main(ARGS);

            //then
            adapterServiceApplicationMockedStatic.verify(
                    () -> AdapterServiceApplication.main(ARGS),
                    times(1));
            springStatic.verify(
                    () -> SpringApplication.run(AdapterServiceApplication.class, ARGS),
                    times(1));
        }
    }

    @Test
    void testRestTemplate() {
        // Arrange and Act
        RestTemplate actualRestTemplateResult = (new AdapterServiceApplication()).restTemplate();

        // Assert
        List<HttpMessageConverter<?>> messageConverters = actualRestTemplateResult.getMessageConverters();
        assertEquals(6, messageConverters.size());
    }
}
