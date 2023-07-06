package com.example.deposit_system.web.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import com.example.deposit_system.services.statement.DemandDepositOperationServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/demandDepositStatement")
public class StatementOfDemandDepositController {
    private DemandDepositOperationService demandDepositOperationService;
    private OpenedDemandDepositService openedDemandDepositService;

    public StatementOfDemandDepositController(DemandDepositOperationServiceImpl demandDepositOperationService, OpenedDemandDepositService openedDemandDepositService) {
        this.demandDepositOperationService = demandDepositOperationService;
        this.openedDemandDepositService = openedDemandDepositService;
    }

    @GetMapping(value = "/demandDeposit/{depositId}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public OpenedDemandDeposit findById(@PathVariable Long depositId) {
        return openedDemandDepositService.loadOpenedDemandDepositById(depositId);
    }

    @RequestMapping(value = "/save")
    @PreAuthorize("hasAuthority('Client')")
    public void makeOperation(@RequestBody DemandDepositOperationInfo info) {
        demandDepositOperationService.makeOperation(info);
    }
}
