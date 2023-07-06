package com.example.deposit_system.repositories.deposits;

import com.example.deposit_system.entity.deposits.TermDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TermDepositRepository extends JpaRepository<TermDeposit, Long> {
    List<TermDeposit> findTermDepositsByDepositNameContains(String keyword);
    List<TermDeposit> findTermDepositsByBankBankName(String bankName);
    TermDeposit findTermDepositByDepositName(String bankName);
}
