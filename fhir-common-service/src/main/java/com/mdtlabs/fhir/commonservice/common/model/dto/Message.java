package com.mdtlabs.fhir.commonservice.common.model.dto;

import lombok.Data;

/**
 * Data Transfer Object representing a message.
 * Contains fields for deduplication ID and message body.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class Message {

    private String deduplicationId;
    private String body;
}
