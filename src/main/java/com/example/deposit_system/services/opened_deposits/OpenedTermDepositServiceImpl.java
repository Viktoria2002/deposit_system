package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.repositories.opened_deposits.OpenedTermDepositRepository;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.deposits.TermDepositService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Transactional
public class OpenedTermDepositServiceImpl implements OpenedTermDepositService {
    private OpenedTermDepositRepository openedTermDepositRepository;
    private TermDepositService termDepositService;
    private ClientService clientService;
    private CardService cardService;

    public OpenedTermDepositServiceImpl(OpenedTermDepositRepository openedTermDepositRepository, TermDepositService termDepositService, ClientService clientService, CardService cardService) {
        this.openedTermDepositRepository = openedTermDepositRepository;
        this.termDepositService = termDepositService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    @Override
    public OpenedTermDeposit loadOpenedTermDepositById(Long id) {
        return openedTermDepositRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Opened term deposit with id " + id + " Not Found"));
    }

    @Override
    public OpenedTermDeposit createOpenedTermDeposit(double amount, LocalDate openingDate, Long termDepositId, Long cardId, Long clientId) {
        TermDeposit termDeposit = termDepositService.loadTermDepositById(termDepositId);
        Client client = clientService.loadClientById(clientId);
        Card card = cardService.loadCardById(cardId);
        return openedTermDepositRepository.save(new OpenedTermDeposit(amount, openingDate, card, termDeposit, client));
    }

    @Override
    public OpenedTermDeposit createOrUpdateOpenedTermDeposit(OpenedTermDeposit openedTermDeposit) {
        openedTermDeposit.setStatus("Открыт");
        if (openedTermDeposit.getId() == null) {
            openedTermDeposit.setOpeningDate(LocalDate.now());
            Card card = cardService.loadCardById(openedTermDeposit.getCard().getCardId());
            card.setCardBalance(card.getCardBalance() - openedTermDeposit.getAmount());
        }
        return openedTermDepositRepository.save(openedTermDeposit);
    }

    @Override
    public List<OpenedTermDeposit> getAllOpenedTermDeposits() {
        return openedTermDepositRepository.findAll();
    }

    @Override
    public List<OpenedTermDeposit> getOpenedTermDepositsOfClient(Long clientId) {
        return openedTermDepositRepository.findOpenedTermDepositsByClientClientId(clientId);
    }

    @Override
    public List<OpenedTermDeposit> findOpenedTermDepositsByDepositName(String depositName) {
        return openedTermDepositRepository.findOpenedTermDepositsByTermDepositDepositName(depositName);
    }

    @Override
    public List<OpenedTermDeposit> getOpenedTermDepositsOfClientWithOpenStatus(Long clientId) {
        List<OpenedTermDeposit> termDeposits = getOpenedTermDepositsOfClient(clientId);
        List<OpenedTermDeposit> openedTermDeposits = new ArrayList<>();
        for(OpenedTermDeposit deposit : termDeposits) {
            if(deposit.getStatus().equals("Открыт")) openedTermDeposits.add(deposit);
        }
        return openedTermDeposits;
    }

    @Override
    public List<OpenedTermDeposit> getOpenedTermDepositsOfClientWithCloseStatus(Long clientId) {
        List<OpenedTermDeposit> termDeposits = getOpenedTermDepositsOfClient(clientId);
        List<OpenedTermDeposit> openedTermDeposits = new ArrayList<>();
        for(OpenedTermDeposit deposit : termDeposits) {
            if(deposit.getStatus().equals("Закрыт")) openedTermDeposits.add(deposit);
        }
        return openedTermDeposits;
    }

    @Override
    public List<OpenedTermDeposit> findOpenedTermDepositsByOpeningDate(LocalDate date) {
        return openedTermDepositRepository.findOpenedTermDepositsByOpeningDate(date);
    }

    @Override
    public List<OpenedTermDeposit> getExpiredDepositsOfClient(Client client) {
        List<OpenedTermDeposit> deposits = client.getTermDeposits();
        List<OpenedTermDeposit> expiredDeposits = new CopyOnWriteArrayList<>();
        for(OpenedTermDeposit deposit : deposits) {
            if(deposit.getOpeningDate().plusMonths(deposit.getTermDeposit().getTerm()).isBefore(LocalDate.now())) {
                expiredDeposits.add(deposit);
            }
        }
        return expiredDeposits;
    }
}
