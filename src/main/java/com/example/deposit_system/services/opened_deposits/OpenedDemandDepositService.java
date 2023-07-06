package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;

import java.time.LocalDate;
import java.util.List;

public interface OpenedDemandDepositService {
    OpenedDemandDeposit loadOpenedDemandDepositById(Long id);

    OpenedDemandDeposit createOpenedDemandDeposit(double amount, LocalDate openingDate, Long demandDepositId, Long cardId, Long clientId);

    OpenedDemandDeposit createOrUpdateOpenedDemandDeposit(OpenedDemandDeposit openedDemandDeposit);

    List<OpenedDemandDeposit> getAllOpenedDemandDeposits();

    List<OpenedDemandDeposit> getOpenedDemandDepositsOfClient(Long clientId);

    List<OpenedDemandDeposit> findOpenedDemandDepositsByDepositName(String depositName);

    List<OpenedDemandDeposit> getOpenedDemandDepositsOfClientWithOpenStatus(Long clientId);
    List<OpenedDemandDeposit> getOpenedDemandDepositsOfClientWithCloseStatus(Long clientId);
    List<OpenedDemandDeposit> findOpenedDemandDepositsByOpeningDate(LocalDate date);

}
