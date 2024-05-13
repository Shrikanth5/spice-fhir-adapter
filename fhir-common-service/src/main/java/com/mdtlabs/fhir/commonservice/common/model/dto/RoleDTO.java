package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

/**
 * <p>
 * This DTO class handling the Role details.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class RoleDTO {
    private long id;
    private String name;
    private String level;

}