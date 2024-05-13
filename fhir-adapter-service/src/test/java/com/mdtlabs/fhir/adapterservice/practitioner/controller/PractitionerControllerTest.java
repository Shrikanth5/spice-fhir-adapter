package com.mdtlabs.fhir.adapterservice.practitioner.controller;

import com.mdtlabs.fhir.adapterservice.practitioner.service.PractitionerService;
import com.mdtlabs.fhir.adapterservice.util.SuccessResponse;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirUserDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerDTO;
import com.mdtlabs.fhir.commonservice.common.model.dto.PractitionerRoleDTO;
import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PractitionerControllerTest {

    @InjectMocks
    PractitionerController practitionerController;

    @Mock
    PractitionerService practitionerService;

    @Test
    void createUserTest() {
        //given
        PractitionerDTO practitionerDTO = TestDataProvider.getPractitionerDTO();
        FhirUserDTO fhirUserRequestDto = TestDataProvider.getFhirUserRequestDTO();
        //when
        when(practitionerService.createAndMapFhirPractitioner(fhirUserRequestDto)).thenReturn(practitionerDTO);
        SuccessResponse<PractitionerDTO> response =  practitionerController.createUser(Constants.ACCESS_KEY_ID_PARAM, Constants.SECRET_ACCESS_KEY_PARAM,fhirUserRequestDto);
        //then
        verify(practitionerService).createAndMapFhirPractitioner(fhirUserRequestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createRoleTest() {
        //given
        PractitionerRoleDTO practitionerRoleDTO = TestDataProvider.getPractitionerRoleDTO();
        //when
        when(practitionerService.createAndMapFhirPractitionerRole(practitionerRoleDTO)).thenReturn(practitionerRoleDTO);
        SuccessResponse<PractitionerRoleDTO> response =  practitionerController.createRole(Constants.ACCESS_KEY_ID_PARAM, Constants.SECRET_ACCESS_KEY_PARAM,practitionerRoleDTO);
        //then
        verify(practitionerService).createAndMapFhirPractitionerRole(practitionerRoleDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
