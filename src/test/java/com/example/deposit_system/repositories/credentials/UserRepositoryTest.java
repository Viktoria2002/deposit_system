package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.credentials.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql","file:src/test/resources/db/inserts.sql"})
class UserRepositoryTest extends AbstractTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void testFindUserByEmail() {
        User user = userRepository.findUserByEmail("clUser2@gmail.com");
        Long expectedId = 4L;
        assertEquals(expectedId, user.getId());
    }

    @Test
    void testFindingNonExistingUserWithEmail() {
        User user = userRepository.findUserByEmail("nonExistingEmail@gmail.com");
        assertNull(user);
    }
}
