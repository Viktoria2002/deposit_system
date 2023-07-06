package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Passport;
import com.example.deposit_system.repositories.credentials.PassportRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
@Service
@Transactional
public class PassportServiceImpl implements PassportService {
    private PassportRepository passportRepository;

    public PassportServiceImpl(PassportRepository passportDataRepository) {
        this.passportRepository = passportDataRepository;
    }

    @Override
    public Passport loadPassportById(Long id) {
        return passportRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passport data with id " + id + " Not Found"));
    }

    @Override
    public Passport loadPassportByNumber(String number) {
        return passportRepository.findPassportByPassportNumber(number);
    }

    @Override
    public Passport createPassport(String passportNumber, String organization, LocalDate dateOfIssue, LocalDate validityPeriod) {
        return passportRepository.save(new Passport(passportNumber, organization, dateOfIssue, validityPeriod));
    }

    @Override
    public Passport updateOrCreatePassport(Passport passportData) {
        return passportRepository.save(passportData);
    }
}
