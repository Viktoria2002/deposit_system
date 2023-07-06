package com.example.deposit_system.web.credentials;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/generalInfo")
public class GeneralInfoController {
    private ClientService clientService;
    private UserService userService;

    public GeneralInfoController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping(value = "/personalData")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public Client personalData(Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        return client;
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public Client update(@RequestBody Client client) {
        return clientService.updateOrCreateClient(client);
    }
}
