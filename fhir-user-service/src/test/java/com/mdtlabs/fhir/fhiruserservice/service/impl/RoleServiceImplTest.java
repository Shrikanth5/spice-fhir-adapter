package com.mdtlabs.fhir.fhiruserservice.service.impl;

import static java.time.Instant.now;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mdtlabs.fhir.commonservice.common.constants.TestConstants;
import com.mdtlabs.fhir.commonservice.common.model.entity.Role;
import com.mdtlabs.fhir.commonservice.common.repository.RoleRepository;

import java.util.Optional;

import com.mdtlabs.fhir.commonservice.common.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Unit Test case for the RoleServiceImplTest
 * <p>
 * Author: Hemavardhini Jothi
 * Created on February 27, 2024
 */
@ContextConfiguration(classes = {RoleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleServiceImpl roleServiceImpl;

    /**
     * Method under test: {@link RoleServiceImpl#getRoleById(Long)}
     */
    @Test
    void testGetRoleById() {

        // Arrange
        Role role = TestDataProvider.getRole();
        Optional<Role> roleResult = Optional.of(role);

        when(roleRepository.findById(Mockito.<Long>any())).thenReturn(roleResult);

        // Act
        Role actualRoleById = roleServiceImpl.getRoleById(TestConstants.ONE_LONG);

        // Assert
        verify(roleRepository).findById(Mockito.<Long>any());
        assertSame(role, actualRoleById);
    }
}
