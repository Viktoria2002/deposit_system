package com.example.deposit_system.web;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.credentials.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.deposit_system.constants.Constants.LIST_CARDS;

@RestController
@RequestMapping(value = "/cards")
public class CardController {
    private CardService cardService;
    private UserService userService;
    private ClientService clientService;

    public CardController(CardService cardService, UserService userService, ClientService clientService) {
        this.cardService = cardService;
        this.userService = userService;
        this.clientService = clientService;
    }
    @GetMapping(value = "/index")
    @PreAuthorize("hasAuthority('Client')")
    public List<Card> cardsForCurrentClient(Model model, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        model.addAttribute(LIST_CARDS, client.getCards());
        return client.getCards();
    }

    @GetMapping(value = "/delete/{cardId}")
    @PreAuthorize("hasAuthority('Client')")
    public void deleteCard(@PathVariable Long cardId) {
        Card card = cardService.loadCardById(cardId);
        for(OpenedDemandDeposit deposit : card.getDemandDeposits()) deposit.setCard(null);
        for(OpenedTermDeposit deposit : card.getTermDeposits()) deposit.setCard(null);
        cardService.removeCard(cardId);
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('Client')")
    public Card save(@RequestBody Card card, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        Client client = clientService.loadClientById(user.getClient().getClientId());
        card.setClient(client);
        Card existCard = cardService.loadCardByCardNumber(card.getNumber());
        if(existCard == null) return cardService.createOrUpdateCard(card);
        else return null;
    }
    @GetMapping(value = "/withdrawal/{cardId}/{amount}")
    public void withdrawal(@PathVariable Long cardId, @PathVariable double amount) {
        Card card = cardService.loadCardById(cardId);
        card.setCardBalance(card.getCardBalance() - amount);
        cardService.createOrUpdateCard(card);
    }

    @GetMapping(value = "/replenishment/{cardId}/{amount}")
    public void replenishment(@PathVariable Long cardId, @PathVariable double amount) {
        Card card = cardService.loadCardById(cardId);
        card.setCardBalance(card.getCardBalance() + amount);
        cardService.createOrUpdateCard(card);
    }
}
