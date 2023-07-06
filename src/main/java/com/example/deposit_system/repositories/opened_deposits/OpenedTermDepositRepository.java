package com.example.deposit_system.repositories.opened_deposits;

import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OpenedTermDepositRepository extends JpaRepository<OpenedTermDeposit, Long> {
    List<OpenedTermDeposit> findOpenedTermDepositsByClientClientId(Long clientId);
    List<OpenedTermDeposit> findOpenedTermDepositsByTermDepositDepositName(String depositName);
    List<OpenedTermDeposit> findOpenedTermDepositsByOpeningDate(LocalDate date);
}
