package com.mdtlabs.fhir.authservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FhirAuthServiceApplicationTests {

    private static final String ARG = "";
    private static final String[] ARGS = new String[]{ARG};

    @InjectMocks
    FhirAuthServiceApplication fhirAuthServiceApplication;

    @Autowired
    ConfigurableApplicationContext context;

    @Test
    void testMain() {
        try (MockedStatic<FhirAuthServiceApplication> FhirAuthServiceApplicationMockedStatic = Mockito.mockStatic(FhirAuthServiceApplication.class);
             MockedStatic<SpringApplication> springStatic = Mockito.mockStatic(
                     SpringApplication.class)) {
            FhirAuthServiceApplicationMockedStatic.when(() -> FhirAuthServiceApplication.main(ARGS))
                    .thenCallRealMethod();
            springStatic.when(() -> SpringApplication.run(FhirAuthServiceApplication.class, ARGS))
                    .thenReturn(context);

            // when
            fhirAuthServiceApplication.main(ARGS);

            //then
            FhirAuthServiceApplicationMockedStatic.verify(
                    () -> FhirAuthServiceApplication.main(ARGS),
                    times(1));
        }
    }
}
