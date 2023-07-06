package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Client;
import com.example.deposit_system.entity.credentials.Passport;
import com.example.deposit_system.repositories.credentials.PassportRepository;
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
class PassportServiceImplTest {
    @Mock
    private PassportRepository passportRepository;
    @InjectMocks
    private PassportServiceImpl passportService;
    @Test
    void testLoadPassportById() {
        Passport passport = new Passport();
        passport.setId(1L);
        when(passportRepository.findById(1L)).thenReturn(Optional.of(passport));
        Passport actualPassport = passportService.loadPassportById(1L);
        assertEquals(passport, actualPassport);
    }

    @Test
    void testCreatePassport() {
        passportService.createPassport("1234567", "organization", LocalDate.of(2010,11,11), LocalDate.of(2030,11,11));
        verify(passportRepository, times(1)).save(any());
    }

    @Test
    void testUpdateOrCreatePassport() {
        Passport passport = new Passport();
        passport.setId(1L);
        passportService.updateOrCreatePassport(passport);
        verify(passportRepository).save(passport);
    }
}