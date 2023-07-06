package com.example.deposit_system.repositories.opened_deposits;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OpenedDemandDepositRepository extends JpaRepository<OpenedDemandDeposit, Long> {
    List<OpenedDemandDeposit> findOpenedDemandDepositsByClientClientId(Long clientId);
    List<OpenedDemandDeposit> findOpenedDemandDepositsByDemandDepositDepositName(String depositName);
    List<OpenedDemandDeposit> findOpenedDemandDepositsByOpeningDate(LocalDate date);
}
