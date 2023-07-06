package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class OpenedDepositServiceImpl implements OpenedDepositService{
    private OpenedDemandDepositService openedDemandDepositService;
    private OpenedTermDepositService openedTermDepositService;

    public OpenedDepositServiceImpl(OpenedDemandDepositService openedDemandDepositService, OpenedTermDepositService openedTermDepositService) {
        this.openedDemandDepositService = openedDemandDepositService;
        this.openedTermDepositService = openedTermDepositService;
    }

    @Override
    public Map<String, Long> createRating() {
        List<OpenedDemandDeposit> demandDepositList = openedDemandDepositService.getAllOpenedDemandDeposits();
        List<OpenedTermDeposit> termDepositList = openedTermDepositService.getAllOpenedTermDeposits();
        List list = new ArrayList();
        for(OpenedDemandDeposit deposit : demandDepositList) list.add(deposit.getDemandDeposit().getDepositName());
        for(OpenedTermDeposit deposit : termDepositList) list.add(deposit.getTermDeposit().getDepositName());
        Map<String, Long> unsortedMap = (Map<String, Long>) list.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<String, Long> reportEntryList = unsortedMap.entrySet().stream().sorted(Comparator.comparingLong(e -> -e.getValue()))
                .collect(Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue, (a, b) -> { throw new AssertionError(); }, LinkedHashMap::new ));
        return reportEntryList;
    }

    @Override
    public List<ReportEntry> calculateDepositPortfolios() {
        List<ReportEntry> portfolios = new ArrayList<>();
        List<OpenedDemandDeposit> demandDepositList = openedDemandDepositService.getAllOpenedDemandDeposits();
        List<OpenedTermDeposit> termDepositList = openedTermDepositService.getAllOpenedTermDeposits();
        for(OpenedDemandDeposit deposit : demandDepositList) {
            if(deposit.getDemandDeposit().getCurrency().equals("USD")) {
                portfolios.add(new ReportEntry(deposit.getDemandDeposit().getBank().getBankName(), Precision.round(deposit.getAmount() * 2.9,2)));
            } else if(deposit.getDemandDeposit().getCurrency().equals("EUR")) {
                portfolios.add(new ReportEntry(deposit.getDemandDeposit().getBank().getBankName(), Precision.round(deposit.getAmount() * 3.2,2)));
            } else if(deposit.getDemandDeposit().getCurrency().equals("BYN")) {
                portfolios.add(new ReportEntry(deposit.getDemandDeposit().getBank().getBankName(), Precision.round(deposit.getAmount(),2)));
            }
        }
        for(OpenedTermDeposit deposit : termDepositList) {
            if(deposit.getTermDeposit().getCurrency().equals("USD")) {
                portfolios.add(new ReportEntry(deposit.getTermDeposit().getBank().getBankName(), Precision.round(deposit.getAmount() * 2.9,2)));
            } else if(deposit.getTermDeposit().getCurrency().equals("EUR")) {
                portfolios.add(new ReportEntry(deposit.getTermDeposit().getBank().getBankName(), Precision.round(deposit.getAmount() * 3.2,2)));
            } else if(deposit.getTermDeposit().getCurrency().equals("BYN")) {
                portfolios.add(new ReportEntry(deposit.getTermDeposit().getBank().getBankName(), Precision.round(deposit.getAmount(),2)));
            }
        }
        List<ReportEntry> resultList = portfolios.stream().collect(Collectors.groupingBy(ReportEntry::getBankName,
                        Collectors.summingDouble(ReportEntry::getDepositAmount))).entrySet().stream()
                .map(e -> new ReportEntry(e.getKey(), e.getValue())).collect(Collectors.toList())
                .stream().collect(Collectors.toList());
        Collections.sort(resultList, Comparator.comparing(ReportEntry::getDepositAmount).reversed());
        for(ReportEntry entry : resultList) {
            entry.setDepositAmount(Precision.round(entry.getDepositAmount(), 2));
        }
        return resultList;
    }

    @Override
    public Map<String, Integer> formGraphValues() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        Map<String, Integer> map = new TreeMap<>();
        for (int i = 6; i >= 0; i--) {
            LocalDate currentDay = LocalDate.now().minusDays(i);
            List<OpenedDemandDeposit> openedDemandDeposits = openedDemandDepositService.findOpenedDemandDepositsByOpeningDate(currentDay);
            List<OpenedTermDeposit> openedTermDeposits = openedTermDepositService.findOpenedTermDepositsByOpeningDate(currentDay);
            map.put(currentDay.format(formatter), openedDemandDeposits.size() + openedTermDeposits.size());
        }
        return map;
    }

    @Override
    public List<ReportEntry> formDiagramValues() {
        List<ReportEntry> portfolios = calculateDepositPortfolios();
        double sum = 0;
        for (ReportEntry entry : portfolios) {
            sum += entry.getDepositAmount();
        }
        for (ReportEntry entry : portfolios) {
            entry.setDepositAmount(Precision.round(entry.getDepositAmount() * 100 / sum, 2));
        }
        return portfolios;
    }
}
