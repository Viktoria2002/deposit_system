package com.example.deposit_system.web;

import com.example.deposit_system.entity.credentials.Passport;
import com.example.deposit_system.services.opened_deposits.OpenedDepositService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/reports")
public class ReportsController {
    private OpenedDepositService openedDepositService;

    public ReportsController(OpenedDepositService openedDepositService) {
        this.openedDepositService = openedDepositService;
    }

    @GetMapping(value = "/depositsRating")
    @PreAuthorize("hasAuthority('Admin')")
    public String rating(Model model) {
        model.addAttribute("rating", openedDepositService.createRating());
        model.addAttribute("portfolios", openedDepositService.calculateDepositPortfolios());
        return "report-views/reports";
    }
}
