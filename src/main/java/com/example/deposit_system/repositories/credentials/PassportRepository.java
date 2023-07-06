package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.entity.credentials.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    Passport findPassportByPassportNumber(String number);
}
