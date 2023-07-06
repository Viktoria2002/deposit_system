package com.example.deposit_system.services.opened_deposits;

public class ReportEntry {
    private String bankName;
    private double depositAmount;

    public ReportEntry(String name, double number) {
        this.bankName = name;
        this.depositAmount = number;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }
}
