package com.example.deposit_system.services;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.repositories.BankRepository;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class BankServiceImpl implements BankService {
    private BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank loadBankById(Long bankId) {
        return bankRepository.findById(bankId).orElseThrow(() -> new EntityNotFoundException("Bank with id " + bankId + " Not Found"));
    }

    @Override
    public List<Bank> findBanksByName(String name) {
        return bankRepository.findBanksByBankNameContains(name);
    }

    @Override
    public Bank findBankByName(String name) {
        return bankRepository.findBankByBankName(name);
    }

    @Override
    public Bank createOrUpdateBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public void removeBank(Long bankId) {
        Bank bank = loadBankById(bankId);
        List<DemandDeposit> demandDeposits = bank.getDemandDeposits();
        for(DemandDeposit demandDeposit : demandDeposits) {
            List<OpenedDemandDeposit> openedDemandDeposits = demandDeposit.getOpenedDemandDeposits();
            for(OpenedDemandDeposit openedDemandDeposit : openedDemandDeposits) {
                openedDemandDeposit.setDemandDeposit(null);
                openedDemandDeposit.setStatus("Закрыт");
            }
        }
        List<TermDeposit> termDeposits = bank.getTermDeposits();
        for(TermDeposit termDeposit : termDeposits) {
            List<OpenedTermDeposit> openedTermDeposits = termDeposit.getOpenedTermDeposits();
            for(OpenedTermDeposit openedTermDeposit : openedTermDeposits) {
                openedTermDeposit.setTermDeposit(null);
                openedTermDeposit.setStatus("Закрыт");
            }
        }
        bankRepository.deleteById(bankId);
    }
}
