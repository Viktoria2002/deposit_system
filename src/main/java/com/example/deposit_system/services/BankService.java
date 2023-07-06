package com.example.deposit_system.services;

import com.example.deposit_system.entity.Bank;

import java.util.List;

public interface BankService {
    Bank loadBankById(Long bankId);

    List<Bank> findBanksByName(String name);

    Bank findBankByName(String name);

    Bank createOrUpdateBank(Bank bank);

    List<Bank> getAllBanks();

    void removeBank(Long bankId);
}
