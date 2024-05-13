package com.mdtlabs.fhir.fhiruserservice.controller;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.dto.ApiKeyManagerDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessCode;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessResponse;
import com.mdtlabs.fhir.fhiruserservice.service.ApiKeyManagerService;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ApiKeyManagerControllerTest {

    @InjectMocks
    private ApiKeyManagerController apiKeyManagerController;

    @Mock
    private ModelMapper mapper;

    @Mock
    private ApiKeyManagerService apiKeyManagerService;


    @Test
    void getApiKeysByUserIdTest() {
        //given
        Map<String, String> request = Map.of(Constants.USER_ID_PARAM, TestConstants.ONE_STRING);
        Long userId = Long.parseLong(request.get(Constants.USER_ID_PARAM));
        List<ApiKeyManager> apiKeyManagers = List.of();
        SuccessResponse<List<ApiKeyManagerDTO>> response = new SuccessResponse(SuccessCode.GET_API_KEY_MANAGERS,
                Constants.NO_DATA_LIST, HttpStatus.OK);
        //when
        when(apiKeyManagerService.getApiKeysByUserId(userId)).thenReturn(apiKeyManagers);
        //then
        SuccessResponse<List<ApiKeyManagerDTO>> actualResponse = apiKeyManagerController.getApiKeysByUserId(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void generateApiKeyTest() {
        //given
        User user = TestDataProvider.getUser();
        SuccessResponse<ApiKeyManagerDTO> response = new SuccessResponse<>(SuccessCode.USER_ID_DOES_NOT_EXISTS,
                Optional.empty(), HttpStatus.NOT_FOUND);
        //when
        when(apiKeyManagerService.generateApiKey(user)).thenReturn(null);
        //then
        SuccessResponse<ApiKeyManagerDTO> actualResponse = apiKeyManagerController.generateApiKey(user);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    void deleteApiKeyManagerById() {
        //given
        Map<String, String> request = Map.of(Constants.USER_ID_PARAM, TestConstants.ONE_STRING);

        Long userId = Long.parseLong(request.get(Constants.USER_ID_PARAM));
        SuccessResponse<Boolean> response = new SuccessResponse<>(SuccessCode.API_KEY_MANAGER_NOT_FOUND,
                Boolean.FALSE, HttpStatus.OK);
        //when
        when(apiKeyManagerService.deleteApiKeyManagerByUserId(userId)).thenReturn(Boolean.FALSE);
        //then
        SuccessResponse<Boolean> actualResponse = apiKeyManagerController.deleteApiKeyManagerById(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void deleteApiKeyManagerByIdWithTrue() {
        //given
        Map<String, String> request = Map.of(Constants.USER_ID_PARAM, TestConstants.ONE_STRING);
        Long userId = Long.parseLong(request.get(Constants.USER_ID_PARAM));
        SuccessResponse<Boolean> response = new SuccessResponse<>(SuccessCode.API_KEY_MANAGER_DELETE,
                Boolean.TRUE, HttpStatus.OK);
        //when
        when(apiKeyManagerService.deleteApiKeyManagerByUserId(userId)).thenReturn(Boolean.TRUE);
        //then
        SuccessResponse<Boolean> actualResponse = apiKeyManagerController.deleteApiKeyManagerById(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    void validateApiKeyTest() {
        //given
        Map<String, String> request = Map.of(Constants.ACCESS_KEY_ID_PARAM, Constants.ACCESS_KEY_ID_PARAM,
                Constants.SECRET_ACCESS_KEY_PARAM, Constants.SECRET_ACCESS_KEY_PARAM);
        String accessKeyId = request.get(Constants.ACCESS_KEY_ID_PARAM);
        String secretAccessKey = request.get(Constants.SECRET_ACCESS_KEY_PARAM);
        SuccessResponse<String> response = new SuccessResponse<>(SuccessCode.VALID_API_KEY,
                Constants.VALID_API, HttpStatus.OK);
        //when
        when(apiKeyManagerService.validateApiKey(accessKeyId, secretAccessKey)).thenReturn(Boolean.TRUE);
        //then
        SuccessResponse<String> actualResponse = apiKeyManagerController.validateApiKey(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void inValidateApiKeyTest() {
        //given
        Map<String, String> request = Map.of(Constants.ACCESS_KEY_ID_PARAM, Constants.ACCESS_KEY_ID_PARAM,
                Constants.SECRET_ACCESS_KEY_PARAM, Constants.SECRET_ACCESS_KEY_PARAM);
        String accessKeyId = request.get(Constants.ACCESS_KEY_ID_PARAM);
        String secretAccessKey = request.get(Constants.SECRET_ACCESS_KEY_PARAM);
        SuccessResponse<String> response = new SuccessResponse<>(SuccessCode.INVALID_API_KEY,
                Constants.INVALID_API, HttpStatus.UNAUTHORIZED);
        //when
        when(apiKeyManagerService.validateApiKey(accessKeyId, secretAccessKey)).thenReturn(Boolean.FALSE);
        //then
        SuccessResponse<String> actualResponse = apiKeyManagerController.validateApiKey(request);
        Assertions.assertEquals(response, actualResponse);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testGetApiKeysByUserId() {
        //given
        Map<String, String> request = new HashMap<>();
        request.put(Constants.USER_ID_PARAM, "1");
        ApiKeyManager apiKeyManager = TestDataProvider.getApiKeyManager();

        //when
        when(apiKeyManagerService.getApiKeysByUserId(1L)).thenReturn(List.of(apiKeyManager));
        List<ApiKeyManagerDTO> mockApiKeyManagerDTOs = new ArrayList<>();
        when(mapper.map(Mockito.any(List.class), Mockito.any())).thenReturn(mockApiKeyManagerDTOs);
        SuccessResponse<List<ApiKeyManagerDTO>> response = apiKeyManagerController.getApiKeysByUserId(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGenerateApiKeyWhenApiKeyManagerIsNotNull() {
        // given
        User user = TestDataProvider.getUser();
        ApiKeyManager apiKeyManager = TestDataProvider.getApiKeyManager();
        Mockito.when(apiKeyManagerService.generateApiKey(user)).thenReturn(apiKeyManager);
        ApiKeyManagerDTO mockApiKeyManagerDTO = new ApiKeyManagerDTO();

        //when
        when(mapper.map(apiKeyManager, ApiKeyManagerDTO.class)).thenReturn(mockApiKeyManagerDTO);
        SuccessResponse<ApiKeyManagerDTO> response = apiKeyManagerController.generateApiKey(user);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
