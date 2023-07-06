package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;

import java.util.List;

public interface TermDepositService {
    TermDeposit loadTermDepositById(Long id);

    List<TermDeposit> findTermDepositsByName(String name);
    List<TermDeposit> findTermDepositsByBankName(String bankName);
    TermDeposit findTermDepositByName(String bankName);

    TermDeposit createTermDeposit(String name, double interestRate, double amount, String currency, int term, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Long bankId);

    TermDeposit createOrUpdateTermDeposit(TermDeposit termDeposit);

    List<TermDeposit> getAllTermDeposits();

    List<TermDeposit> getOptimalDeposits(TermDeposit termDeposit);

    void removeTermDeposit(Long id);
}
