package com.mdtlabs.fhir.adapterservice.migration.controller;

import com.mdtlabs.fhir.adapterservice.migration.service.MigrationService;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Migration controller.
 * Author: Akash Gopinath
 * Created on: April 10, 2024
 */
@RestController
@RequestMapping("/migration")
public class MigrationController {

    private final MigrationService userMigrationService;

    /**
     * Instantiates a new Migration controller.
     *
     * @param userMigrationService the user migration service
     */
    public MigrationController(MigrationService userMigrationService) {
        this.userMigrationService = userMigrationService;
    }

    /**
     * Migrate users.
     */
    @GetMapping("/user-migrate")
    public String migrateUsers() {
        Logger.logInfo("Inside The Migration Controller For Users Migration");
        return userMigrationService.performUserMigration();
    }

    /**
     * Migrate sites.
     */
    @GetMapping("/site-migrate")
    public String migrateSites() {
        Logger.logInfo("Inside The Migration Controller For Site Migration");
        return userMigrationService.performSiteMigration();
    }
}
