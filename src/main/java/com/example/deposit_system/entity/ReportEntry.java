package com.example.deposit_system.entity;

import lombok.Data;

@Data
public class ReportEntry {
    private String bankName;
    private double depositAmount;

    public ReportEntry(String name, double number) {
        this.bankName = name;
        this.depositAmount = number;
    }
}
