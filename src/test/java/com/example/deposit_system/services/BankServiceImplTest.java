package com.example.deposit_system.services;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.repositories.BankRepository;
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
class BankServiceImplTest {
    @InjectMocks
    private BankServiceImpl bankService;
    @Mock
    private BankRepository bankRepository;

    @Test
    void testLoadBankById() {
        Bank bank = new Bank();
        bank.setBankId(1L);
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        Bank actualBank = bankService.loadBankById(1L);
        assertEquals(bank, actualBank);
    }

    @Test
    void testFindBanksByName() {
        String name = "bank";
        bankService.findBanksByName(name);
        verify(bankRepository).findBanksByBankNameContains(name);
    }

    @Test
    void testFindBankByName() {
        bankService.findBankByName("bank1");
        verify(bankRepository, times(1)).findBankByBankName(any());
    }

    @Test
    void testCreateOrUpdateBank() {
        Bank bank = new Bank();
        bank.setBankId(1L);
        bankService.createOrUpdateBank(bank);
        verify(bankRepository).save(bank);
    }

    @Test
    void testGetAllBanks() {
        bankService.getAllBanks();
        verify(bankRepository, times(1)).findAll();
    }

    @Test
    void testRemoveBank() {
        bankService.removeBank(1L);
        verify(bankRepository, times(1)).deleteById(any());
    }
}