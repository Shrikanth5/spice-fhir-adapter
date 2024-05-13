package com.mdtlabs.fhir.commonservice.common.model.dto;


import com.mdtlabs.fhir.commonservice.common.constants.ErrorConstants;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * <p>
 * The {@code CountryDTO} class represents a Data Transfer Object for the Country entity.
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class CountryDTO {

    private Long id;

    @NotEmpty(message = ErrorConstants.COUNTRY_NAME_NOT_NULL)
    private String name;

    @NotEmpty(message = ErrorConstants.COUNTRY_CODE_NOT_NULL)
    private String code;

    private String abbreviation;

}
