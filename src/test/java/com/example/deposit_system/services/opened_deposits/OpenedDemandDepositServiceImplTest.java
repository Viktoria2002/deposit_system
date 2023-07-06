package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.deposits.DemandDepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpenedDemandDepositServiceImplTest {
    @InjectMocks
    private OpenedDemandDepositServiceImpl openedDemandDepositService;
    @Mock
    private OpenedDemandDepositRepository openedDemandDepositRepository;
    @Mock
    private DemandDepositService demandDepositService;
    @Mock
    private ClientService clientService;
    @Mock
    private CardService cardService;

    @Test
    void testLoadOpenedDemandDepositById() {
        OpenedDemandDeposit openedDemandDeposit = new OpenedDemandDeposit();
        openedDemandDeposit.setId(1L);
        when(openedDemandDepositRepository.findById(any())).thenReturn(Optional.of(openedDemandDeposit));
        OpenedDemandDeposit actualOpenedDemandDeposit = openedDemandDepositService.loadOpenedDemandDepositById(1L);
        assertEquals(openedDemandDeposit, actualOpenedDemandDeposit);
    }

    @Test
    void testCreateOpenedDemandDeposit() {
        DemandDeposit demandDeposit = new DemandDeposit();
        demandDeposit.setDepositId(1L);
        when(demandDepositService.loadDemandDepositById(any())).thenReturn(demandDeposit);
        Client client = new Client();
        client.setClientId(1L);
        when(clientService.loadClientById(any())).thenReturn(client);
        Card card = new Card();
        card.setCardId(1L);
        when(cardService.loadCardById(any())).thenReturn(card);
        openedDemandDepositService.createOpenedDemandDeposit(1000, LocalDate.of(2023, 03,11), card.getCardId(), demandDeposit.getDepositId(), client.getClientId());
        verify(openedDemandDepositRepository, times(1)).save(any());
    }

    @Test
    void testCreateOrUpdateOpenedDemandDeposit() {
        OpenedDemandDeposit openedDemandDeposit = new OpenedDemandDeposit();
        openedDemandDeposit.setId(1L);
        openedDemandDepositService.createOrUpdateOpenedDemandDeposit(openedDemandDeposit);
        verify(openedDemandDepositRepository).save(openedDemandDeposit);
    }

    @Test
    void getAllOpenedDemandDeposits() {
        openedDemandDepositService.getAllOpenedDemandDeposits();
        verify(openedDemandDepositRepository, times(1)).findAll();
    }

    @Test
    void getOpenedDemandDepositsOfClient() {
        openedDemandDepositService.getOpenedDemandDepositsOfClient(1L);
        verify(openedDemandDepositRepository, times(1)).findOpenedDemandDepositsByClientClientId(1L);
    }
}