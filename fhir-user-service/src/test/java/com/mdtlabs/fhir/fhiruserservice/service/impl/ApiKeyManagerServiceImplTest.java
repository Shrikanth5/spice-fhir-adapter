package com.mdtlabs.fhir.fhiruserservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.commonservice.common.repository.ApiKeyManagerRepository;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import com.mdtlabs.fhir.fhiruserservice.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Unit Test case for the ApiKeyManagerServiceImpl
 * <p>
 * Author: Hemavardhini Jothi
 * Created on February 27, 2024
 */
@ContextConfiguration(classes = {ApiKeyManagerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ApiKeyManagerServiceImplTest {
    @MockBean
    private ApiKeyManagerRepository apiKeyManagerRepository;

    @Autowired
    private ApiKeyManagerServiceImpl apiKeyManagerServiceImpl;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link ApiKeyManagerServiceImpl#getApiKeysByUserId(Long)}
     */
    @Test
    void testGetApiKeysByUserId() {
        // Arrange
        ArrayList<ApiKeyManager> apiKeyManagerList = new ArrayList<>();
        when(apiKeyManagerRepository.findByUserId(Mockito.<Long>any())).thenReturn(apiKeyManagerList);

        // Act
        List<ApiKeyManager> actualApiKeysByUserId = apiKeyManagerServiceImpl.getApiKeysByUserId(TestConstants.ONE_LONG);

        // Assert
        verify(apiKeyManagerRepository).findByUserId(Mockito.<Long>any());
        assertTrue(actualApiKeysByUserId.isEmpty());
        assertSame(apiKeyManagerList, actualApiKeysByUserId);
    }

    /**
     * Method under test:
     * {@link ApiKeyManagerServiceImpl#deleteApiKeyManagerByUserId(Long)}
     */
    @Test
    void testDeleteApiKeyManagerByUserId() {
        // Arrange
        doNothing().when(apiKeyManagerRepository).deleteByUserId(Mockito.<Long>any());
        when(apiKeyManagerRepository.existsByUserId(Mockito.<Long>any())).thenReturn(Boolean.TRUE);

        // Act
        boolean actualDeleteApiKeyManagerByUserIdResult = apiKeyManagerServiceImpl.deleteApiKeyManagerByUserId(TestConstants.ONE_LONG);

        // Assert
        verify(apiKeyManagerRepository).deleteByUserId(Mockito.<Long>any());
        verify(apiKeyManagerRepository).existsByUserId(Mockito.<Long>any());
        assertTrue(actualDeleteApiKeyManagerByUserIdResult);
    }

    /**
     * Method under test:
     * {@link ApiKeyManagerServiceImpl#deleteApiKeyManagerByUserId(Long)}
     */
    @Test
    void testDeleteApiKeyManagerByUserIdNegative() {
        // Arrange
        when(apiKeyManagerRepository.existsByUserId(Mockito.<Long>any())).thenReturn(Boolean.FALSE);

        // Act
        boolean actualDeleteApiKeyManagerByUserIdResult = apiKeyManagerServiceImpl.deleteApiKeyManagerByUserId(TestConstants.ONE_LONG);

        // Assert
        verify(apiKeyManagerRepository).existsByUserId(Mockito.<Long>any());
        assertFalse(actualDeleteApiKeyManagerByUserIdResult);
    }

    /**
     * Method under test: {@link ApiKeyManagerServiceImpl#generateApiKey(User)}
     */
    @Test
    void testGenerateApiKey() {

        // Arrange
        Country country = TestDataProvider.getCountry();
        Timezone timezone = TestDataProvider.getTimezone();
        User user = TestDataProvider.getUser();
        ApiKeyManager apiKeyManager = TestDataProvider.getApiKey();

        user.setCountry(country);
        user.setTimezone(timezone);
        apiKeyManager.setUser(user);

        when(apiKeyManagerRepository.save(Mockito.<ApiKeyManager>any())).thenReturn(apiKeyManager);
        when(userService.findByUserIdAndIsActiveTrue(Mockito.<Long>any())).thenReturn(user);

        // Act
        ApiKeyManager actualGenerateApiKeyResult = apiKeyManagerServiceImpl.generateApiKey(user);

        // Assert
        verify(userService).findByUserIdAndIsActiveTrue(Mockito.<Long>any());
        verify(apiKeyManagerRepository).save(Mockito.<ApiKeyManager>any());
        assertSame(apiKeyManager, actualGenerateApiKeyResult);
    }

    /**
     * Method under test:
     * {@link ApiKeyManagerServiceImpl#validateApiKey(String, String)}
     */
    @Test
    void testValidateApiKey() {

        // Arrange
        Country country = TestDataProvider.getCountry();
        Timezone timezone = TestDataProvider.getTimezone();
        User user = TestDataProvider.getUser();
        ApiKeyManager apiKeyManager = TestDataProvider.getApiKey();

        user.setCountry(country);
        user.setTimezone(timezone);
        apiKeyManager.setUser(user);

        when(apiKeyManagerRepository.findByAccessKeyIdAndSecretAccessKey(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(apiKeyManager);

        // Act
        boolean actualValidateApiKeyResult = apiKeyManagerServiceImpl.validateApiKey(TestConstants.ACCESS_ID,
                TestConstants.ACCESS_KEY);

        // Assert
        verify(apiKeyManagerRepository).findByAccessKeyIdAndSecretAccessKey((TestConstants.ACCESS_ID),(TestConstants.ACCESS_KEY));
        assertTrue(actualValidateApiKeyResult);

    }

    @Test
    void generateApiKeyNullTest() {
        // given
        User user = TestDataProvider.getUser();
        // when
        when(userService.findByUserIdAndIsActiveTrue(TestConstants.ONE_LONG)).thenReturn(null);
        ApiKeyManager apiKeyManagerResult = apiKeyManagerServiceImpl.generateApiKey(user);
        // then
        assertNull(apiKeyManagerResult);
    }
    @Test
    void validateApiKeyNullTest() {
        // when
        when(apiKeyManagerRepository.findByAccessKeyIdAndSecretAccessKey(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(null);
        boolean actualValidateApiKeyResult = apiKeyManagerServiceImpl.validateApiKey(TestConstants.ACCESS_ID,
                TestConstants.ACCESS_KEY);
        // then
        assertFalse(actualValidateApiKeyResult);

    }
}
