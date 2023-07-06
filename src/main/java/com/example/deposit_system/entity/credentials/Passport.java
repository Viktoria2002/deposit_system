package com.example.deposit_system.entity.credentials;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "passports")
@Data
@NoArgsConstructor
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    protected Long id;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^[A-Z]{2}[0-9]{7}$",message = "Номер паспорта должен быть введен в формате XX1234567.")
    @Column(name = "passport_number", nullable = false)
    private String passportNumber;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^[а-яА-Я ]+$", message = "Название организации должно содержать только символы русского алфавита.")
    @Column(name = "organization", nullable = false)
    private String organization;

    @Column(name = "date_of_issue", nullable = false)
    private LocalDate dateOfIssue;

    @Column(name = "validity_period", nullable = false)
    private LocalDate validityPeriod;

    @OneToOne(mappedBy = "passportData")
    @ToString.Exclude
    @JsonIgnore
    private Client client;

    public Passport(String passportNumber, String organization, LocalDate dateOfIssue, LocalDate validityPeriod) {
        this.passportNumber = passportNumber;
        this.organization = organization;
        this.dateOfIssue = dateOfIssue;
        this.validityPeriod = validityPeriod;
    }
}
