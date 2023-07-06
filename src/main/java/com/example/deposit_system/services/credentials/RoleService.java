package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Role;

public interface RoleService {
    Role loadRoleByName(String roleName);

    Role createRole(String roleName);
}
