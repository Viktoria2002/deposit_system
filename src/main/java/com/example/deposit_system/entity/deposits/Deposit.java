package com.example.deposit_system.entity.deposits;

import com.example.deposit_system.entity.Bank;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public abstract class Deposit {
    @Id
    @GenericGenerator(name = "depositId", strategy = "increment")
    @GeneratedValue(generator = "depositId")
    @Column(name = "deposit_id")
    protected Long depositId;

    @NotBlank(message = "Поле является обязательным.")
    @Size(max = 25, message = "Название вклада не должно превышать 25 символов")
    @Column(name = "deposit_name", nullable = false)
    protected String depositName;

    @Min(value = 1, message = "Процентная ставка должна быть не менее 1%.")
    @Max(value = 16, message = "Процентная ставка должна быть не больше 16%.")
    @Column(name = "interest_rate", nullable = false)
    protected double interestRate;

    @Min(value = 50, message = "Сумма вклада должна превышать 50 денежных единиц.")
    @Column(name = "deposit_amount", nullable = false)
    protected double amount;

    @NotBlank(message = "Поле является обязательным.")
    @Size(max = 3, message = "Название валюты не должно превышать 3 символов")
    @Column(name = "currency", nullable = false)
    protected String currency;

    @Column(name = "capitalization")
    protected boolean hasCapitalization;

    @Column(name = "replenishment")
    protected boolean hasReplenishment;

    @Column(name = "partial_withdrawal")
    protected boolean hasPartialWithdrawal;

    @Column(name = "early_withdrawal")
    protected boolean hasEarlyWithdrawal;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    @ToString.Exclude
    protected Bank bank;

    public Deposit(String name, double interestRate, double amount, String currency, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Bank bank) {
        this.depositName = name;
        this.interestRate = interestRate;
        this.amount = amount;
        this.currency = currency;
        this.hasCapitalization = hasCapitalization;
        this.hasReplenishment = hasReplenishment;
        this.hasPartialWithdrawal = hasPartialWithdrawal;
        this.hasEarlyWithdrawal = hasEarlyWithdrawal;
        this.bank = bank;
    }
}
