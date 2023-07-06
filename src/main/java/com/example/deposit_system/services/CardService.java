package com.example.deposit_system.services;

import com.example.deposit_system.entity.Card;

import java.util.List;

public interface CardService {
    Card loadCardById(Long cardId);

    Card createCard(String paymentSystem, long number, int month, int year, int cvv, double balance, Long clientId);
    Card createOrUpdateCard(Card card);
    Card loadCardByCardNumber(Long cardNumber);

    List<Card> getAllCards();

    void removeCard(Long cardId);

    Card findCardByNumber(long number);
}
