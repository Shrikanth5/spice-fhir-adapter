package com.mdtlabs.fhir.commonservice.common.model.dto;

import com.mdtlabs.fhir.commonservice.common.model.entity.Country;
import com.mdtlabs.fhir.commonservice.common.model.entity.Organization;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.model.entity.Timezone;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * This is a DTO class for user profile entity.
 * </p>
 * <p>
 * Author: Akash Gopinath
 * Created on: February 26, 2024
 */
@Data
public class UserProfileDTO {

    private long id;
    private Set<Role> roles = new HashSet<>();
    private String firstName;
    private String lastName;
    private String username;
    private Set<Organization> organizations;
    private Long tenantId;
    private Timezone timezone;
    private String phoneNumber;
    private String gender;
    private String countryCode;
    private Country country;

    /**
     * <p>
     * This Java function returns a set of RoleDTO objects by mapping each element of the input roles set
     * to a RoleDTO object using ModelMapper.
     * </p>
     *
     * @return {@link Set<RoleDTO>} The method uses Java 8 stream and lambda expressions to map each
     * Role object in the roles set to a RoleDTO object using ModelMapper, and then collects the mapped
     * objects into a new set using Collectors.toSet().
     */
    public Set<RoleDTO> getRoles() {
        return roles.stream().map(role -> new ModelMapper().map(role, RoleDTO.class)).collect(Collectors.toSet());
    }

    /**
     * <p>
     * This function maps a timezone object to a timezone DTO using a ModelMapper.
     * </p>
     *
     * @return A {@link TimezoneDTO} object is being returned. The method uses a ModelMapper to map the
     * properties of the timezone object to the corresponding properties of the TimezoneDTO object.
     */
    public TimezoneDTO getTimezone() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(timezone, TimezoneDTO.class);
    }
}
