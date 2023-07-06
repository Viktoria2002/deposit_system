package com.example.deposit_system.repositories.statement;

import com.example.deposit_system.entity.statement.TermDepositOperationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TermDepositOperationRepository extends JpaRepository<TermDepositOperationInfo, Long> {
    @Query(value = "select * from term_deposit_operations as d where d.opened_term_deposit_id =:depositId", nativeQuery = true)
    List<TermDepositOperationInfo> findTermDepositOperationsByOpenedTermDepositId(@Param("depositId") Long depositId);
}
