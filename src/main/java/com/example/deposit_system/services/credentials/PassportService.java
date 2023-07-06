package com.example.deposit_system.services.credentials;
import com.example.deposit_system.entity.credentials.Passport;

import java.time.LocalDate;

public interface PassportService {
    Passport loadPassportById(Long id);
    Passport loadPassportByNumber(String number);

    Passport createPassport(String passportNumber, String organization, LocalDate dateOfIssue, LocalDate validityPeriod);

    Passport updateOrCreatePassport(Passport passportData);
}
