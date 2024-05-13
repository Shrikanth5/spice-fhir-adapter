package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * This DTO class handling the MentalHealthDetails details
 * </p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class MentalHealthDetailsDTO implements Serializable {

    private Long questionId;

    private Long answerId;

    private int displayOrder;

    private int score;

}