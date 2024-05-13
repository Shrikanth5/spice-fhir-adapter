package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.model.enumeration.MessageStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * The {@code SpiceMessageTracker} class represents an entity that stores information about spice data,
 * which is derived from Sqs queue.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "spice_message_tracker")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SpiceMessageTracker extends BaseEntity {

    @Column(name = FieldConstants.MESSAGE, updatable = false, nullable = false)
    private String message;

    @Column(name = FieldConstants.DEDUPLICATION_ID, updatable = false, nullable = false)
    private String deduplicationId;

    @Enumerated(EnumType.STRING)
    @Column(name = FieldConstants.STATUS, columnDefinition = FieldConstants.MESSAGE_STATUS_CODE, updatable = false, nullable = false)
    private MessageStatusCode status;
}
