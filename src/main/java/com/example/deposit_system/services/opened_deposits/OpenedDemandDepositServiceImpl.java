package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.repositories.credentials.ClientRepository;
import com.example.deposit_system.repositories.deposits.DemandDepositRepository;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.deposits.DemandDepositService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OpenedDemandDepositServiceImpl implements OpenedDemandDepositService {
    private OpenedDemandDepositRepository openedDemandDepositRepository;
    private DemandDepositService demandDepositService;
    private ClientService clientService;
    private CardService cardService;

    public OpenedDemandDepositServiceImpl(OpenedDemandDepositRepository openedDemandDepositRepository, DemandDepositRepository demandDepositRepository, ClientRepository clientRepository, DemandDepositService demandDepositService, ClientService clientService, CardService cardService) {
        this.openedDemandDepositRepository = openedDemandDepositRepository;
        this.demandDepositService = demandDepositService;
        this.clientService = clientService;
        this.cardService = cardService;
    }

    @Override
    public OpenedDemandDeposit loadOpenedDemandDepositById(Long id) {
        return openedDemandDepositRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Opened demand deposit with id " + id + " Not Found"));
    }

    @Override
    public OpenedDemandDeposit createOpenedDemandDeposit(double amount, LocalDate openingDate, Long demandDepositId, Long cardId, Long clientId) {
        DemandDeposit demandDeposit = demandDepositService.loadDemandDepositById(demandDepositId);
        Card card = cardService.loadCardById(cardId);
        Client client = clientService.loadClientById(clientId);
        return openedDemandDepositRepository.save(new OpenedDemandDeposit(amount, openingDate, card, demandDeposit, client));
    }

    @Override
    public OpenedDemandDeposit createOrUpdateOpenedDemandDeposit(OpenedDemandDeposit openedDemandDeposit) {
        openedDemandDeposit.setStatus("Открыт");
        if (openedDemandDeposit.getId() == null) {
            openedDemandDeposit.setOpeningDate(LocalDate.now());
            if (openedDemandDeposit.getCard() != null) {
                Card card = cardService.loadCardById(openedDemandDeposit.getCard().getCardId());
                card.setCardBalance(card.getCardBalance() - openedDemandDeposit.getAmount());
            }
        }
        return openedDemandDepositRepository.save(openedDemandDeposit);
    }

    @Override
    public List<OpenedDemandDeposit> getAllOpenedDemandDeposits() {
        return openedDemandDepositRepository.findAll();
    }

    @Override
    public List<OpenedDemandDeposit> getOpenedDemandDepositsOfClient(Long clientId) {
        return openedDemandDepositRepository.findOpenedDemandDepositsByClientClientId(clientId);
    }

    @Override
    public List<OpenedDemandDeposit> findOpenedDemandDepositsByDepositName(String depositName) {
        return openedDemandDepositRepository.findOpenedDemandDepositsByDemandDepositDepositName(depositName);
    }

    @Override
    public List<OpenedDemandDeposit> getOpenedDemandDepositsOfClientWithOpenStatus(Long clientId) {
        List<OpenedDemandDeposit> demandDeposits = getOpenedDemandDepositsOfClient(clientId);
        List<OpenedDemandDeposit> openedDemandDeposits = new ArrayList<>();
        if(demandDeposits.size() > 0) {
            for (OpenedDemandDeposit deposit : demandDeposits) {
                if (deposit.getStatus().equals("Открыт")) openedDemandDeposits.add(deposit);
            }
        }
        return openedDemandDeposits;
    }

    @Override
    public List<OpenedDemandDeposit> getOpenedDemandDepositsOfClientWithCloseStatus(Long clientId) {
        List<OpenedDemandDeposit> demandDeposits = getOpenedDemandDepositsOfClient(clientId);
        List<OpenedDemandDeposit> openedDemandDeposits = new ArrayList<>();
        if(demandDeposits.size() > 0) {
            for (OpenedDemandDeposit deposit : demandDeposits) {
                if (deposit.getStatus().equals("Закрыт")) openedDemandDeposits.add(deposit);
            }
        }
        return openedDemandDeposits;
    }

    @Override
    public List<OpenedDemandDeposit> findOpenedDemandDepositsByOpeningDate(LocalDate date) {
        return openedDemandDepositRepository.findOpenedDemandDepositsByOpeningDate(date);
    }
}
