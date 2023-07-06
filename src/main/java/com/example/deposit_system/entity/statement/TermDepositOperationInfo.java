package com.example.deposit_system.entity.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "term_deposit_operations")
@Data
@NoArgsConstructor
public class TermDepositOperationInfo extends OperationInfo {
    @ManyToOne
    @JoinColumn(name = "opened_term_deposit_id")
    @EqualsAndHashCode.Exclude
    private OpenedTermDeposit deposit;

    public TermDepositOperationInfo(LocalDate dateOfOperation, OperationType type, double amount, double balance, OpenedTermDeposit deposit) {
        super(dateOfOperation, type, amount, balance);
        this.deposit = deposit;
    }

}
