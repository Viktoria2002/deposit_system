package com.example.deposit_system.entity.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReplenishmentTermDepositOperation implements TermDepositOperation {
    private TermDepositOperationService termDepositOperationService;
    private OpenedTermDepositService openedTermDepositService;

    public ReplenishmentTermDepositOperation(@Lazy TermDepositOperationService termDepositOperationService, OpenedTermDepositService openedTermDepositService) {
        this.termDepositOperationService = termDepositOperationService;
        this.openedTermDepositService = openedTermDepositService;
    }

    @Override
    public void makeOperation(TermDepositOperationInfo info) {
        OpenedTermDeposit deposit = openedTermDepositService.loadOpenedTermDepositById(info.getDeposit().getId());
        deposit.setAmount(deposit.getAmount() + info.getAmount());
        termDepositOperationService.createTermDepositOperation(LocalDate.now(), info.getType(), info.getAmount(), deposit.getAmount(), info.getDeposit().getId());
    }

    @Override
    public OperationType getType() {
        return OperationType.REPLENISHMENT;
    }
}
