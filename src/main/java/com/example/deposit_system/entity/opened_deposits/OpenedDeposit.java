package com.example.deposit_system.entity.opened_deposits;

import javax.persistence.*;
import javax.validation.constraints.Min;

import com.example.deposit_system.entity.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"termDeposit", "demandDeposit"})
public abstract class OpenedDeposit implements Serializable {
    @Id
    @GenericGenerator(name = "idDeposit", strategy = "increment")
    @GeneratedValue(generator = "idDeposit")
    @Column(name = "opened_deposit_id")
    protected Long id;
    @Column(name = "amount", nullable = false)
    protected double amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "opening_date", nullable = false)
    protected LocalDate openingDate;
    @Column(name = "status", nullable = false)
    protected String status;
    @ManyToOne
    @JoinColumn(name = "card_id")
    @ToString.Exclude
    @JsonIgnore
    protected Card card;

    public OpenedDeposit(double amount, LocalDate openingDate, Card card) {
        this.amount = amount;
        this.openingDate = openingDate;
        this.status = "Открыт";
        this.card = card;
    }
}
