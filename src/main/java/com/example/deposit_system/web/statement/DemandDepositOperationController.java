package com.example.deposit_system.web.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/demandDepositOperations")
public class DemandDepositOperationController {
    private DemandDepositOperationService demandDepositOperationService;
    private OpenedDemandDepositService openedDemandDepositService;

    public DemandDepositOperationController(DemandDepositOperationService demandDepositOperationService, OpenedDemandDepositService openedDemandDepositService) {
        this.demandDepositOperationService = demandDepositOperationService;
        this.openedDemandDepositService = openedDemandDepositService;
    }

    @GetMapping(value = "/statement")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String formDemandDepositOperation(Model model, Long depositId) {
        OpenedDemandDeposit deposit = openedDemandDepositService.loadOpenedDemandDepositById(depositId);
        model.addAttribute(DEMAND_DEPOSIT, deposit.getDemandDeposit());
        model.addAttribute(LIST_DEMAND_DEPOSIT_OPERATIONS, deposit.getDemandDepositOperations());
        return "openedDemandDeposit-views/statement";
    }
}
