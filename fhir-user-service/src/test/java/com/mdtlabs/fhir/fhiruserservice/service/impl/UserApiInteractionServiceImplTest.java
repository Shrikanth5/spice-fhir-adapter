package com.mdtlabs.fhir.fhiruserservice.service.impl;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserApiInteractionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserApiInteractionServiceImplTest {
    @MockBean
    private ApiKeyManagerRepository apiKeyManagerRepository;

    @Autowired
    private UserApiInteractionServiceImpl userApiInteractionServiceImpl;

    /**
     * Method under test:
     * {@link UserApiInteractionServiceImpl#deleteUserApiKeys(Long)}
     */
    @Test
    void testDeleteUserApiKeys() {
        // Arrange
        doNothing().when(apiKeyManagerRepository).deleteByUserId(Mockito.<Long>any());

        // Act
        userApiInteractionServiceImpl.deleteUserApiKeys(TestConstants.ONE_LONG);

        // Assert that nothing has changed
        verify(apiKeyManagerRepository).deleteByUserId(Mockito.<Long>any());
    }
}
