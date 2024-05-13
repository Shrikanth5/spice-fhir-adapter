package com.mdtlabs.fhir.fhirlistenerservice;

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

/**
 * <p>
 *   This class has the test methods for the Listener Service main class.
 * </p>
 *
 * @author Divya S created on March 03, 2023
 */
@ExtendWith(MockitoExtension.class)
public class FhirListenerServiceApplicationTest {
    private static final String ARG = "";
    private static final String[] ARGS = new String[]{ARG};

    @InjectMocks
    FhirListenerServiceApplication fhirListenerServiceApplication;

    @Autowired
    ConfigurableApplicationContext context;

    @Test
    void testMain() {
        try (MockedStatic<FhirListenerServiceApplication> fhirListenerServiceApplicationMockedStatic = Mockito.mockStatic(FhirListenerServiceApplication.class);
             MockedStatic<SpringApplication> springStatic = Mockito.mockStatic(
                     SpringApplication.class)) {
            fhirListenerServiceApplicationMockedStatic.when(() -> FhirListenerServiceApplication.main(ARGS))
                    .thenCallRealMethod();
            springStatic.when(() -> SpringApplication.run(FhirListenerServiceApplication.class, ARGS))
                    .thenReturn(context);

            // when
            fhirListenerServiceApplication.main(ARGS);

            //then
            fhirListenerServiceApplicationMockedStatic.verify(
                    () -> fhirListenerServiceApplication.main(ARGS),
                    times(1));
            springStatic.verify(
                    () -> SpringApplication.run(FhirListenerServiceApplication.class, ARGS),
                    times(1));
        }
    }
}
