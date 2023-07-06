package com.example.deposit_system.repositories.statement;

import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemandDepositOperationRepository extends JpaRepository<DemandDepositOperationInfo, Long> {
    @Query(value = "select * from demand_deposit_operations as d where d.opened_demand_deposit_id =:depositId", nativeQuery = true)
    List<DemandDepositOperationInfo> findDemandDepositOperationsByOpenedDemandDepositId(@Param("depositId") Long depositId);
}
