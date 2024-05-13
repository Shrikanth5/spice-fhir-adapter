package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * The {@code ApiKeyManagerDTO} class represents a Data Transfer Object for the ApiKeyManager entity.
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyManagerDTO {

    private String accessKeyId;
    private String secretAccessKey;
}
