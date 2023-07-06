package com.example.deposit_system.entity.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.statement.TermDepositOperationInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "opened_term_deposits")
@Data
@NoArgsConstructor
public class OpenedTermDeposit extends OpenedDeposit {
    @ManyToOne
    @JoinColumn(name = "term_deposit_id")
    @ToString.Exclude
    private TermDeposit termDeposit;

    @OneToMany(mappedBy = "deposit", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TermDepositOperationInfo> termDepositOperations = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @JsonIgnore
    private Client client;

    public OpenedTermDeposit(double amount, LocalDate openingDate, Card card, TermDeposit termDeposit, Client client) {
        super(amount, openingDate, card);
        this.termDeposit = termDeposit;
        this.client = client;
    }
}
