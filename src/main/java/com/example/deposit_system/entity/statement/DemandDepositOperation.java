package com.example.deposit_system.entity.statement;

public interface DemandDepositOperation {
    public void makeOperation(DemandDepositOperationInfo info);
    OperationType getType();
}
