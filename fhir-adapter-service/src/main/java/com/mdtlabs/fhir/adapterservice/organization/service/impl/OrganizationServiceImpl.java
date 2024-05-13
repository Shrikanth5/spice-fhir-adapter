package com.mdtlabs.fhir.adapterservice.organization.service.impl;

import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import com.mdtlabs.fhir.adapterservice.fhirclient.FhirClient;
import com.mdtlabs.fhir.adapterservice.organization.service.OrganizationService;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.exception.FhirValidation;
import com.mdtlabs.fhir.commonservice.common.exception.RequestTimeOutException;
import com.mdtlabs.fhir.commonservice.common.logger.Logger;
import com.mdtlabs.fhir.commonservice.common.model.dto.FhirOrganizationRequestDto;
import com.mdtlabs.fhir.commonservice.common.model.entity.SiteFhirMapping;
import com.mdtlabs.fhir.commonservice.common.repository.SiteSpiceFhirMappingRepository;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.model.Address;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.StringType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * The type Organization service.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository;
    private final FhirClient fhirClient;

    /**
     * Instantiates a new Organization service.
     *
     * @param siteSpiceFhirMappingRepository the site spice fhir mapping repository
     * @param fhirClient                     the fhir client
     */
    public OrganizationServiceImpl(SiteSpiceFhirMappingRepository siteSpiceFhirMappingRepository,
                                   FhirClient fhirClient) {
        this.siteSpiceFhirMappingRepository = siteSpiceFhirMappingRepository;
        this.fhirClient = fhirClient;
    }

    private Address getAddressForOrganisation(FhirOrganizationRequestDto fhirOrganizationRequestDto) {
        Address address = new Address();

        try {
            String[] addressTypeCodes = fhirOrganizationRequestDto.getAddressType().split(Constants.REGEX);

            for (String addressTypeCode : addressTypeCodes) {
                Address.AddressType addressType = getAddressTypeFromCode(addressTypeCode.trim());
                address.getTypeElement().setValue(addressType);
            }
        } catch (FHIRException exception) {
            Logger.logError("FHIRException: {}", exception.getMessage());
        }

        try {
            String addressUseCode = fhirOrganizationRequestDto.getAddressUse();
            Address.AddressUse addressUse = getAddressUseFromCode(addressUseCode.trim());
            address.getUseElement().setValue(addressUse);

        } catch (FHIRException exception) {
            Logger.logError("FHIRException: {}", exception.getMessage());
        }

        address.setLine(List.of(new StringType(fhirOrganizationRequestDto.getLatitude()),
                new StringType(fhirOrganizationRequestDto.getLongitude())));
        address.setPostalCode(fhirOrganizationRequestDto.getPostalCode());
        address.setCity(fhirOrganizationRequestDto.getCity());
        address.setCountry(mapCountryIdToName(fhirOrganizationRequestDto.getCountryId()));
        address.setDistrict(fhirOrganizationRequestDto.getCountryName());
        address.setState(fhirOrganizationRequestDto.getSubCountyName());
        address.setPostalCode(fhirOrganizationRequestDto.getPostalCode());

        String concatenatedAddress = (fhirOrganizationRequestDto.getAddress2() != null)
                ? fhirOrganizationRequestDto.getAddress1() + Constants.SPACE + fhirOrganizationRequestDto.getAddress2()
                : fhirOrganizationRequestDto.getAddress1();
        address.setText(concatenatedAddress);
        return address;
    }

    private Address.AddressType getAddressTypeFromCode(String code) throws FHIRException {
        return switch (code.toUpperCase()) {
            case Constants.POSTAL -> Address.AddressType.POSTAL;
            case Constants.PHYSICAL, Constants.PHYSICAL_ADDRESS -> Address.AddressType.PHYSICAL;
            default -> throw new FHIRException(Constants.UNKNOWN_ADDRESS_TYPE_CODE + code);
        };
    }

    private Address.AddressUse getAddressUseFromCode(String code) throws FHIRException {
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
    public String mapCountryIdToName(Long countryId) {
        String returnString = Constants.UNKNOWN;
        if (Constants.KENYA_ID == countryId.intValue()) {
            returnString = Constants.KENYA;
        }
        return returnString;
    }

    @Override
    public void createAndMapFhirOrganization(FhirOrganizationRequestDto fhirOrganizationRequestDto) {
        Organization organization = new Organization();
        organization.addIdentifier().setSystem(Constants.IDENTIFIER_URL + Constants.ORGANIZATION)
                .setValue(fhirOrganizationRequestDto.getMflCode());
        organization.setName(fhirOrganizationRequestDto.getName());

        CodeableConcept codeAbleAddressType = new CodeableConcept();
        Coding addressTypeCoding = new Coding();
        addressTypeCoding.setSystem(Constants.LOINC_SYSTEM_URL).setCode(fhirOrganizationRequestDto.getSiteType());
        codeAbleAddressType.addCoding(addressTypeCoding);

        organization.setType(List.of(codeAbleAddressType));

        Address address = getAddressForOrganisation(fhirOrganizationRequestDto);

        ContactPoint phone = new ContactPoint();
        phone.setSystem(ContactPoint.ContactPointSystem.PHONE);
        phone.setValue(fhirOrganizationRequestDto.getPhoneNumber());
        organization.addTelecom(phone);

        organization.setAddress(List.of(address));
        organization.setActive(fhirOrganizationRequestDto.isActive());

        String organisationID = sendOrganizationsToFhirServer(organization);
        if (Objects.nonNull(organisationID)) {
            Logger.logInfo("Organization saved successfully with id: " + organisationID);
            siteSpiceFhirMappingRepository.save(new SiteFhirMapping(fhirOrganizationRequestDto.getId(),
                    Long.valueOf(organisationID), true, false));
        } else {
            throw new FhirValidation(20016);
        }
    }

    @Override
    public String sendOrganizationsToFhirServer(Organization organization) {
        try {
            String response = fhirClient.sendOrganizationToFhirServer(organization);
            Logger.logInfo("After Receiving the response from the server"+ response);
            return response;
        } catch (FhirClientConnectionException timeoutException) {
            Logger.logError("Receiving Error on fhirServer", timeoutException);
            throw new RequestTimeOutException(20010, timeoutException.getMessage());
        }
    }
}

