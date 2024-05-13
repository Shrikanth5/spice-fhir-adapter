package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * This DTO class handling the pregnancy ANC details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class PregnancySymptomsDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
}
