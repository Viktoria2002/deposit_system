package com.example.deposit_system.entity.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
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
@Table(name = "opened_demand_deposits")
@Data
@NoArgsConstructor
public class OpenedDemandDeposit extends OpenedDeposit {
    @ManyToOne
    @JoinColumn(name = "demand_deposit_id")
    @ToString.Exclude
    private DemandDeposit demandDeposit;
    @OneToMany(mappedBy = "deposit", fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
//    @Fetch(value = FetchMode.SUBSELECT)
    private List<DemandDepositOperationInfo> demandDepositOperations = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    @JsonIgnore
    private Client client;

    public OpenedDemandDeposit(double amount, LocalDate openingDate, Card card, DemandDeposit demandDeposit, Client client) {
        super(amount, openingDate, card);
        this.demandDeposit = demandDeposit;
        this.client = client;
    }
}
