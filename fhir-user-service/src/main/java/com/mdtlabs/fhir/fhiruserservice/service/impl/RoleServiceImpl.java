package com.mdtlabs.fhir.fhiruserservice.service.impl;

import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.repository.RoleRepository;
import com.mdtlabs.fhir.fhiruserservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Role getRoleById(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        return role.orElse(null);
    }

}
