package com.example.deposit_system.repositories.deposits;

import com.example.deposit_system.entity.deposits.DemandDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandDepositRepository extends JpaRepository<DemandDeposit, Long> {
    List<DemandDeposit> findDemandDepositsByDepositNameContains(String keyword);
    List<DemandDeposit> findDemandDepositsByBankBankName(String bankName);
    DemandDeposit findDemandDepositByDepositName(String bankName);
}
