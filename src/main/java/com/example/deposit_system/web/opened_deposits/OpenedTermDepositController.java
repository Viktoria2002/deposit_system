package com.example.deposit_system.web.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import com.example.deposit_system.services.deposits.TermDepositService;
import com.example.deposit_system.services.opened_deposits.OpenedTermDepositService;
import com.example.deposit_system.services.statement.TermDepositOperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.deposit_system.constants.Constants.*;
import static com.example.deposit_system.constants.Constants.CURRENT_DATE;

@Controller
@RequestMapping(value = "/openedTermDeposits")
public class OpenedTermDepositController {
    private OpenedTermDepositService openedTermDepositService;
    private TermDepositService termDepositService;
    private TermDepositOperationService termDepositOperationService;
    private UserService userService;
    private ClientService clientService;
    private CardService cardService;

    public OpenedTermDepositController(OpenedTermDepositService openedTermDepositService, TermDepositService termDepositService, TermDepositOperationService termDepositOperationService, UserService userService, ClientService clientService, CardService cardService) {
        this.openedTermDepositService = openedTermDepositService;
        this.termDepositService = termDepositService;
        this.termDepositOperationService = termDepositOperationService;
        this.userService = userService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public String formTermDeposit(Model model, Long depositId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        List<Card> cards = client.getCards();
        List<Card> cardList = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            Card card = new Card();
            card.setCardId(cards.get(i).getCardId());
            card.setPaymentSystem(cards.get(i).getPaymentSystem().substring(17, cards.get(i).getPaymentSystem().length() - 4));
            card.setNumber(Integer.parseInt(String.valueOf(cards.get(i).getNumber()).substring(12)));
            card.setCardBalance(cards.get(i).getCardBalance());
            cardList.add(card);
        }
        TermDeposit deposit = termDepositService.loadTermDepositById(depositId);
        model.addAttribute(OPENED_TERM_DEPOSIT, new OpenedTermDeposit());
        model.addAttribute(LIST_CARDS, cardList);
        model.addAttribute(TERM_DEPOSIT, deposit);
        model.addAttribute(CURRENT_DATE, LocalDate.now());
        return "openedTermDeposit-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Client')")
    public String save(@Valid OpenedTermDeposit deposit, Principal principal) {
        deposit.setClient(clientService.loadClientByEmail(principal.getName()));
        deposit.setOpeningDate(LocalDate.now());
        deposit.setStatus("Открыт");
        openedTermDepositService.createOrUpdateOpenedTermDeposit(deposit);
        termDepositOperationService.createTermDepositOperation(LocalDate.now(), OperationType.OPENING, deposit.getAmount(), deposit.getAmount(), deposit.getId());
        return "redirect:/openedDeposits/open/client";
    }
}
