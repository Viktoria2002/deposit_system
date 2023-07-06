package com.example.deposit_system.entity.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Table(name = "term_deposits")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"openedTermDeposit"})
public class TermDeposit extends Deposit {

    @OneToMany(mappedBy = "termDeposit")
    @ToString.Exclude
    @JsonIgnore
    private List<OpenedTermDeposit> openedTermDeposits;
    @Min(value = 9, message = "Минимальный срок вклада - 10 месяцев.")
    @Column(name = "term", nullable = false)
    private int term;

    public TermDeposit(String name, double interestRate, double amount, String currency, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Bank bank, int term) {
        super(name, interestRate, amount, currency, hasCapitalization, hasReplenishment, hasPartialWithdrawal, hasEarlyWithdrawal, bank);
        this.term = term;
    }
}
