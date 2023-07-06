package com.example.deposit_system.web;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.services.BankService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.deposit_system.constants.Constants.*;

@Controller
@RequestMapping(value = "/banks")
public class BankController {
    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Admin')")
    public String banks(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.findBanksByName(keyword));
        model.addAttribute(LIST_BANKS, banks);
        model.addAttribute(KEYWORD, keyword);
        return "bank-views/banks";
    }

    @GetMapping(value = "/sorting")
    @PreAuthorize("hasAuthority('Admin')")
    public String sorting(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Bank> banks = new CopyOnWriteArrayList<>(bankService.findBanksByName(keyword));
        banks.sort(Comparator.comparing(Bank::getBankName));
        model.addAttribute(LIST_BANKS, banks);
        return "bank-views/banks";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteBank(Long bankId, String keyword) {
        bankService.removeBank(bankId);
        return "redirect:/banks/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('Admin')")
    public String formBanks(Model model) {
        model.addAttribute(BANK, new Bank());
        return "bank-views/formCreate";
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateCourse(Model model, Long bankId) {
        Bank bank = bankService.loadBankById(bankId);
        model.addAttribute(BANK, bank);
        return "bank-views/formUpdate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Admin')")
    public String save(@Valid Bank bank, BindingResult bindingResult) {
        if (!bank.getLogo().contains("/logos/")) bank.setLogo("/logos/" + bank.getLogo());
        if (bank.getLogo().contains(",")) bank.setLogo(bank.getLogo().replace(",", ""));
        Bank foundedBank = bankService.findBankByName(bank.getBankName());
        if (foundedBank != null) {
            bindingResult.rejectValue("bankName", null, "Банк с таким названием уже зарегестрирован в системе");
        }
        if (bindingResult.hasErrors()) return "bank-views/formCreate";
        bankService.createOrUpdateBank(bank);
        return "redirect:/banks/index";
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAuthority('Admin')")
    public String update(@Valid Bank bank, BindingResult bindingResult) {
        if (bank.getLogo() == "")
            bank.setLogo(bankService.loadBankById(bank.getBankId()).getLogo());
        else {
            if (!bank.getLogo().contains("/logos/")) bank.setLogo("/logos/" + bank.getLogo());
            if (bank.getLogo().contains(",")) bank.setLogo(bank.getLogo().replace(",", ""));
        }
        Bank currentBank = bankService.loadBankById(bank.getBankId());
        Bank foundedBank = bankService.findBankByName(bank.getBankName());
        if (foundedBank != null && !foundedBank.getBankName().equals(currentBank.getBankName())) {
            bindingResult.rejectValue("bankName", null, "Банк с таким названием уже зарегестрирован в системе");
        }
        if (bindingResult.hasErrors()) return "bank-views/formUpdate";
        bankService.createOrUpdateBank(bank);
        return "redirect:/banks/index";
    }
}
