package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import com.example.deposit_system.repositories.opened_deposits.OpenedTermDepositRepository;
import com.example.deposit_system.repositories.statement.DemandDepositOperationRepository;
import com.example.deposit_system.repositories.statement.TermDepositOperationRepository;
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
class TermDepositOperationServiceImplTest {
    @InjectMocks
    private TermDepositOperationServiceImpl operationService;
    @Mock
    private TermDepositOperationRepository operationRepository;
    @Mock
    private OpenedTermDepositRepository openedTermDepositRepository;

    @Test
    void testCreateTermDepositOperation() {
        OpenedTermDeposit termDeposit = new OpenedTermDeposit();
        termDeposit.setId(1L);
        when(openedTermDepositRepository.findById(any())).thenReturn(Optional.of(termDeposit));
        operationService.createTermDepositOperation(LocalDate.of(2023, 03,11), OperationType.WITHDRAWAL, 100, 1100, termDeposit.getId());
        verify(operationRepository, times(1)).save(any());
    }

    @Test
    void testGetTermDepositOperationsOfOpenedTermDeposit() {
        operationService.getTermDepositOperationsOfOpenedTermDeposit(1L);
        verify(operationRepository, times(1)).findTermDepositOperationsByOpenedTermDepositId(1L);
    }
}