package com.example.deposit_system.runner;

import com.example.deposit_system.entity.Bank;
import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.*;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.entity.statement.TermDepositOperationInfo;
import com.example.deposit_system.services.BankService;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.*;
import com.example.deposit_system.services.deposits.DemandDepositService;
import com.example.deposit_system.services.deposits.TermDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedDemandDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.DemandDepositOperationService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;

@Component
public class MyRunner implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private PassportService passportDataService;
    @Autowired
    private DemandDepositService demandDepositService;
    @Autowired
    private TermDepositService termDepositService;
    @Autowired
    private OpenedDemandDepositService openedDemandDepositService;
    @Autowired
    private OpenedTermDepositService openedTermDepositService;
    @Autowired
    private DemandDepositOperationService demandDepositOperationService;
    @Autowired
    private TermDepositOperationService termDepositOperationService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CardService cardService;

    public static final String ADMIN = "Admin";
    public static final String CLIENT = "Client";

    @Override
    public void run(String... args) {
        Role adminRole = roleService.createRole(ADMIN);
        Role clientRole = roleService.createRole(CLIENT);

        Bank bank1 = bankService.createOrUpdateBank(Bank.builder().logo("/logos/alphabank.png").bankName("Альфа-Банк").address("г. Минск, ул. Московская, 14").a1Number("+375(44)309-15-15").mtsNumber("+375(29)309-15-15").lifeNumber("+375(25)309-15-15").build());
        Bank bank2 = bankService.createOrUpdateBank(Bank.builder().logo("/logos/belarusbank.png").bankName("Беларусбанк").address("г. Минск, ул. Мясникова, 14").a1Number("+375(44)215-61-15").mtsNumber("+375(29)215-61-15").lifeNumber("+375(25)215-61-15").build());
        Bank bank3 = bankService.createOrUpdateBank(Bank.builder().logo("/logos/belagroprombank.png").bankName("Белагропромбанк").address("г. Минск, ул. Филимонова, 22-130").a1Number("+375(44)733-33-32").mtsNumber("+375(29)733-33-32").lifeNumber("+375(25)733-33-32").build());
        Bank bank4 = bankService.createOrUpdateBank(Bank.builder().logo("/logos/belinvestbank.png").bankName("Белинвестбанк").address("г. Минск, просп. Независимости, 20").a1Number("+375(44)309-73-09").mtsNumber("+375(29)309-73-09").lifeNumber("+375(25)309-73-09").build());
        Bank bank5 = bankService.createOrUpdateBank(Bank.builder().logo("/logos/belgasprombank.png").bankName("Белгазпромбанк").address("г. Минск, ул. Платонова, 1Б").a1Number("+375(44)218-84-31").mtsNumber("+375(29)218-84-31").lifeNumber("+375(25)218-84-31").build());

        Passport passportData1 = passportDataService.createPassport("KH3456789", "Гродненский РОВД", LocalDate.of(2019, 11, 11), LocalDate.of(2029, 11, 12));
        Passport passportData2 = passportDataService.createPassport("MK3456888", "Минский РОВД", LocalDate.of(2020, 9, 5), LocalDate.of(2030, 9, 5));
        Passport passportData3 = passportDataService.createPassport("KH4583456", "Гродненский РОВД", LocalDate.of(2020, 11, 9), LocalDate.of(2030, 11, 9));
        Passport passportData4 = passportDataService.createPassport("AM3456888", "Гомельский РОВД", LocalDate.of(2018, 8, 12), LocalDate.of(2028, 8, 12));
        Passport passportData5 = passportDataService.createPassport("PH3456888", "Витебский РОВД", LocalDate.of(2015, 11, 5), LocalDate.of(2025, 11, 5));
        Passport passportData6 = passportDataService.createPassport("PH3671188", "Витебский РОВД", LocalDate.of(2017, 7, 15), LocalDate.of(2027, 7, 15));

        Client admin = clientService.createClient("Петров", "Антон", "Николаевич", "Мужской", LocalDate.of(2000, 11, 19), "+375(44)753-46-30", passportData6.getId(), "admin@gmail.com", "Pass1*");
        Client client1 = clientService.createClient("Шилова", "Наталья", "Георгиевна", "Женский", LocalDate.of(2000, 11, 3), "+375(44)724-11-11", passportData1.getId(), "natalia@gmail.com", "Pass1*");
        Client client2 = clientService.createClient("Зайцева", "Елизавета", "Игоревна", "Женский", LocalDate.of(1990, 11, 4), "+375(44)222-35-22", passportData2.getId(), "liza@gmail.com", "Pass2*");
        Client client3 = clientService.createClient("Смирнов", "Андрей", "Петрович", "Мужской", LocalDate.of(1989, 10, 11), "+375(44)456-22-40", passportData3.getId(), "andrey@gmail.com", "Pass3*");
        Client client4 = clientService.createClient("Устинова", "Анастасия", "Сергеевна", "Женский", LocalDate.of(1977, 5, 5), "+375(44)789-22-11", passportData4.getId(), "nastya@gmail.com", "Pass4*");
        Client client5 = clientService.createClient("Жданова", "Екатерина", "Михайловна", "Женский", LocalDate.of(1979, 7, 28), "+375(44)367-23-22", passportData5.getId(), "kate@gmail.com", "Pass5*");

        User user = admin.getUser();
        user.getRoles().clear();
        userService.createOrUpdateUser(user);
        userService.assignRoleToUser(admin.getUser().getEmail(), ADMIN);

        Card card1 = cardService.createCard("/payment_systems/Visa.png", 3456234589674534L, 11, 2025, 111, 3000, client1.getClientId());
        Card card2 = cardService.createCard("/payment_systems/Mastercard.png", 7834985345923456L, 9, 2024, 222, 4000, client1.getClientId());
        Card card3 = cardService.createCard("/payment_systems/Visa.png", 2345789034564567L, 9, 2023, 333, 4000, client2.getClientId());
        Card card4 = cardService.createCard("/payment_systems/Mastercard.png", 1234345678904567L, 7, 2024, 444, 4000, client3.getClientId());
        Card card5 = cardService.createCard("/payment_systems/Mastercard.png", 4567234589053456L, 9, 2024, 555, 4000, client4.getClientId());
        Card card6 = cardService.createCard("/payment_systems/Visa.png", 3478456723451234L, 10, 2023, 666, 4000, client5.getClientId());

        DemandDeposit bank1_demandDeposit1 = demandDepositService.createDemandDeposit("Урожайный", 2, 2000, "USD", true, true, true, true, bank1.getBankId());
        DemandDeposit bank1_demandDeposit2 = demandDepositService.createDemandDeposit("Летний бриз", 3, 1000, "BYN", true, true, false, true, bank1.getBankId());

        DemandDeposit bank2_demandDeposit1 = demandDepositService.createDemandDeposit("Максимум", 4, 1000, "USD", true, true, false, true, bank2.getBankId());
        DemandDeposit bank2_demandDeposit2 = demandDepositService.createDemandDeposit("Икс", 2, 2000, "EUR", false, true, true, true, bank2.getBankId());

        DemandDeposit bank3_demandDeposit1 = demandDepositService.createDemandDeposit("Зигзаг", 3, 1000, "BYN", true, true, false, true, bank3.getBankId());
        DemandDeposit bank3_demandDeposit2 = demandDepositService.createDemandDeposit("Белки", 4, 1000, "USD", true, true, false, true, bank3.getBankId());

        DemandDeposit bank4_demandDeposit1 = demandDepositService.createDemandDeposit("Личный выбор", 3, 1000, "BYN", true, true, false, true, bank4.getBankId());
        DemandDeposit bank4_demandDeposit2 = demandDepositService.createDemandDeposit("Щедрый", 4, 1000, "USD", true, true, false, true, bank4.getBankId());

        DemandDeposit bank5_demandDeposit1 = demandDepositService.createDemandDeposit("Выгода", 5, 1000, "BYN", true, true, false, true, bank5.getBankId());
        DemandDeposit bank5_demandDeposit2 = demandDepositService.createDemandDeposit("Детский", 4, 1000, "USD", true, true, false, true, bank5.getBankId());

        TermDeposit bank1_termDeposit1 = termDepositService.createTermDeposit("Весна", 16, 2000, "USD", 24, true, true, true, true, bank1.getBankId());
        TermDeposit bank1_termDeposit2 = termDepositService.createTermDeposit("Альтернативный", 10, 1000, "USD", 10, true, false, false, true, bank1.getBankId());

        TermDeposit bank2_termDeposit1 = termDepositService.createTermDeposit("СуперСемь", 12, 2000, "USD", 18, true, true, false, true, bank2.getBankId());
        TermDeposit bank2_termDeposit2 = termDepositService.createTermDeposit("Почтенный", 11, 1000, "BYN", 12, true, false, false, false, bank2.getBankId());

        TermDeposit bank3_termDeposit1 = termDepositService.createTermDeposit("СуперСемь", 13, 2000, "USD", 18, true, true, false, true, bank3.getBankId());
        TermDeposit bank4_termDeposit2 = termDepositService.createTermDeposit("Почтенный", 11, 1000, "BYN", 12, true, false, false, false, bank4.getBankId());

        OpenedDemandDeposit openedDemandDeposit1 = openedDemandDepositService.createOpenedDemandDeposit(2100, LocalDate.of(2023, 01, 28), bank1_demandDeposit1.getDepositId(), card1.getCardId(), client1.getClientId());
        OpenedDemandDeposit openedDemandDeposit2 = openedDemandDepositService.createOpenedDemandDeposit(2000, LocalDate.of(2022, 10, 10), bank2_demandDeposit1.getDepositId(), card2.getCardId(), client1.getClientId());
        OpenedDemandDeposit openedDemandDeposit3 = openedDemandDepositService.createOpenedDemandDeposit(2300, LocalDate.of(2023, 04, 28), bank1_demandDeposit2.getDepositId(), card3.getCardId(), client2.getClientId());

        OpenedDemandDeposit openedDemandDeposit4 = openedDemandDepositService.createOpenedDemandDeposit(2100, LocalDate.of(2023, 5, 8), bank1_demandDeposit1.getDepositId(), card4.getCardId(), client3.getClientId());
        OpenedDemandDeposit openedDemandDeposit5 = openedDemandDepositService.createOpenedDemandDeposit(2000, LocalDate.of(2023, 5, 7), bank3_demandDeposit1.getDepositId(), card5.getCardId(), client4.getClientId());
        OpenedDemandDeposit openedDemandDeposit6 = openedDemandDepositService.createOpenedDemandDeposit(2300, LocalDate.of(2023, 5, 7), bank4_demandDeposit2.getDepositId(), card6.getCardId(), client5.getClientId());
        OpenedDemandDeposit openedDemandDeposit7 = openedDemandDepositService.createOpenedDemandDeposit(2000, LocalDate.of(2023, 5, 3), bank4_demandDeposit2.getDepositId(), card6.getCardId(), client5.getClientId());

        OpenedTermDeposit openedTermDeposit1 = openedTermDepositService.createOpenedTermDeposit(2700, LocalDate.of(2022, 7, 03), bank1_termDeposit2.getDepositId(), card1.getCardId(), client1.getClientId());
        OpenedTermDeposit openedTermDeposit2 = openedTermDepositService.createOpenedTermDeposit(2300, LocalDate.of(2022, 7, 4), bank3_termDeposit1.getDepositId(), card2.getCardId(), client1.getClientId());

        OpenedTermDeposit openedTermDeposit3 = openedTermDepositService.createOpenedTermDeposit(2700, LocalDate.of(2023, 5, 6), bank1_termDeposit2.getDepositId(), card3.getCardId(), client2.getClientId());
        OpenedTermDeposit openedTermDeposit4 = openedTermDepositService.createOpenedTermDeposit(2300, LocalDate.of(2023, 5, 6), bank3_termDeposit1.getDepositId(), card3.getCardId(), client2.getClientId());
        OpenedTermDeposit openedTermDeposit5 = openedTermDepositService.createOpenedTermDeposit(2600, LocalDate.of(2023, 5, 6), bank1_termDeposit2.getDepositId(), card4.getCardId(), client3.getClientId());
        OpenedTermDeposit openedTermDeposit6 = openedTermDepositService.createOpenedTermDeposit(2300, LocalDate.of(2023, 5, 5), bank3_termDeposit1.getDepositId(), card5.getCardId(), client4.getClientId());
        OpenedTermDeposit openedTermDeposit7 = openedTermDepositService.createOpenedTermDeposit(2300, LocalDate.of(2023, 5, 2), bank4_termDeposit2.getDepositId(), card6.getCardId(), client5.getClientId());

        DemandDepositOperationInfo demandDepositOperation1 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,01,28), OperationType.OPENING, 2100, 2100, openedDemandDeposit1.getId());
        DemandDepositOperationInfo demandDepositOperation2 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2022,10,10), OperationType.OPENING, 2000, 2000, openedDemandDeposit2.getId());
        DemandDepositOperationInfo demandDepositOperation3 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,04,28), OperationType.OPENING, 2300, 2300, openedDemandDeposit3.getId());
        DemandDepositOperationInfo demandDepositOperation4 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,5,8), OperationType.OPENING, 2100, 2100, openedDemandDeposit4.getId());
        DemandDepositOperationInfo demandDepositOperation5 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,5,7), OperationType.OPENING, 2000, 2000, openedDemandDeposit5.getId());
        DemandDepositOperationInfo demandDepositOperation6 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,5,7), OperationType.OPENING, 2300, 2300, openedDemandDeposit6.getId());
        DemandDepositOperationInfo demandDepositOperation7 = demandDepositOperationService.createDemandDepositOperation(LocalDate.of(2023,5,3), OperationType.OPENING, 2000, 2000, openedDemandDeposit7.getId());

        TermDepositOperationInfo termDepositOperation1 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2022, 7, 03), OperationType.OPENING, 1200, 1200, openedTermDeposit1.getId());
        TermDepositOperationInfo termDepositOperation2 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2022, 7, 4), OperationType.OPENING, 2300, 2300, openedTermDeposit2.getId());
        TermDepositOperationInfo termDepositOperation3 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2023, 5, 6), OperationType.OPENING, 1200, 1200, openedTermDeposit3.getId());
        TermDepositOperationInfo termDepositOperation4 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2023, 5, 6), OperationType.OPENING, 2300, 2300, openedTermDeposit4.getId());
        TermDepositOperationInfo termDepositOperation5 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2023, 5, 6), OperationType.OPENING, 1200, 1200, openedTermDeposit5.getId());
        TermDepositOperationInfo termDepositOperation6 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2023, 5, 5), OperationType.OPENING, 2300, 2300, openedTermDeposit6.getId());
        TermDepositOperationInfo termDepositOperation7 = termDepositOperationService.createTermDepositOperation(LocalDate.of(2023, 5, 2), OperationType.OPENING, 1200, 1200, openedTermDeposit7.getId());

        List<OpenedTermDeposit> openedTermDeposits = openedTermDepositService.getAllOpenedTermDeposits();
        for (OpenedTermDeposit deposit : openedTermDeposits) {
            TemporalAdjuster lastDayOfMonth = TemporalAdjusters.lastDayOfMonth();
            int capitalizationMonths = Period.between(deposit.getOpeningDate(), LocalDate.now()).getMonths();
            if (LocalDate.now().with(lastDayOfMonth).isEqual(LocalDate.now())) capitalizationMonths += 1;
            for (int i = 0; i < capitalizationMonths; i++) {
                LocalDate dateOfOperation = deposit.getOpeningDate().plusMonths(i).with(lastDayOfMonth);
                if (deposit.getTermDeposit().isHasCapitalization()) {
                    double capitalization = Precision.round(deposit.getAmount() * deposit.getTermDeposit().getInterestRate() / 36500 * 30, 2);
                    TermDepositOperationInfo operationInfo = new TermDepositOperationInfo(dateOfOperation, OperationType.CAPITALIZATION, capitalization,
                            Precision.round(capitalization + deposit.getAmount(), 2), deposit);
                    int flag = 1;
                    for (TermDepositOperationInfo operation : termDepositOperationService.getTermDepositOperationsOfOpenedTermDeposit(deposit.getId())) {
                        if (operation.getType().equals(OperationType.CAPITALIZATION) && operationInfo.getDateOfOperation().isEqual(operation.getDateOfOperation()))  flag = 0;
                    }
                    if (flag == 1) {
                        termDepositOperationService.createTermDepositOperation(dateOfOperation, OperationType.CAPITALIZATION,
                                capitalization, Precision.round(capitalization + deposit.getAmount(), 2), deposit.getId());
                        deposit.setAmount(Precision.round(capitalization + deposit.getAmount(), 2));
                    }
                    openedTermDepositService.createOrUpdateOpenedTermDeposit(deposit);
                } else {
                    int days = deposit.getTermDeposit().getTerm() * 30;
                    double simplePercentages = Precision.round(deposit.getTermDepositOperations().get(0).getAmount()  * deposit.getTermDeposit().getInterestRate() / 36500 * 30, 2);
                    TermDepositOperationInfo operationInfo = new TermDepositOperationInfo(dateOfOperation, OperationType.SIMPLE_PERCENTAGES,
                            simplePercentages, Precision.round(simplePercentages + deposit.getAmount(), 2), deposit);
                    int flag = 1;
                    for (TermDepositOperationInfo operation : termDepositOperationService.getTermDepositOperationsOfOpenedTermDeposit(deposit.getId())) {
                        if (operation.getType().equals(OperationType.SIMPLE_PERCENTAGES) && operationInfo.getDateOfOperation().isEqual(operation.getDateOfOperation()))  flag = 0;
                    }
                    if (flag == 1) {
                        termDepositOperationService.createTermDepositOperation(dateOfOperation,
                                OperationType.SIMPLE_PERCENTAGES, simplePercentages, Precision.round(deposit.getAmount() + simplePercentages, 2), deposit.getId());
                        deposit.setAmount(Precision.round(simplePercentages + deposit.getAmount(),2));
                    }
                    openedTermDepositService.createOrUpdateOpenedTermDeposit(deposit);
                }
            }
        }


        List<OpenedDemandDeposit> openedDemandDeposits = openedDemandDepositService.getAllOpenedDemandDeposits();
        for (OpenedDemandDeposit deposit : openedDemandDeposits) {
            TemporalAdjuster lastDayOfMonth = TemporalAdjusters.lastDayOfMonth();
            int capitalizationMonths = Period.between(deposit.getOpeningDate(), LocalDate.now()).getMonths();
            if (LocalDate.now().with(lastDayOfMonth).isEqual(LocalDate.now())) capitalizationMonths += 1;
            for (int i = 0; i < capitalizationMonths; i++) {
                LocalDate dateOfOperation = deposit.getOpeningDate().plusMonths(i).with(lastDayOfMonth);
                if (deposit.getDemandDeposit().isHasCapitalization()) {
                    double capitalization = Precision.round(deposit.getAmount() * deposit.getDemandDeposit().getInterestRate() / 36500 * 30, 2);
                    DemandDepositOperationInfo operationInfo = new DemandDepositOperationInfo(dateOfOperation, OperationType.CAPITALIZATION,
                            capitalization, Precision.round(capitalization + deposit.getAmount(), 2), deposit);
                    int flag = 1;
                    for (DemandDepositOperationInfo operation : demandDepositOperationService.getDemandDepositOperationsOfOpenedDemandDeposit(deposit.getId())) {
                        if (operation.getType().equals(OperationType.CAPITALIZATION) && operationInfo.getDateOfOperation().isEqual(operation.getDateOfOperation()))  flag = 0;
                    }
                    if (flag == 1) {
                        demandDepositOperationService.createDemandDepositOperation(dateOfOperation, OperationType.CAPITALIZATION,
                                capitalization, Precision.round(capitalization + deposit.getAmount(), 2), deposit.getId());
                        deposit.setAmount(Precision.round(capitalization + deposit.getAmount(), 2));
                    }
                    openedDemandDepositService.createOrUpdateOpenedDemandDeposit(deposit);
                } else {
                    double simplePercentages = Precision.round(deposit.getDemandDepositOperations().get(0).getAmount()  * deposit.getDemandDeposit().getInterestRate() / 36500 * 30, 2);
                    DemandDepositOperationInfo operationInfo = new DemandDepositOperationInfo(dateOfOperation, OperationType.SIMPLE_PERCENTAGES,
                            simplePercentages, Precision.round(simplePercentages + deposit.getAmount(), 2), deposit);
                    int flag = 1;
                    for (DemandDepositOperationInfo operation : demandDepositOperationService.getDemandDepositOperationsOfOpenedDemandDeposit(deposit.getId())) {
                        if (operation.getType().equals(OperationType.SIMPLE_PERCENTAGES) && operationInfo.getDateOfOperation().isEqual(operation.getDateOfOperation()))  flag = 0;
                    }
                    if (flag == 1) {
                        demandDepositOperationService.createDemandDepositOperation(dateOfOperation,
                                OperationType.SIMPLE_PERCENTAGES, simplePercentages, Precision.round(deposit.getAmount() + simplePercentages, 2), deposit.getId());
                        deposit.setAmount(Precision.round(simplePercentages + deposit.getAmount(), 2));
                    }
                    openedDemandDepositService.createOrUpdateOpenedDemandDeposit(deposit);
                }
            }
        }
    }
}
