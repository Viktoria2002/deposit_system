package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.repositories.BankRepository;
import com.example.deposit_system.repositories.deposits.DemandDepositRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DemandDepositServiceImpl implements DemandDepositService {
    private DemandDepositRepository demandDepositRepository;
    private BankRepository bankRepository;

    public DemandDepositServiceImpl(DemandDepositRepository demandDepositRepository, BankRepository bankRepository) {
        this.demandDepositRepository = demandDepositRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public DemandDeposit loadDemandDepositById(Long id) {
        return demandDepositRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Demand deposit with id " + id + " Not Found"));
    }

    @Override
    public List<DemandDeposit> findDemandDepositsByName(String name) {
        return demandDepositRepository.findDemandDepositsByDepositNameContains(name);
    }

    @Override
    public List<DemandDeposit> findDemandDepositsByBankName(String name) {
        return demandDepositRepository.findDemandDepositsByBankBankName(name);
    }

    @Override
    public DemandDeposit findDemandDepositByName(String name) {
        return demandDepositRepository.findDemandDepositByDepositName(name);
    }

    @Override
    public DemandDeposit createDemandDeposit(String name, double interestRate, double amount, String currency, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new EntityNotFoundException("Bank with id" + bankId + " Not Found"));
        return demandDepositRepository.save(new DemandDeposit(name, interestRate, amount, currency, hasCapitalization, hasReplenishment, hasPartialWithdrawal, hasEarlyWithdrawal, bank));
    }

    @Override
    public DemandDeposit createOrUpdateDemandDeposit(DemandDeposit demandDeposit) {
        return demandDepositRepository.save(demandDeposit);
    }

    @Override
    public List<DemandDeposit> getAllDemandDeposits() {
        return demandDepositRepository.findAll();
    }

    @Override
    public List<DemandDeposit> getOptimalDeposits(DemandDeposit demandDeposit) {
        List<DemandDeposit> deposits = getAllDemandDeposits();
        List<DemandDeposit> filteredStream;
        if(demandDeposit.getAmount() != 0) {
            filteredStream = deposits.stream()
                    .filter(d -> d.getAmount() <= demandDeposit.getAmount())
                    .collect(Collectors.toList());
        } else filteredStream = deposits;
        filteredStream = filteredStream.stream()
                .filter(d -> d.getCurrency().equals(demandDeposit.getCurrency()))
                .collect(Collectors.toList());
        if(demandDeposit.getInterestRate() != 0) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.getInterestRate() >= demandDeposit.getInterestRate() )
                    .collect(Collectors.toList());
        }
        if(demandDeposit.getBank() != null) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.getBank().getBankId() == demandDeposit.getBank().getBankId())
                    .collect(Collectors.toList());
        }
        if(demandDeposit.isHasCapitalization())  {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasCapitalization() == true)
                    .collect(Collectors.toList());
        }
        if(demandDeposit.isHasReplenishment()) {
            filteredStream = filteredStream.stream()
                    .filter(d ->  d.isHasReplenishment() == true)
                    .collect(Collectors.toList());
        }
        if(demandDeposit.isHasPartialWithdrawal()) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasPartialWithdrawal() == true)
                    .collect(Collectors.toList());
        }
        if(demandDeposit.isHasEarlyWithdrawal()) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasEarlyWithdrawal() == true)
                    .collect(Collectors.toList());
        }
        return filteredStream;
    }

    @Override
    public void removeDemandDeposit(Long id) {
        DemandDeposit demandDeposit = loadDemandDepositById(id);
        for(OpenedDemandDeposit deposit : demandDeposit.getOpenedDemandDeposits()) {
            if(deposit != null) {
                deposit.setDemandDeposit(null);
                deposit.setStatus("Закрыт");
            }
        }
        demandDepositRepository.deleteById(id);
    }
}
