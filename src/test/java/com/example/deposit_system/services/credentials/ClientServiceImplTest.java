package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.Passport;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.repositories.credentials.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @InjectMocks
    private ClientServiceImpl clientService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PassportService passportService;
    @Mock
    private UserService userService;
    @Test
    void testLoadClientById() {
        Client client = new Client();
        client.setClientId(1L);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        Client actualClient = clientService.loadClientById(1L);
        assertEquals(client, actualClient);
    }

    @Test
    void testFindClientsByName() {
        String name = "name";
        clientService.findClientsByName(name);
        verify(clientRepository).findClientsByName(name);
    }

    @Test
    void testFindClientByEmail() {
        String email = "test@gmail.com";
        clientService.loadClientByEmail(email);
        verify(clientRepository).findClientByEmail(email);
    }

    @Test
    void testCreateClient() {
        User user = new User("user@gmail.com", "adpass");
        when(userService.createUser(any(),any())).thenReturn(user);
        Passport passport = new Passport("3456789", "organization",
                LocalDate.of(1990,11,11), LocalDate.of(2029,11,12));
        passport.setId(3L);
        when(passportService.loadPassportById(any())).thenReturn(passport);
        clientService.createClient("LN", "FN", "P", "male", LocalDate.of(2000,11,11),
                "+375441111111", passport.getId(), "user@gmail.com", "adpass");
        verify(clientRepository).save(any());
    }

    @Test
    void testUpdateOrCreateClient() {
        Client client = new Client();
        client.setClientId(1L);
        clientService.updateOrCreateClient(client);
        verify(clientRepository).save(client);
    }

    @Test
    void testGetAllClients() {
        clientService.getAllClients();
        verify(clientRepository, times(1)).findAll();
    }
}