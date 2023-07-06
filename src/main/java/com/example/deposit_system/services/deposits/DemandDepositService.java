package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;

import java.util.List;

public interface DemandDepositService {
    DemandDeposit loadDemandDepositById(Long id);

    List<DemandDeposit> findDemandDepositsByName(String name);
    List<DemandDeposit> findDemandDepositsByBankName(String name);
    DemandDeposit findDemandDepositByName(String name);

    DemandDeposit createDemandDeposit(String name, double interestRate, double amount, String currency, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Long bankId);

    DemandDeposit createOrUpdateDemandDeposit(DemandDeposit demandDeposit);

    List<DemandDeposit> getAllDemandDeposits();
    List<DemandDeposit> getOptimalDeposits(DemandDeposit demandDeposit);

    void removeDemandDeposit(Long id);
}
