package com.example.deposit_system.web.credentials;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/clients")
public class ClientController {
    private ClientService clientService;
    private UserService userService;

    public ClientController(ClientService clientService, UserService userService) {
        this.clientService = clientService;
        this.userService = userService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String clients(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Client> clients = new CopyOnWriteArrayList<>(clientService.findClientsByName(keyword));
        clients.remove(0);
        model.addAttribute(LIST_CLIENTS, clients);
        model.addAttribute(KEYWORD, keyword);
        return "client-views/clients";
    }

    @GetMapping(value = "/fioSorting")
    @PreAuthorize("hasAuthority('Admin')")
    public String fioSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Client> clients = new CopyOnWriteArrayList<>(clientService.findClientsByName(keyword));
        clients.remove(0);
        clients.sort(Comparator.comparing(Client::getSurname));
        model.addAttribute(LIST_CLIENTS, clients);
        return "client-views/clients";
    }

    @GetMapping(value = "/dateSorting")
    @PreAuthorize("hasAuthority('Admin')")
    public String dateSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Client> clients = new CopyOnWriteArrayList<>(clientService.findClientsByName(keyword));
        clients.sort(Comparator.comparing(Client::getDateOfBirth));
        model.addAttribute(LIST_CLIENTS, clients);
        return "client-views/clients";
    }

    @GetMapping(value = "/personalData")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String personalData(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        model.addAttribute(CLIENT, client);
        return "client-views/personalData";
    }

    @GetMapping(value = "/registration")
    @PreAuthorize("hasAuthority('Client')")
    public String registration(Model model) {
        model.addAttribute(CLIENT, new Client());
        model.addAttribute("passwordConfirmation", new String());
        return "credential-views/registration";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Client')")
    public String save(@Valid Client client, BindingResult bindingResult) {
        User user = userService.loadUserByEmail(client.getUser().getEmail());
        if (user != null)
            bindingResult.rejectValue("user.email", null, "Аккаунт уже зарегестрирован в системе");
        if (bindingResult.hasErrors()) return "credential-views/registration";
        clientService.updateOrCreateClient(client);
        return "credential-views/login";
    }
}
