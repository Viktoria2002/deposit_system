package com.example.deposit_system.repositories;

import com.example.deposit_system.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findCardByNumber(long number);
}
