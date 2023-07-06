package com.example.deposit_system.web.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.services.BankService;
import com.example.deposit_system.services.deposits.DemandDepositService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/demandDeposits")
public class DemandDepositController {
    private DemandDepositService demandDepositService;
    private BankService bankService;

    public DemandDepositController(DemandDepositService demandDepositService, BankService bankService) {
        this.demandDepositService = demandDepositService;
        this.bankService = bankService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String demandDeposits(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<DemandDeposit> deposits = new CopyOnWriteArrayList<>(demandDepositService.findDemandDepositsByName(keyword));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(DEMAND_DEPOSIT, new DemandDeposit());
        model.addAttribute(LIST_DEMAND_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "demandDeposit-views/demandDeposits";
    }

    @GetMapping(value = "/depositSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String bankSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<DemandDeposit> deposits = new CopyOnWriteArrayList<>(demandDepositService.findDemandDepositsByName(keyword));
        deposits.sort(Comparator.comparing(DemandDeposit::getDepositName));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(DEMAND_DEPOSIT, new DemandDeposit());
        model.addAttribute(LIST_DEMAND_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "demandDeposit-views/demandDeposits";
    }

    @GetMapping(value = "/interestRateSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String interestRateSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<DemandDeposit> deposits = new CopyOnWriteArrayList<>(demandDepositService.findDemandDepositsByName(keyword));
        deposits.sort(Comparator.comparing(DemandDeposit::getInterestRate));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(DEMAND_DEPOSIT, new DemandDeposit());
        model.addAttribute(LIST_DEMAND_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "demandDeposit-views/demandDeposits";
    }

    @GetMapping(value = "/amountSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String amountSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<DemandDeposit> deposits = new CopyOnWriteArrayList<>(demandDepositService.findDemandDepositsByName(keyword));
        deposits.sort(Comparator.comparing(DemandDeposit::getAmount));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(DEMAND_DEPOSIT, new DemandDeposit());
        model.addAttribute(LIST_DEMAND_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "demandDeposit-views/demandDeposits";
    }

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String search(DemandDeposit demandDeposit, Model model) {
        List<DemandDeposit> optimalDeposits = demandDepositService.getOptimalDeposits(demandDeposit);
        model.addAttribute(LIST_DEMAND_DEPOSITS, optimalDeposits);
        return "demandDeposit-views/demandDeposits";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteDemandDeposit(Long depositId, String keyword) {
        demandDepositService.removeDemandDeposit(depositId);
        return "redirect:/demandDeposits/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formDemandDeposit(Model model) {
        List<Bank> banks = bankService.getAllBanks();
        model.addAttribute(DEMAND_DEPOSIT, new DemandDeposit());
        model.addAttribute(LIST_BANKS, banks);
        return "demandDeposit-views/formCreate";
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateDemandDeposit(Model model, Long depositId) {
        List<Bank> banks = bankService.getAllBanks();
        DemandDeposit demandDeposit = demandDepositService.loadDemandDepositById(depositId);
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(DEMAND_DEPOSIT, demandDeposit);
        return "demandDeposit-views/formUpdate";
    }

    @GetMapping(value = "/{depositId}")
    @PreAuthorize("hasAnyAuthority('Client')")
    public ResponseEntity<?> demandDeposit(@PathVariable Long depositId) {
        DemandDeposit deposit = demandDepositService.loadDemandDepositById(depositId);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid DemandDeposit deposit, BindingResult bindingResult) {
        DemandDeposit foundedDeposit = demandDepositService.findDemandDepositByName(deposit.getDepositName());
        if (foundedDeposit != null) {
            bindingResult.rejectValue("depositName", null, "Вклад с таким названием уже был добавлен.");
        }
        if(bindingResult.hasErrors()) return "demandDeposit-views/formCreate";
        demandDepositService.createOrUpdateDemandDeposit(deposit);
        return "redirect:/demandDeposits/index";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid DemandDeposit deposit, BindingResult bindingResult) {
        DemandDeposit foundedDeposit = demandDepositService.findDemandDepositByName(deposit.getDepositName());
        DemandDeposit currentDeposit = demandDepositService.loadDemandDepositById(deposit.getDepositId());
        if (foundedDeposit != null && !foundedDeposit.getDepositName().equals(currentDeposit.getDepositName())) {
            bindingResult.rejectValue("depositName", null, "Вклад с таким названием уже был добавлен.");
        }
        if(bindingResult.hasErrors()) return "demandDeposit-views/formUpdate";
        demandDepositService.createOrUpdateDemandDeposit(deposit);
        return "redirect:/demandDeposits/index";
    }
}
