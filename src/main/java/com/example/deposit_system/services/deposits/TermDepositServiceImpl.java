package com.example.deposit_system.services.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.repositories.BankRepository;
import com.example.deposit_system.repositories.deposits.TermDepositRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TermDepositServiceImpl implements TermDepositService {
    private TermDepositRepository termDepositRepository;
    private BankRepository bankRepository;

    public TermDepositServiceImpl(TermDepositRepository termDepositRepository, BankRepository bankRepository) {
        this.termDepositRepository = termDepositRepository;
        this.bankRepository = bankRepository;
    }

    @Override
    public TermDeposit loadTermDepositById(Long id) {
        return termDepositRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Term deposit with id " + id + " Not Found"));
    }

    @Override
    public List<TermDeposit> findTermDepositsByName(String name) {
        return termDepositRepository.findTermDepositsByDepositNameContains(name);
    }

    @Override
    public List<TermDeposit> findTermDepositsByBankName(String bankName) {
        return termDepositRepository.findTermDepositsByBankBankName(bankName);
    }

    @Override
    public TermDeposit findTermDepositByName(String bankName) {
        return termDepositRepository.findTermDepositByDepositName(bankName);
    }

    @Override
    public TermDeposit createTermDeposit(String name, double interestRate, double amount, String currency, int term, boolean hasCapitalization, boolean hasReplenishment, boolean hasPartialWithdrawal, boolean hasEarlyWithdrawal, Long bankId) {
        Bank bank = bankRepository.findById(bankId).orElseThrow(() -> new EntityNotFoundException("Bank with id" + bankId + " Not Found"));
        return termDepositRepository.save(new TermDeposit(name, interestRate, amount, currency, hasCapitalization, hasReplenishment, hasPartialWithdrawal, hasEarlyWithdrawal, bank, term));
    }

    @Override
    public TermDeposit createOrUpdateTermDeposit(TermDeposit termDeposit) {
        return termDepositRepository.save(termDeposit);
    }

    @Override
    public List<TermDeposit> getAllTermDeposits() {
        return termDepositRepository.findAll();
    }

    @Override
    public List<TermDeposit> getOptimalDeposits(TermDeposit termDeposit) {
        List<TermDeposit> deposits = getAllTermDeposits();
        List<TermDeposit> filteredStream;
        if(termDeposit.getAmount() != 0) {
            filteredStream = deposits.stream()
                    .filter(d -> d.getAmount() <= termDeposit.getAmount())
                    .collect(Collectors.toList());
        } else filteredStream = deposits;
        filteredStream = filteredStream.stream()
                .filter(d -> d.getCurrency().equals(termDeposit.getCurrency()))
                .collect(Collectors.toList());
        if(termDeposit.getTerm() != 0) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.getTerm() == termDeposit.getTerm() )
                    .collect(Collectors.toList());
        }
        if(termDeposit.getBank() != null) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.getBank().getBankId() == termDeposit.getBank().getBankId())
                    .collect(Collectors.toList());
        }
        if(termDeposit.isHasCapitalization())  {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasCapitalization() == true)
                    .collect(Collectors.toList());
        }
        if(termDeposit.isHasReplenishment()) {
            filteredStream = filteredStream.stream()
                    .filter(d ->  d.isHasReplenishment() == true)
                    .collect(Collectors.toList());
        }
        if(termDeposit.isHasPartialWithdrawal()) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasPartialWithdrawal() == true)
                    .collect(Collectors.toList());
        }
        if(termDeposit.isHasEarlyWithdrawal()) {
            filteredStream = filteredStream.stream()
                    .filter(d -> d.isHasEarlyWithdrawal() == true)
                    .collect(Collectors.toList());
        }
        return filteredStream;
    }

    @Override
    public void removeTermDeposit(Long id) {
        TermDeposit termDeposit = loadTermDepositById(id);
        for(OpenedTermDeposit deposit : termDeposit.getOpenedTermDeposits()) {
            if(deposit != null) {
                deposit.setTermDeposit(null);
                deposit.setStatus("Закрыт");
            }
        }
        termDepositRepository.deleteById(id);
    }
}
