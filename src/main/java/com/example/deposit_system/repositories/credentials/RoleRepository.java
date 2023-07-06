package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.entity.credentials.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String name);
    Role deleteRoleByRoleName(String roleName);
}
