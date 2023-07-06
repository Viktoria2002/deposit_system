package com.example.deposit_system.entity.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "demand_deposit_operations")
@Data
@NoArgsConstructor
public class DemandDepositOperationInfo extends OperationInfo {
    @ManyToOne
    @JoinColumn(name = "opened_demand_deposit_id")
    @EqualsAndHashCode.Exclude
    private OpenedDemandDeposit deposit;

    public DemandDepositOperationInfo(LocalDate dateOfOperation, OperationType type, double amount, double balance, OpenedDemandDeposit deposit) {
        super(dateOfOperation, type, amount, balance);
        this.deposit = deposit;
    }
}
