package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Role;
import com.example.deposit_system.repositories.credentials.RoleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role loadRoleByName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
