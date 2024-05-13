package com.mdtlabs.fhir.commonservice.common.model.entity;

import com.mdtlabs.fhir.commonservice.common.constants.FieldConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = FieldConstants.API_KEY_MANAGER)
public class ApiKeyManager extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = FieldConstants.USER_ID)
    private User user;

    @Column(name = FieldConstants.ACCESS_KEY_ID)
    private String accessKeyId;

    @Column(name = FieldConstants.SECRET_ACCESS_KEY)
    private String secretAccessKey;

}