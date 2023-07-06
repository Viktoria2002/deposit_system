package com.example.deposit_system.web.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.entity.statement.TermDepositOperationInfo;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/termDepositStatement")
public class StatementOfTermDepositController {
    private TermDepositOperationService termDepositOperationService;
    private OpenedTermDepositService openedTermDepositService;

    public StatementOfTermDepositController(TermDepositOperationService termDepositOperationService, OpenedTermDepositService openedTermDepositService) {
        this.termDepositOperationService = termDepositOperationService;
        this.openedTermDepositService = openedTermDepositService;
    }

    @GetMapping(value = "/termDeposit/{depositId}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public OpenedTermDeposit findById(@PathVariable Long depositId) {
        return openedTermDepositService.loadOpenedTermDepositById(depositId);
    }

    @GetMapping(value = "/recalculation/{depositId}")
    @PreAuthorize("hasAuthority('Client')")
    public OpenedTermDeposit recalculation(@PathVariable Long depositId) {
        OpenedTermDeposit deposit = openedTermDepositService.loadOpenedTermDepositById(depositId);
        if(deposit.getOpeningDate().plusMonths(deposit.getTermDeposit().getTerm()).isAfter(LocalDate.now())) {
            List<TermDepositOperationInfo> operations = termDepositOperationService.getTermDepositOperationsOfOpenedTermDeposit(deposit.getId());
            for (TermDepositOperationInfo operation : operations) {
                if (operation.getType().equals(OperationType.CAPITALIZATION)) {
                    deposit.setAmount(deposit.getAmount() - operation.getAmount() * 2 / 3);
                }
            }
        }
        return deposit;
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Client')")
    public void makeOperation(@RequestBody TermDepositOperationInfo info) {
        termDepositOperationService.makeOperation(info);
    }
}
