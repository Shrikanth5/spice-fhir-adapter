package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * Bp log details is used to handle bp log details information.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BpLogDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer systolic;

    @NotNull
    private Integer diastolic;

    private Integer pulse;

    public BpLogDetails(@NotNull Integer systolic, @NotNull Integer diastolic, Integer pulse) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    public BpLogDetails() {
    }

}
