package com.example.deposit_system.web.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/termDepositOperations")
public class TermDepositOperationController {
    private TermDepositOperationService termDepositOperationService;
    private OpenedTermDepositService openedTermDepositService;

    public TermDepositOperationController(TermDepositOperationService termDepositOperationService, OpenedTermDepositService openedTermDepositService) {
        this.termDepositOperationService = termDepositOperationService;
        this.openedTermDepositService = openedTermDepositService;
    }

    @GetMapping(value = "/statement")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String formTermDepositOperations(Model model, Long depositId) {
        OpenedTermDeposit deposit = openedTermDepositService.loadOpenedTermDepositById(depositId);
        model.addAttribute(TERM_DEPOSIT, deposit.getTermDeposit());
        model.addAttribute(LIST_TERM_DEPOSIT_OPERATIONS, deposit.getTermDepositOperations());
        return "openedTermDeposit-views/statement";
    }
}
