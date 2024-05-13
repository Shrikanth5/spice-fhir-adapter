package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

/**
 * <p>
 * This class is an entity class for timezone table.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class TimezoneDTO {

    private long id;

    private String offset;

    private String description;

}
