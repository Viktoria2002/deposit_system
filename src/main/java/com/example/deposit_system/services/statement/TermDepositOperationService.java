package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.entity.statement.TermDepositOperationInfo;

import java.time.LocalDate;
import java.util.List;

public interface TermDepositOperationService {
    TermDepositOperationInfo createTermDepositOperation(LocalDate dateOfOperation, OperationType type, double amount, double balance, Long openedTermDepositId);
    List<TermDepositOperationInfo> getTermDepositOperationsOfOpenedTermDeposit(Long depositId);
    void makeOperation(TermDepositOperationInfo info);

}
