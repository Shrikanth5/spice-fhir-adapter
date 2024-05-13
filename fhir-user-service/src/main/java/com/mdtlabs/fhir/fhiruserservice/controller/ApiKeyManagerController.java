package com.mdtlabs.fhir.fhiruserservice.controller;

import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.ApiKeyManagerDTO;
import com.mdtlabs.fhir.commonservice.common.model.entity.ApiKeyManager;
import com.mdtlabs.fhir.commonservice.common.model.entity.User;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessCode;
import com.mdtlabs.fhir.fhiruserservice.message.SuccessResponse;
import com.mdtlabs.fhir.fhiruserservice.service.ApiKeyManagerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * The {@code ApiKeyManagerController} class represents a RESTful controller for managing API key-related operations.
 * It provides endpoints for checking the availability of the API key management service, retrieving API keys
 * associated with a specific user, generating a new API key for a user, and deleting an API key associated with a user.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 12, 2024
 */
@RestController
@RequestMapping(Constants.API_KEY_MANAGER_BASE_URI)
public class ApiKeyManagerController {

    /**
     * <p>
     * ModelMapper instance for converting between DTOs and entities.
     * </p>
     */
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * <p>
     * Service for managing API key operations.
     * </p>
     */
    private final ApiKeyManagerService apiKeyManagerService;

    public ApiKeyManagerController(ApiKeyManagerService apiKeyManagerService) {
        this.apiKeyManagerService = apiKeyManagerService;
    }

    /**
     * <p>
     * Endpoint for retrieving API keys associated with a specific user.
     * </p>
     *
     * @param request A Map containing the user ID as a parameter.
     * @return A SuccessResponse containing a list of API key DTOs.
     */
    @GetMapping(Constants.API_KEY_BY_USER_ID_URI)
    public SuccessResponse<List<ApiKeyManagerDTO>> getApiKeysByUserId(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get(Constants.USER_ID_PARAM));
        List<ApiKeyManager> apiKeyManagers = apiKeyManagerService.getApiKeysByUserId(userId);
        if (apiKeyManagers.isEmpty()) {
            return new SuccessResponse(SuccessCode.GET_API_KEY_MANAGERS, Constants.NO_DATA_LIST, HttpStatus.OK);
        }
        List<ApiKeyManagerDTO> apiKeyManagerDTOs = modelMapper.map(apiKeyManagers, new TypeToken<List<ApiKeyManagerDTO>>() {
        }.getType());
        return new SuccessResponse(SuccessCode.GET_API_KEY_MANAGERS, apiKeyManagerDTOs, HttpStatus.OK);
    }

    /**
     * <p>
     * Endpoint for generating a new API key for a specific user.
     * </p>
     *
     * @param user A Map containing the user ID as a parameter.
     * @return A SuccessResponse containing the generated API key DTO.
     */
    @PostMapping(Constants.GENERATE_URI)
    public SuccessResponse<ApiKeyManagerDTO> generateApiKey(@RequestBody User user) {
        ApiKeyManager apiKeyManager = apiKeyManagerService.generateApiKey(user);
        if (Objects.nonNull(apiKeyManager)) {
            ApiKeyManagerDTO apiKeyManagerDTO = modelMapper.map(apiKeyManager, ApiKeyManagerDTO.class);
            return new SuccessResponse<>(SuccessCode.API_KEY_MANAGER_SAVE, apiKeyManagerDTO, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.USER_ID_DOES_NOT_EXISTS, Optional.empty(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * <p>
     * Endpoint for deleting an API key associated with a specific user.
     * </p>
     *
     * @param request A Map containing the user ID as a parameter.
     * @return A SuccessResponse indicating the success or failure of the deletion operation.
     */
    @DeleteMapping(Constants.DELETE_API_KEY_URI)
    public SuccessResponse<Boolean> deleteApiKeyManagerById(@RequestBody Map<String, String> request) {
        Long userId = Long.parseLong(request.get(Constants.USER_ID_PARAM));
        boolean isDeleted = apiKeyManagerService.deleteApiKeyManagerByUserId(userId);
        if (isDeleted) {
            return new SuccessResponse<>(SuccessCode.API_KEY_MANAGER_DELETE, Boolean.TRUE, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.API_KEY_MANAGER_NOT_FOUND, Boolean.FALSE, HttpStatus.OK);
        }
    }

    /**
     * <p>
     * Endpoint for validating the given access key and secret access key.
     * </p>
     *
     * @param request A Map containing the accessKeyId and secretAccessKey as parameters.
     * @return A SuccessResponse indicating whether the API key is valid or not.
     */
    @PostMapping(Constants.VALIDATE_ENDPOINT)
    public SuccessResponse<String> validateApiKey(@RequestBody Map<String, String> request) {
        String accessKeyId = request.get(Constants.ACCESS_KEY_ID_PARAM);
        String secretAccessKey = request.get(Constants.SECRET_ACCESS_KEY_PARAM);

        boolean isValid = apiKeyManagerService.validateApiKey(accessKeyId, secretAccessKey);

        if (isValid) {
            return new SuccessResponse<>(SuccessCode.VALID_API_KEY, Constants.VALID_API, HttpStatus.OK);
        } else {
            return new SuccessResponse<>(SuccessCode.INVALID_API_KEY, Constants.INVALID_API, HttpStatus.UNAUTHORIZED);
        }
    }
}
