package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import com.mdtlabs.fhir.commonservice.common.utils.CustomDateSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * The {@code UserToken} class represents an entity that stores user tokens,
 * including the user ID, authentication token, and token type.
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = FieldConstants.USER_TOKEN)
public class UserToken extends BaseEntity {

    @Column(name = FieldConstants.USER_ID)
    private Long userId;

    @Column(name = FieldConstants.AUTH_TOKEN)
    private String authToken;

    @Column(name = FieldConstants.LAST_SESSION_TIME, columnDefinition = "TIMESTAMP")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date lastSessionTime;
}