package com.example.deposit_system.services.opened_deposits;

import java.util.List;
import java.util.Map;

public interface OpenedDepositService {
    Map<String, Long> createRating();
    List<ReportEntry> calculateDepositPortfolios();
    Map<String, Integer> formGraphValues();
    List<ReportEntry> formDiagramValues();
}
