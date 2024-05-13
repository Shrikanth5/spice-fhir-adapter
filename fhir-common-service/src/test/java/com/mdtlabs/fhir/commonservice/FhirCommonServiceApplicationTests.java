package com.mdtlabs.fhir.commonservice;

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
class FhirCommonServiceApplicationTests {
    private static final String ARG = "";
    private static final String[] ARGS = new String[]{ARG};

    @InjectMocks
    FhirCommonServiceApplication fhirCommonServiceApplication;

    @Autowired
    ConfigurableApplicationContext context;

    @Test
    void testMain() {
        try (MockedStatic<FhirCommonServiceApplication> fhirCommonServiceApplicationMockedStatic = Mockito.mockStatic(FhirCommonServiceApplication.class);
             MockedStatic<SpringApplication> springStatic = Mockito.mockStatic(
                     SpringApplication.class)) {
            fhirCommonServiceApplicationMockedStatic.when(() -> FhirCommonServiceApplication.main(ARGS))
                    .thenCallRealMethod();
            springStatic.when(() -> SpringApplication.run(FhirCommonServiceApplication.class, ARGS))
                    .thenReturn(context);

            // when
            fhirCommonServiceApplication.main(ARGS);

            //then
            fhirCommonServiceApplicationMockedStatic.verify(
                    () -> FhirCommonServiceApplication.main(ARGS),
                    times(1));
            springStatic.verify(
                    () -> SpringApplication.run(FhirCommonServiceApplication.class, ARGS),
                    times(1));
        }
    }

}
