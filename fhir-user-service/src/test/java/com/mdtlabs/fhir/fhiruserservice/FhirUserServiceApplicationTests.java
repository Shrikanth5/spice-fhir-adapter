package com.mdtlabs.fhir.fhiruserservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FhirUserServiceApplicationTests {
    private static final String ARG = "";
    private static final String[] ARGS = new String[]{ARG};

    @InjectMocks
    FhirUserServiceApplication userServiceMain;

    @Autowired
    ConfigurableApplicationContext context;

    @Test
    void testMain() {
        try (MockedStatic<FhirUserServiceApplication> userServiceMainMockedStatic = Mockito.mockStatic(FhirUserServiceApplication.class);
             MockedStatic<SpringApplication> springStatic = Mockito.mockStatic(
                     SpringApplication.class)) {
            userServiceMainMockedStatic.when(() -> FhirUserServiceApplication.main(ARGS))
                    .thenCallRealMethod();
            springStatic.when(() -> SpringApplication.run(FhirUserServiceApplication.class, ARGS))
                    .thenReturn(context);

            // when
            FhirUserServiceApplication.main(ARGS);

            //then
            userServiceMainMockedStatic.verify(
                    () -> FhirUserServiceApplication.main(ARGS),
                    times(1));
            springStatic.verify(
                    () -> SpringApplication.run(FhirUserServiceApplication.class, ARGS),
                    times(1));
        }
    }

}
