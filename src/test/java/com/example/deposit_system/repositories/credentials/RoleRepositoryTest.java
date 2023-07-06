package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.credentials.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql","file:src/test/resources/db/inserts.sql"})
class RoleRepositoryTest extends AbstractTest {
    @Autowired
    private RoleRepository roleRepository;
    @Test
    void testFindRoleByName() {
        String roleName = "Client";
        Role role = roleRepository.findRoleByRoleName(roleName);
        assertEquals(roleName, role.getRoleName());
    }

    @Test
    void testFindNonExistingRole() {
        String roleName = "NewRole";
        Role role = roleRepository.findRoleByRoleName(roleName);
        assertNull(role);
    }
}