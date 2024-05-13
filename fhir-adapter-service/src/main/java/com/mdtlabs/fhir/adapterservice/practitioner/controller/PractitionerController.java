package com.mdtlabs.fhir.adapterservice.practitioner.controller;

import com.mdtlabs.fhir.adapterservice.practitioner.service.PractitionerService;
import com.mdtlabs.fhir.adapterservice.util.SuccessCode;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user-related endpoints.
 * <p>
 * Author: Charan
 * <p>
 * Created on: February 27, 2024
 */
@RestController
@RequestMapping(Constants.USER_URI)
public class PractitionerController {

    private final PractitionerService practitionerService;

    public PractitionerController(PractitionerService practitionerService) {
        this.practitionerService = practitionerService;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param accessKey   The access key ID for authentication.
     * @param secretKey   The secret access key for authentication.
     * @param userDetails The details of the user to be created.
     * @return A SuccessResponse containing the created PractitionerDTO.
     */
    @PostMapping(Constants.CREATE_URI)
    public SuccessResponse<PractitionerDTO> createUser(@RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
                                                       @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
                                                       @RequestBody FhirUserDTO userDetails) {

        return new SuccessResponse<>(SuccessCode.USER_SAVE, practitionerService.createAndMapFhirPractitioner(userDetails),
                HttpStatus.OK);
    }

    /**
     * Endpoint for creating a new role.
     *
     * @param accessKey   The access key ID for authentication.
     * @param secretKey   The secret access key for authentication.
     * @param roleDetails The details of the role to be created.
     * @return A SuccessResponse containing the created PractitionerRoleDTO.
     */
    @PostMapping(Constants.ROLE_CREATE_URI)
    public SuccessResponse<PractitionerRoleDTO> createRole(@RequestHeader(Constants.ACCESS_KEY_ID_PARAM) String accessKey,
                                                           @RequestHeader(Constants.SECRET_ACCESS_KEY_PARAM) String secretKey,
                                                           @RequestBody PractitionerRoleDTO roleDetails) {
        return new SuccessResponse<>(SuccessCode.ROLE_SAVE, practitionerService.createAndMapFhirPractitionerRole(roleDetails),
                HttpStatus.OK);
    }

}