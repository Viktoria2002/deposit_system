package com.example.deposit_system.web.opened_deposits;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import com.example.deposit_system.services.deposits.DemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.deposit_system.constants.Constants.*;
import static com.example.deposit_system.constants.Constants.NAME;

@Controller
@RequestMapping(value = "/openedDeposits")
public class OpenedDepositController {
    private OpenedDemandDepositService openedDemandDepositService;
    private OpenedTermDepositService openedTermDepositService;
    private DemandDepositService demandDepositService;
    private UserService userService;
    private ClientService clientService;

    public OpenedDepositController(OpenedDemandDepositService openedDemandDepositService, OpenedTermDepositService openedTermDepositService, DemandDepositService demandDepositService, UserService userService, ClientService clientService) {
        this.openedDemandDepositService = openedDemandDepositService;
        this.openedTermDepositService = openedTermDepositService;
        this.demandDepositService = demandDepositService;
        this.userService = userService;
        this.clientService = clientService;
    }

    @GetMapping(value = "/client")
    public String openedDemandDepositsOfClient(Model model, Long clientId) {
        model.addAttribute(LIST_OPENED_DEMAND_DEPOSITS, openedDemandDepositService.getOpenedDemandDepositsOfClientWithOpenStatus(clientId));
        model.addAttribute(LIST_OPENED_TERM_DEPOSITS, openedTermDepositService.getOpenedTermDepositsOfClientWithOpenStatus(clientId));
        return "openedDeposit-views/openedDeposits";
    }

    @GetMapping(value = "/open/client")
    public String openedDemandDepositsForCurrentClient(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        model.addAttribute(LIST_OPENED_DEMAND_DEPOSITS, openedDemandDepositService.getOpenedDemandDepositsOfClientWithOpenStatus(client.getClientId()));
        model.addAttribute(LIST_OPENED_TERM_DEPOSITS, openedTermDepositService.getOpenedTermDepositsOfClientWithOpenStatus(client.getClientId()));
        return "openedDeposit-views/openedDeposits";
    }

    @GetMapping(value = "/close/client")
    public String closedDemandDepositsForCurrentClient(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        model.addAttribute(LIST_OPENED_DEMAND_DEPOSITS, openedDemandDepositService.getOpenedDemandDepositsOfClientWithCloseStatus(client.getClientId()));
        model.addAttribute(LIST_OPENED_TERM_DEPOSITS, openedTermDepositService.getOpenedTermDepositsOfClientWithCloseStatus(client.getClientId()));
        return "openedDeposit-views/closedDeposits";
    }

}
