package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.*;
import com.example.deposit_system.repositories.credentials.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PassportService passportService;
    private UserService userService;

    public ClientServiceImpl(ClientRepository clientRepository, PassportService passportService, UserService userService) {
        this.clientRepository = clientRepository;
        this.passportService = passportService;
        this.userService = userService;
    }

    @Override
    public Client loadClientById(Long clintId) {
        return clientRepository.findById(clintId).orElseThrow(() -> new EntityNotFoundException("Client with id " + clintId + " Not Found"));
    }

    @Override
    public List<Client> findClientsByName(String name) {
        return clientRepository.findClientsByName(name);
    }

    @Override
    public Client loadClientByEmail(String email) {
        return clientRepository.findClientByEmail(email);
    }

    @Override
    public Client createClient(String surname, String name, String patronymic, String gender, LocalDate dateOfBirth, String phoneNumber, Long passportId, String email, String password) {
        Passport passportData = passportService.loadPassportById(passportId);
        User user = userService.createUser(email, password);
        userService.assignRoleToUser(email, "Client");
        return clientRepository.save(new Client(surname, name, patronymic, gender, dateOfBirth, phoneNumber, passportData, user));
    }

    @Override
    public Client updateOrCreateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
