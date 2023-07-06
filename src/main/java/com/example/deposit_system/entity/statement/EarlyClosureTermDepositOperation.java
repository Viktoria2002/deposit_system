package com.example.deposit_system.entity.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.apache.commons.math3.util.Precision;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EarlyClosureTermDepositOperation implements TermDepositOperation {
    private TermDepositOperationService termDepositOperationService;
    private OpenedTermDepositService openedTermDepositService;

    public EarlyClosureTermDepositOperation(@Lazy TermDepositOperationService termDepositOperationService, OpenedTermDepositService openedTermDepositService) {
        this.termDepositOperationService = termDepositOperationService;
        this.openedTermDepositService = openedTermDepositService;
    }

    @Override
    public void makeOperation(TermDepositOperationInfo info) {
        OpenedTermDeposit deposit = openedTermDepositService.loadOpenedTermDepositById(info.getDeposit().getId());
        List<TermDepositOperationInfo> operations = termDepositOperationService.getTermDepositOperationsOfOpenedTermDeposit(deposit.getId());
        for(TermDepositOperationInfo operation : operations) {
            if(operation.getType().equals(OperationType.CAPITALIZATION)) {
                deposit.setAmount(Precision.round(deposit.getAmount() - operation.getAmount() * 2 / 3, 2));
            }
        }
        deposit.setStatus("Закрыт");
        termDepositOperationService.createTermDepositOperation(LocalDate.now(), info.getType(), deposit.getAmount(), 0, info.getDeposit().getId());
        deposit.setAmount(0);
    }
    @Override
    public OperationType getType() {
        return OperationType.EARLY_CLOSURE;
    }
}
