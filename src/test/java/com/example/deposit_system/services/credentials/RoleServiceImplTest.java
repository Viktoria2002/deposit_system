package com.example.deposit_system.services.credentials;

import com.example.deposit_system.repositories.credentials.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testLoadRoleByName() {
        roleService.loadRoleByName("Admin");
        verify(roleRepository).findRoleByRoleName(any());
    }

    @Test
    void testCreateRole() {
        roleService.createRole("Admin");
        verify(roleRepository, times(1)).save(any());
    }
}