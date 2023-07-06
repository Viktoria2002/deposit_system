package com.example.deposit_system.entity.statement;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class OperationInfo {
    @Id
    @GenericGenerator(name = "idOperation", strategy = "increment")
    @GeneratedValue(generator = "idOperation")
    @Column(name = "operation_id")
    @EqualsAndHashCode.Exclude
    protected Long id;
    @Column(name = "date", nullable = false)
    protected LocalDate dateOfOperation;
    @Column(name = "operation_type", nullable = false)
    protected OperationType type;
    @Column(name = "amount", nullable = false)
    protected double amount;
    @Column(name = "balance", nullable = false)
    protected double balance;

    public OperationInfo(LocalDate dateOfOperation, OperationType type, double amount, double balance) {
        this.dateOfOperation = dateOfOperation;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }
}

