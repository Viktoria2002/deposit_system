package com.example.deposit_system.entity;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;
    @Column(name = "payment_system")
    private String paymentSystem;
    @Column(name = "number", nullable = false)
    private long number;
    @Column(name = "month", nullable = false)
    private int month;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "card_balance", nullable = false)
    private double cardBalance;
    @Column(name = "cvv", nullable = false)
    private int cvv;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @JsonIgnore
    private Client client;
    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    @JsonIgnore
    List<OpenedDemandDeposit> demandDeposits;
    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    @JsonIgnore
    List<OpenedTermDeposit> termDeposits;

    public Card(String paymentSystem, long number, int month, int year, int cvv, double balance, Client client) {
        this.paymentSystem = paymentSystem;
        this.number = number;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        this.cardBalance = balance;
        this.client = client;
    }
}
