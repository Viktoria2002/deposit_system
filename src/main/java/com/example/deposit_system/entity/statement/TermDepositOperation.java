package com.example.deposit_system.entity.statement;

public interface TermDepositOperation {
    public void makeOperation(TermDepositOperationInfo info);
    OperationType getType();
}
