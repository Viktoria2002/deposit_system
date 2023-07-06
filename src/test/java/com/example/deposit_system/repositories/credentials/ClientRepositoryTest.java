package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.credentials.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql","file:src/test/resources/db/inserts.sql"})
class ClientRepositoryTest extends AbstractTest {
    @Autowired
    private ClientRepository clientRepository;
    @Test
    void testFindClientsByName() {
        List<Client> clients = clientRepository.findClientsByName("client");
        assertEquals(2, clients.size());
    }

    @Test
    void testFindClientByEmail() {
        Client expectedClient = new Client();
        expectedClient.setClientId(1L);
        expectedClient.setSurname("client1FN");
        expectedClient.setName("client1LN");
        expectedClient.setPatronymic("client1P");
        expectedClient.setGender("male");
        expectedClient.setDateOfBirth(LocalDate.of(2000,11,3));
        expectedClient.setPhoneNumber("+375441111111");
        Client client = clientRepository.findClientByEmail("clUser1@gmail.com");
        assertEquals(expectedClient, client);
    }
}
