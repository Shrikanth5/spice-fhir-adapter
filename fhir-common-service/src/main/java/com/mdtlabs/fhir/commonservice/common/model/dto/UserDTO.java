package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdtlabs.fhir.commonservice.common.constants.Constants;
import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import com.mdtlabs.fhir.commonservice.common.utils.UserContextHolder;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * This is a DTO class for user entity.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String middleName;

    private String lastname;

    private String companyName;

    private String companyEmail;

    private String countryCode;

    private String phone;

    private String address;

    private String website;

    private Country country;

    private Timezone timezone;

    private Integer forgetPasswordCount;

    private Integer invalidLoginAttempts;

    private Set<Role> roles = new HashSet<>();

    private String authorization;

    private long currentDate;

    private Boolean isSuperUser = false;

    private Long createdBy = getUserValue();

    private Long updatedBy = getUserValue();


    /**
     * This function returns a TimezoneDTO object mapped from a timezone object using ModelMapper.
     *
     * @return A {@link TimezoneDTO} object is being returned. If the "timezone" object is null, then null is
     * returned. The method uses a ModelMapper to map the "timezone" object to a TimezoneDTO object.
     */
    public TimezoneDTO getTimezone() {
        ModelMapper modelMapper = new ModelMapper();
        if (Objects.isNull(timezone)) {
            return null;
        }
        return modelMapper.map(timezone, TimezoneDTO.class);
    }

    /**
     * This function returns a set of RoleDTO objects mapped from a list of roles using ModelMapper.
     *
     * @return {@link Set<RoleDTO>} is being returned. If the roles object is null, an empty set is
     * returned. Otherwise, the roles are mapped to RoleDTO objects using ModelMapper and then
     * collected into a set using Collectors.toSet().
     */
    public Set<RoleDTO> getRoles() {
        if (Objects.isNull(roles)) {
            return Collections.emptySet();
        }
        return roles.stream().map(role -> new ModelMapper().map(role, RoleDTO.class)).collect(Collectors.toSet());
    }

    /**
     * This Java function returns a CountryDTO object mapped from a country object using ModelMapper.
     *
     * @return A {@link CountryDTO} object is being returned.
     */
    public CountryDTO getCountry() {
        ModelMapper modelMapper = new ModelMapper();
        if (Objects.isNull(country)) {
            return null;
        }
        return modelMapper.map(country, CountryDTO.class);
    }

    /**
     * This method is used to get user value
     *
     * @return String - user value
     */
    @JsonIgnore
    public long getUserValue() {
        if (null != UserContextHolder.getUserDto()) {
            return UserContextHolder.getUserDto().getId();
        }
        return Constants.ZERO;
    }
}

