package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Client;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    Client loadClientById(Long clintId);

    List<Client> findClientsByName(String name);

    Client loadClientByEmail(String email);

    Client createClient(String surname, String name, String patronymic, String gender, LocalDate dateOfBirth, String phoneNumber, Long passportDataId, String email, String password);

    Client updateOrCreateClient(Client client);

    List<Client> getAllClients();

//    void removeClient(Long clientId);
}
