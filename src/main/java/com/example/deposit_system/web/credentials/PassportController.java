package com.example.deposit_system.web.credentials;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.Passport;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.PassportService;
import com.example.deposit_system.services.credentials.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/passports")
public class PassportController {
    private PassportService passportService;
    private UserService userService;
    private ClientService clientService;

    public PassportController(PassportService passportService, UserService userService, ClientService clientService) {
        this.passportService = passportService;
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping(value = "/personalData")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public Passport personalData(Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        Passport passport = client.getPassportData();
        return passport;
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public Passport save(@RequestBody Passport passport) {
        return passportService.updateOrCreatePassport(passport);
    }
}
