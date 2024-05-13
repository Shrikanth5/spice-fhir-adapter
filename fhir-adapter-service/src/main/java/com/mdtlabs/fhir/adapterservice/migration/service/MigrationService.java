package com.mdtlabs.fhir.adapterservice.migration.service;

/**
 * The interface Migration service.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
public interface MigrationService {

    /**
     * Perform user migration.
     */
    String performUserMigration();

    /**
     * Perform site migration.
     */
    String performSiteMigration();
}
