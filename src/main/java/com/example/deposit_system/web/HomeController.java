package com.example.deposit_system.web;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.BankService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.opened_deposits.ReportEntry;
import org.apache.commons.math3.util.Precision;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {
    private OpenedDemandDepositService openedDemandDepositService;
    private OpenedTermDepositService openedTermDepositService;
    private OpenedDepositService openedDepositService;
    private ClientService clientService;
    private BankService bankService;
    private UserService userService;

    public HomeController(OpenedDemandDepositService openedDemandDepositService, OpenedTermDepositService openedTermDepositService, OpenedDepositService openedDepositService, ClientService clientService, BankService bankService, UserService userService) {
        this.openedDemandDepositService = openedDemandDepositService;
        this.openedTermDepositService = openedTermDepositService;
        this.openedDepositService = openedDepositService;
        this.clientService = clientService;
        this.bankService = bankService;
        this.userService = userService;
    }

    @GetMapping("/clientHome")
    @PreAuthorize("hasAuthority('Client')")
    public ResponseEntity<?> clientHome(Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        return new ResponseEntity<>(openedTermDepositService.getExpiredDepositsOfClient(client), HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String home(Model model) {
        model.addAttribute("depositsMap", openedDepositService.formGraphValues());
        model.addAttribute("clientsNumber", clientService.getAllClients().size());
        model.addAttribute("depositsNumber", openedDemandDepositService.getAllOpenedDemandDeposits().size() + openedTermDepositService.getAllOpenedTermDeposits().size());
        model.addAttribute("banksNumber", bankService.getAllBanks().size());
        return "home";
    }

    @RequestMapping("/banksCharts")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> banksCharts() {
        return new ResponseEntity<>(openedDepositService.formDiagramValues(), HttpStatus.OK);
    }
}
