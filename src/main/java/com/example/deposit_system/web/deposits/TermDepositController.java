package com.example.deposit_system.web.deposits;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.services.BankService;
import com.example.deposit_system.services.deposits.TermDepositService;
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
import java.util.stream.Collectors;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/termDeposits")
public class TermDepositController {
    private TermDepositService termDepositService;
    private BankService bankService;

    public TermDepositController(TermDepositService termDepositService, BankService bankService) {
        this.termDepositService = termDepositService;
        this.bankService = bankService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String termDeposits(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<TermDeposit> termDeposits = new CopyOnWriteArrayList<>(termDepositService.findTermDepositsByName(keyword));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(TERM_DEPOSIT, new TermDeposit());
        model.addAttribute(KEYWORD, keyword);
        model.addAttribute(LIST_TERM_DEPOSITS, termDeposits);
        return "termDeposit-views/termDeposits";
    }

    @GetMapping(value = "/depositSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String bankSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<TermDeposit> deposits = new CopyOnWriteArrayList<>(termDepositService.findTermDepositsByName(keyword));
        deposits.sort(Comparator.comparing(TermDeposit::getDepositName));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(TERM_DEPOSIT, new TermDeposit());
        model.addAttribute(LIST_TERM_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "termDeposit-views/termDeposits";
    }

    @GetMapping(value = "/interestRateSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String interestRateSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<TermDeposit> deposits = new CopyOnWriteArrayList<>(termDepositService.findTermDepositsByName(keyword));
        deposits.sort(Comparator.comparing(TermDeposit::getInterestRate));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(TERM_DEPOSIT, new TermDeposit());
        model.addAttribute(LIST_TERM_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "termDeposit-views/termDeposits";
    }

    @GetMapping(value = "/amountSorting")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String amountSorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "")String keyword) {
        List<TermDeposit> deposits = new CopyOnWriteArrayList<>(termDepositService.findTermDepositsByName(keyword));
        deposits.sort(Comparator.comparing(TermDeposit::getAmount));
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.getAllBanks());
        banks.add(Bank.builder().bankName("Любой").bankId(0L).build());
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(TERM_DEPOSIT, new TermDeposit());
        model.addAttribute(LIST_TERM_DEPOSITS, deposits);
        model.addAttribute(KEYWORD, keyword);
        return "termDeposit-views/termDeposits";
    }

    @PostMapping(value = "/search")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String search(TermDeposit termDeposit, Model model) {
        List<TermDeposit> optimalDeposits = termDepositService.getOptimalDeposits(termDeposit);
        model.addAttribute(LIST_TERM_DEPOSITS, optimalDeposits);
        return "termDeposit-views/termDeposits";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteTermDeposit(Long depositId, String keyword) {
        termDepositService.removeTermDeposit(depositId);
        return "redirect:/termDeposits/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formTermDeposit(Model model) {
        List<Bank> banks = bankService.getAllBanks();
        model.addAttribute(TERM_DEPOSIT, new TermDeposit());
        model.addAttribute(LIST_BANKS, banks);
        return "termDeposit-views/formCreate";
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateTermDeposit(Model model, Long depositId) {
        List<Bank> banks = bankService.getAllBanks();
        TermDeposit termDeposit = termDepositService.loadTermDepositById(depositId);
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(TERM_DEPOSIT, termDeposit);
        return "termDeposit-views/formUpdate";
    }

    @GetMapping(value = "/{depositId}")
    public ResponseEntity<?> termDeposit(@PathVariable Long depositId) {
        TermDeposit deposit = termDepositService.loadTermDepositById(depositId);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid TermDeposit deposit, BindingResult bindingResult) {
        TermDeposit foundedDeposit = termDepositService.findTermDepositByName(deposit.getDepositName());
        if (foundedDeposit != null) {
            bindingResult.rejectValue("depositName", null, "Вклад с таким названием уже был добавлен.");
        }
        if(bindingResult.hasErrors()) return "termDeposit-views/formCreate";
        termDepositService.createOrUpdateTermDeposit(deposit);
        return "redirect:/termDeposits/index";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid TermDeposit deposit, BindingResult bindingResult) {
        TermDeposit foundedDeposit = termDepositService.findTermDepositByName(deposit.getDepositName());
        TermDeposit currentDeposit = termDepositService.loadTermDepositById(deposit.getDepositId());
        if (foundedDeposit != null && !foundedDeposit.getDepositName().equals(currentDeposit.getDepositName())) {
            bindingResult.rejectValue("depositName", null, "Вклад с таким названием уже был добавлен.");
        }
        if(bindingResult.hasErrors()) return "termDeposit-views/formUpdate";
        termDepositService.createOrUpdateTermDeposit(deposit);
        return "redirect:/termDeposits/index";
    }
}
