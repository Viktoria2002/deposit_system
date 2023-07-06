package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import com.example.deposit_system.repositories.statement.DemandDepositOperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemandDepositOperationServiceImplTest {
    @InjectMocks
    private DemandDepositOperationServiceImpl operationService;
    @Mock
    private DemandDepositOperationRepository operationRepository;
    @Mock
    private OpenedDemandDepositRepository openedDemandDepositRepository;

    @Test
    void testCreateDemandDepositOperation() {
        OpenedDemandDeposit demandDeposit = new OpenedDemandDeposit();
        demandDeposit.setId(1L);
        when(openedDemandDepositRepository.findById(any())).thenReturn(Optional.of(demandDeposit));
        operationService.createDemandDepositOperation(LocalDate.of(2023, 03,11), OperationType.CAPITALIZATION, 100, 1100, demandDeposit.getId());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void testGetDemandDepositOperationsOfOpenedDemandDeposit() {
        operationService.getDemandDepositOperationsOfOpenedDemandDeposit(1L);
        verify(operationRepository, times(1)).findDemandDepositOperationsByOpenedDemandDepositId(1L);
    }
}