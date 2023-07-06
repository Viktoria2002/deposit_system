package com.example.deposit_system.services;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.repositories.CardRepository;
import com.example.deposit_system.repositories.credentials.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {
    @InjectMocks
    private CardServiceImpl cardService;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ClientRepository clientRepository;

    @Test
    void testLoadCardById() {
        Card card = new Card();
        card.setCardId(1L);
        when(cardRepository.findById(any())).thenReturn(Optional.of(card));
        Card actualCard = cardService.loadCardById(1L);
        assertEquals(card, actualCard);
    }

    @Test
    void testCreateCard() {
        Client client = new Client();
        client.setClientId(1L);
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        cardService.createCard("/payment_systems/Mastercard.png",11111111111111L, 10, 2023, 123,1200, client.getClientId());
        verify(cardRepository, times(1)).save(any());
    }

    @Test
    void testGetAllCards() {
        cardService.getAllCards();
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    void testRemoveCard() {
        cardService.removeCard(1L);
        verify(cardRepository, times(1)).deleteById(any());
    }
}