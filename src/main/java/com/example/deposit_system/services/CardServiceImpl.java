package com.example.deposit_system.services;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.repositories.CardRepository;
import com.example.deposit_system.repositories.credentials.ClientRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class CardServiceImpl implements CardService {
    private CardRepository cardRepository;
    private ClientRepository clientRepository;

    public CardServiceImpl(CardRepository cardRepository, ClientRepository clientRepository) {
        this.cardRepository = cardRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Card loadCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("Card with id " + cardId + " Not Found"));
    }

    @Override
    public Card createCard(String paymentSystem, long number, int month, int year, int cvv, double balance, Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client with id " + clientId + " Not Found"));
        return cardRepository.save(new Card(paymentSystem, number, month, year, cvv, balance, client));
    }

    @Override
    public Card createOrUpdateCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card loadCardByCardNumber(Long cardNumber) {
        return cardRepository.findCardByNumber(cardNumber);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public void removeCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    @Override
    public Card findCardByNumber(long number) {
        return cardRepository.findCardByNumber(number);
    }
}
