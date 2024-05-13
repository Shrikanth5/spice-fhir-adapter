package com.mdtlabs.fhir.commonservice.common.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "fhir_mapping")
public class SpiceFhirMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spice_id")
    private int spiceId;

    @Column(name = "fhir_id")
    private int fhirId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private String status;

    @Column(name = "spice_module")
    private String spiceModule;

    @Column(name = "fhir_resource_type")
    private String fhirResourceType;

    @Column(name = "spice_module_id")
    private Long spiceModuleId;
}
