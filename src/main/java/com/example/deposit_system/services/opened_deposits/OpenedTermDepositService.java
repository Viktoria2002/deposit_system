package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;

import java.time.LocalDate;
import java.util.List;

public interface OpenedTermDepositService {
    OpenedTermDeposit loadOpenedTermDepositById(Long id);

    OpenedTermDeposit createOpenedTermDeposit(double amount, LocalDate openingDate, Long termDepositId, Long cardId, Long clientId);

    OpenedTermDeposit createOrUpdateOpenedTermDeposit(OpenedTermDeposit openedTermDeposit);

    List<OpenedTermDeposit> getAllOpenedTermDeposits();

    List<OpenedTermDeposit> getOpenedTermDepositsOfClient(Long clientId);

    List<OpenedTermDeposit> findOpenedTermDepositsByDepositName(String depositName);

    List<OpenedTermDeposit> getOpenedTermDepositsOfClientWithOpenStatus(Long clientId);

    List<OpenedTermDeposit> getOpenedTermDepositsOfClientWithCloseStatus(Long clientId);

    List<OpenedTermDeposit> findOpenedTermDepositsByOpeningDate(LocalDate date);

    List<OpenedTermDeposit> getExpiredDepositsOfClient(Client client);
}
