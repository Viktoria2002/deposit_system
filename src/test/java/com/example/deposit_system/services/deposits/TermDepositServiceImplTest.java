package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.repositories.BankRepository;
import com.example.deposit_system.repositories.deposits.TermDepositRepository;
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
class TermDepositServiceImplTest {
    @InjectMocks
    private TermDepositServiceImpl termDepositService;
    @Mock
    private TermDepositRepository termDepositRepository;
    @Mock
    private BankRepository bankRepository;
    @Test
    void loadTermDepositById() {
        TermDeposit termDeposit = new TermDeposit();
        termDeposit.setDepositId(1L);
        when(termDepositRepository.findById(any())).thenReturn(Optional.of(termDeposit));
        TermDeposit actualTermDeposit = termDepositService.loadTermDepositById(1L);
        assertEquals(termDeposit, actualTermDeposit);
    }

    @Test
    void findTermDepositsByName() {
        String name = "deposit";
        termDepositService.findTermDepositsByName(name);
        verify(termDepositRepository).findTermDepositsByDepositNameContains(name);
    }

    @Test
    void findTermDepositsByBankName() {
        String bankName = "bank";
        termDepositService.findTermDepositsByBankName(bankName);
        verify(termDepositRepository).findTermDepositsByBankBankName(bankName);
    }

    @Test
    void createTermDeposit() {
        Bank bank = new Bank();
        bank.setBankId(1L);
        when(bankRepository.findById(any())).thenReturn(Optional.of(bank));
        termDepositService.createTermDeposit("deposit", 10, 1000, "BYN", 12, true, true, true, true, bank.getBankId());
        verify(termDepositRepository, times(1)).save(any());
    }

    @Test
    void createOrUpdateTermDeposit() {
        TermDeposit termDeposit = new TermDeposit();
        termDeposit.setDepositId(1L);
        termDepositService.createOrUpdateTermDeposit(termDeposit);
        verify(termDepositRepository).save(termDeposit);
    }

    @Test
    void getAllTermDeposits() {
        termDepositService.getAllTermDeposits();
        verify(termDepositRepository, times(1)).findAll();
    }

    @Test
    void removeTermDeposit() {
        termDepositService.removeTermDeposit(1L);
        verify(termDepositRepository, times(1)).deleteById(any());
    }
}