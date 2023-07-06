package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.entity.statement.OperationType;

import java.time.LocalDate;
import java.util.List;

public interface DemandDepositOperationService {
    DemandDepositOperationInfo createDemandDepositOperation(LocalDate dateOfOperation, OperationType type, double amount, double balance, Long openedDemandDepositId);
    List<DemandDepositOperationInfo> getDemandDepositOperationsOfOpenedDemandDeposit(Long depositId);
    void makeOperation(DemandDepositOperationInfo info);
}
