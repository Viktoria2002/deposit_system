package com.example.deposit_system.services.opened_deposits;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.repositories.opened_deposits.OpenedTermDepositRepository;
import com.example.deposit_system.services.CardService;
import com.example.deposit_system.services.credentials.ClientService;
import com.example.deposit_system.services.deposits.TermDepositService;
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
class OpenedTermDepositServiceImplTest {
    @InjectMocks
    private OpenedTermDepositServiceImpl openedTermDepositService;
    @Mock
    private OpenedTermDepositRepository openedTermDepositRepository;
    @Mock
    private TermDepositService termDepositService;
    @Mock
    private ClientService clientService;
    @Mock
    private CardService cardService;

    @Test
    void testLoadOpenedTermDepositById() {
        OpenedTermDeposit openedTermDeposit = new OpenedTermDeposit();
        openedTermDeposit.setId(1L);
        when(openedTermDepositRepository.findById(any())).thenReturn(Optional.of(openedTermDeposit));
        OpenedTermDeposit actualOpenedTermDeposit = openedTermDepositService.loadOpenedTermDepositById(1L);
        assertEquals(openedTermDeposit, actualOpenedTermDeposit);
    }

    @Test
    void testCreateOpenedTermDeposit() {
        TermDeposit termDeposit = new TermDeposit();
        termDeposit.setDepositId(1L);
        when(termDepositService.loadTermDepositById(any())).thenReturn(termDeposit);
        Client client = new Client();
        client.setClientId(1L);
        when(clientService.loadClientById(any())).thenReturn(client);
        Card card = new Card();
        card.setCardId(1L);
        when(cardService.loadCardById(any())).thenReturn(card);
        openedTermDepositService.createOpenedTermDeposit(1000, LocalDate.of(2023, 03,11), card.getCardId(), termDeposit.getDepositId(), client.getClientId());
        verify(openedTermDepositRepository, times(1)).save(any());
    }

    @Test
    void testCreateOrUpdateOpenedTermDeposit() {
        OpenedTermDeposit openedTermDeposit = new OpenedTermDeposit();
        openedTermDeposit.setId(1L);
        openedTermDepositService.createOrUpdateOpenedTermDeposit(openedTermDeposit);
        verify(openedTermDepositRepository).save(openedTermDeposit);
    }

    @Test
    void testGetAllOpenedTermDeposits() {
        openedTermDepositService.getAllOpenedTermDeposits();
        verify(openedTermDepositRepository, times(1)).findAll();
    }

    @Test
    void testGetOpenedTermDepositsOfClient() {
        openedTermDepositService.getOpenedTermDepositsOfClient(1L);
        verify(openedTermDepositRepository, times(1)).findOpenedTermDepositsByClientClientId(1L);
    }
}