package com.example.deposit_system.entity.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "demand_deposits")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"openedDemandDeposit"})
public class DemandDeposit extends Deposit {
    @OneToMany(mappedBy = "demandDeposit")
    @ToString.Exclude
    @JsonIgnore
    private List<OpenedDemandDeposit> openedDemandDeposits;

    public DemandDeposit(String name, double interestRate, double amount, String currency, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Bank bank) {
        super(name, interestRate, amount, currency, hasCapitalization, hasReplenishment, hasPartialWithdrawal, hasEarlyWithdrawal, bank);
    }
}
