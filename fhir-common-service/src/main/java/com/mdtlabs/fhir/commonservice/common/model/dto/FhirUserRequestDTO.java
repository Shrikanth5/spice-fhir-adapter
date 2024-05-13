package com.mdtlabs.fhir.commonservice.common.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class FhirUserRequestDTO {

    private String type;

    private List<FhirUserDTO> users;
}
