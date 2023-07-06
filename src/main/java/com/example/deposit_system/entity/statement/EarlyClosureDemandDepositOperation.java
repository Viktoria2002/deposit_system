package com.example.deposit_system.entity.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EarlyClosureDemandDepositOperation implements DemandDepositOperation {
    private DemandDepositOperationService demandDepositOperationService;
    private OpenedDemandDepositService openedDemandDepositService;

    public EarlyClosureDemandDepositOperation(@Lazy DemandDepositOperationService demandDepositOperationService, OpenedDemandDepositService openedDemandDepositService) {
        this.demandDepositOperationService = demandDepositOperationService;
        this.openedDemandDepositService = openedDemandDepositService;
    }

    @Override
    public void makeOperation(DemandDepositOperationInfo info) {
        OpenedDemandDeposit deposit = openedDemandDepositService.loadOpenedDemandDepositById(info.getDeposit().getId());
        deposit.setAmount(0);
        deposit.setStatus("Закрыт");
        demandDepositOperationService.createDemandDepositOperation(LocalDate.now(), info.getType(), info.getAmount(), deposit.getAmount(), info.getDeposit().getId());
    }
    @Override
    public OperationType getType() {
        return OperationType.EARLY_CLOSURE;
    }
}
