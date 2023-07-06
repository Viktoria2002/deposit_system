package com.example.deposit_system.repositories;

import com.example.deposit_system.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findBanksByBankNameContains(String name);
    Bank findBankByBankName(String name);
}
