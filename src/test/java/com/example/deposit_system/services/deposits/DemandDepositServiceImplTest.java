package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.repositories.BankRepository;
import com.example.deposit_system.repositories.deposits.DemandDepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DemandDepositServiceImplTest {
    @InjectMocks
    private DemandDepositServiceImpl demandDepositService;
    @Mock
    private DemandDepositRepository demandDepositRepository;
    @Mock
    private BankRepository bankRepository;

    @Test
    void testLoadDemandDepositById() {
        DemandDeposit demandDeposit = new DemandDeposit();
        demandDeposit.setDepositId(1L);
        when(demandDepositRepository.findById(any())).thenReturn(Optional.of(demandDeposit));
        DemandDeposit actualDemandDeposit = demandDepositService.loadDemandDepositById(1L);
        assertEquals(demandDeposit, actualDemandDeposit);
    }

    @Test
    void testFindDemandDepositsByName() {
        String name = "deposit";
        demandDepositService.findDemandDepositsByName(name);
        verify(demandDepositRepository).findDemandDepositsByDepositNameContains(name);
    }

    @Test
    void testFindDemandDepositsByBankName() {
        String bankName = "bank";
        demandDepositService.findDemandDepositsByBankName(bankName);
        verify(demandDepositRepository).findDemandDepositsByBankBankName(bankName);
    }

    @Test
    void testCreateDemandDeposit() {
        Bank bank = new Bank();
        bank.setBankId(1L);
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        demandDepositService.createDemandDeposit("deposit", 10, 1000, "BYN", true, true, true, true, bank.getBankId());
        verify(demandDepositRepository, times(1)).save(any());
    }

    @Test
    void testCreateOrUpdateDemandDeposit() {
        DemandDeposit demandDeposit = new DemandDeposit();
        demandDeposit.setDepositId(1L);
        demandDepositService.createOrUpdateDemandDeposit(demandDeposit);
        verify(demandDepositRepository).save(demandDeposit);
    }

    @Test
    void testGetAllDemandDeposits() {
        demandDepositService.getAllDemandDeposits();
        verify(demandDepositRepository, times(1)).findAll();
    }

    @Test
    void testRemoveDemandDeposit() {
        demandDepositService.removeDemandDeposit(1L);
        verify(demandDepositRepository, times(1)).deleteById(any());
    }
}