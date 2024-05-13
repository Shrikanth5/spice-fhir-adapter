package com.mdtlabs.fhir.adapterservice.migration.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdtlabs.fhir.adapterservice.migration.model.Site;
import com.mdtlabs.fhir.adapterservice.migration.model.SpiceUser;
import com.mdtlabs.fhir.adapterservice.migration.repository.SpiceUserMigrationRepository;
import com.mdtlabs.fhir.adapterservice.migration.service.FhirOrganizationService;
import com.mdtlabs.fhir.adapterservice.migration.service.FhirPractitionerService;
import com.mdtlabs.fhir.adapterservice.migration.service.FhirUserService;
import com.mdtlabs.fhir.adapterservice.migration.service.MigrationService;
import com.mdtlabs.fhir.adapterservice.migration.service.SpiceUserService;
import com.mdtlabs.fhir.adapterservice.migration.utils.Constants;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;

/**
 * The type Migrate service.
 */
@Service
public class MigrateServiceImpl implements MigrationService {
    /**
     * The constant LOGGER.
     * Author: Akash Gopinath
     * Created on: April 10, 2024
     */
    private final SpiceUserService spiceUserService;
    private final FhirUserService fhirUserService;
    private final FhirPractitionerService practitionerService;
    private final FhirOrganizationService organizationService;

    @Autowired
    private SpiceUserMigrationRepository spiceUserMigrationRepository;

    /**
     * Instantiates a new Migrate service.
     *
     * @param spiceUserService    the spice user service
     * @param fhirUserService     the fhir user service
     * @param practitionerService the practitioner service
     * @param organizationService the organization service
     */
    public MigrateServiceImpl(SpiceUserService spiceUserService, FhirUserService fhirUserService, FhirPractitionerService practitionerService, FhirOrganizationService organizationService) {
        this.spiceUserService = spiceUserService;
        this.fhirUserService = fhirUserService;
        this.practitionerService = practitionerService;
        this.organizationService = organizationService;
    }

    private static Practitioner mapUserDTOToFhirPractitioner(SpiceUser userDTO) {
        Practitioner practitioner = new Practitioner();

        practitioner.setId(userDTO.getId().toString());
        HumanName name = new HumanName();
        name.addGiven(userDTO.getFirstName());
        name.setFamily(userDTO.getLastName());
        practitioner.addName(name);
        if (null != userDTO.getGender()) {
            if (userDTO.getGender().equals(Constants.NON_BINARY)) {
                practitioner.setGender(Enumerations.AdministrativeGender.OTHER);
            } else {
                practitioner.setGender(Enumerations.AdministrativeGender.fromCode(userDTO.getGender().toLowerCase()));
            }
        }
        practitioner.setTelecom(List.of(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue(userDTO.getPhoneNumber()), new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                .setValue(userDTO.getUsername())));
        practitioner.setAddress(List.of(new Address().setText(userDTO.getAddress())));
        practitioner.addIdentifier()
                .setSystem(Constants.IDENTIFIER_URL+ Constants.PRACTITIONER_URI)
                .setValue(String.valueOf(userDTO.getId()));
        practitioner.setActive(userDTO.isActive());
        return practitioner;
    }

    private static Organization mapSiteDTOtoOrganization(Site site) {
        Organization organization = new Organization();
        organization.addIdentifier().setSystem(Constants.IDENTIFIER_URL + Constants.ORGANIZATION).setValue(site.getMflCode());
        organization.setName(site.getName());

        CodeableConcept codeAbleAddressType = new CodeableConcept();
        Coding addressTypeCoding = new Coding();
        addressTypeCoding.setSystem(Constants.LOINC_SYSTEM_URL).setCode(site.getSiteType());
        codeAbleAddressType.addCoding(addressTypeCoding);
        organization.setType(List.of(codeAbleAddressType));
        Address address = getAddressForOrganisation(site);
        ContactPoint phone = new ContactPoint();
        phone.setSystem(ContactPoint.ContactPointSystem.PHONE);
        phone.setValue(site.getPhoneNumber());
        organization.addTelecom(phone);
        organization.setAddress(List.of(address));
        organization.setActive(site.isActive());
        // Print values
        Logger.logInfo("Identifier System: " + organization.getIdentifier().get(0).getSystem());
        Logger.logInfo("Identifier Value: " + organization.getIdentifier().get(0).getValue());
        Logger.logInfo("Organization Name: " + organization.getName());
        // Print organization type
        if (!organization.getType().isEmpty()) {
            CodeableConcept codeableConcept = organization.getType().get(0);
            for (Coding coding : codeableConcept.getCoding()) {
                Logger.logInfo("Organization Type System: " + coding.getSystem());
                Logger.logInfo("Organization Type Code: " + coding.getCode());
            }
        }

        // Print address
        if (!organization.getAddress().isEmpty()) {
            Address orgAddress = organization.getAddress().get(0);
            Logger.logInfo("Organization Address: " + orgAddress.getLine().get(0)); // Assuming only one line in the address
            Logger.logInfo("Organization City: " + orgAddress.getCity());
            // Similarly, you can print other address fields as required
        }

        // Print phone
        if (!organization.getTelecom().isEmpty()) {
            ContactPoint orgPhone = organization.getTelecom().get(0);
            Logger.logInfo("Organization Phone System: " + orgPhone.getSystem());
            Logger.logInfo("Organization Phone: " + orgPhone.getValue());
        }
        Logger.logInfo("Organization Active Status: " + organization.getActive());
        return organization;
    }

    private static Address getAddressForOrganisation(Site site) {
        Address address = new Address();

        try {
            String[] addressTypeCodes = site.getAddressType().split(Constants.REGEX);

            for (String addressTypeCode : addressTypeCodes) {
                Address.AddressType addressType = getAddressTypeFromCode(addressTypeCode.trim());

                address.getTypeElement().setValue(addressType);
            }
        } catch (FHIRException exception) {
            logException(exception);
        }

        try {
            String addressUseCode = site.getAddressUse();
            Address.AddressUse addressUse = getAddressUseFromCode(addressUseCode.trim());
            address.getUseElement().setValue(addressUse);

        } catch (FHIRException exception) {
            logException(exception);
        }

        address.setLine(List.of(new StringType(site.getLatitude()), new StringType(site.getLongitude())));
        address.setPostalCode(site.getPostalCode());
        address.setCity(site.getCity());
        address.setCountry(mapCountryIdToName(site.getCountryId()));
        address.setDistrict(site.getCountryName());
        address.setState(site.getSubCountyName());
        address.setPostalCode(site.getPostalCode());

        String concatenatedAddress = (site.getAddress2() != null) ? site.getAddress1() + Constants.SPACE + site.getAddress2() : site.getAddress1();
        address.setText(concatenatedAddress);
        return address;
    }

    private static void logException(FHIRException exception) {
        Logger.logError("FHIRException: {}", exception.getMessage());
    }

    private static Address.AddressType getAddressTypeFromCode(String code) throws FHIRException {
        return switch (code.toUpperCase()) {
            case Constants.POSTAL -> Address.AddressType.POSTAL;
            case Constants.PHYSICAL, Constants.PHYSICAL_ADDRESS -> Address.AddressType.PHYSICAL;
            default -> throw new FHIRException(Constants.UNKNOWN_ADDRESS_TYPE_CODE + code);
        };
    }

    private static Address.AddressUse getAddressUseFromCode(String code) throws FHIRException {
        return switch (code.toUpperCase()) {
            case Constants.WORK -> Address.AddressUse.WORK;
            case Constants.HOME -> Address.AddressUse.HOME;
            case Constants.TEMPORARY -> Address.AddressUse.TEMP;
            case Constants.OBSOLETE -> Address.AddressUse.OLD;
            default -> throw new FHIRException(Constants.UNKNOWN_ADDRESS_USE_CODE + code);
        };
    }

    /**
     * Map country id to name string.
     *
     * @param countryId the country id
     * @return the string
     */
    public static String mapCountryIdToName(Long countryId) {
        String returnString = Constants.UNKNOWN;
        if (Constants.KENYA_ID == countryId.intValue()) {
            returnString = Constants.KENYA;
        }
        return returnString;
    }

    @Override
    public String performUserMigration() {
        // Remove the below
        List<SpiceUser> allSpiceUsers = spiceUserService.getAllSpiceUsers();
        List<Long> allSpiceIds = fhirUserService.findAllUserIds();
        List<Long> response = new ArrayList<>();
        List<Long> errorResponse = new ArrayList<>();
        Logger.logInfo("In MigrationServiceImpl for All Spice Users Total Count : "+ allSpiceUsers.size());
        for (SpiceUser userDTO : allSpiceUsers) {
            Logger.logInfo("In MigrationServiceImpl for User : "+ userDTO);
            if(!allSpiceIds.contains(userDTO.getId()))   {
                Logger.logInfo("In MigrationServiceImpl for Spice User : "+ userDTO);
                Practitioner practitioner = mapUserDTOToFhirPractitioner(userDTO);
                Logger.logInfo("In MigrationServiceImpl for FHIR Practitioner : "+ practitioner);
                String practitionerId = practitionerService.savePractitionerToHapiFhirServer(userDTO.getId(), practitioner);
                // send only the id of spice
                fhirUserService.insertSpiceUsers(userDTO, Long.valueOf(practitionerId));
                response.add(userDTO.getId());
            } else {
                Logger.logInfo("In MigrationServiceImpl for already existing Users");
                errorResponse.add(userDTO.getId());
            }
        }
        return "Users Migrated SuccessFully" ;
    }

    @Override
    public String performSiteMigration() {
        List<Site> siteList = spiceUserMigrationRepository.getSiteDTOs();
        Logger.logInfo("Total Number of Sites to be inserted into site_fhir_mapping: {}", siteList.size());
        List<Long> allSpiceIds = fhirUserService.findAllSitesIds();
        siteList.forEach(site -> {
            if(!allSpiceIds.contains(site.getId()))   {
                Organization organization = mapSiteDTOtoOrganization(site);
                Logger.logInfo("Site to Organization Converted Details {}", organization);
                String organisationID = organizationService.sendOrganizationsToFhirServer(organization);
                fhirUserService.insertIntoSpiceFhirMapping(site.getId(), organisationID);
                Logger.logInfo("site_id {}", site.getId());
                Logger.logInfo("organisationID {}", organisationID);
            }
        });
        return " Sites Migrated Successfully ";
    }
}
