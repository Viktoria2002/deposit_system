package com.example.deposit_system.entity.credentials;

import com.example.deposit_system.entity.Card;
import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"generalData", "passportData", "user", "cards", "demandDeposits", "termDeposits"})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    protected Long clientId;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^([а-яА-Я]+)|([a-zA-Z]+)$", message = "Фамилия должна содержать только символы русского или латинского алфавита.")
    @Column(name = "surname", nullable = false)
    private String surname;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^([а-яА-Я]+)|([a-zA-Z]+)$", message = "Имя должна содержать только символы русского или латинского алфавита.")
    @Column(name = "name", nullable = false)
    private String name;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^([а-яА-Я]+)|([a-zA-Z]+)$", message = "Отчество должна содержать только символы русского или латинского алфавита.")
    @Column(name = "patronymic", nullable = false)
    private String patronymic;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^\\+375\\((29|44|25|33)\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$", message = "Номер телефона должен\n" +
            " соответствовать шаблону \"+375(29|25|44|33)###-##-##\"")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "passport_data_id")
    @ToString.Exclude
    @Valid
    private Passport passportData;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @Valid
    private User user;
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    @JsonIgnore
    private List<Card> cards = new CopyOnWriteArrayList<>();
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    @JsonIgnore
    private List<OpenedDemandDeposit> demandDeposits = new ArrayList<>();
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    @JsonIgnore
    private List<OpenedTermDeposit> termDeposits = new ArrayList<>();

    public Client(String surname, String name, String patronymic, String gender, LocalDate dateOfBirth, String phoneNumber, Passport passportData, User user) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.passportData = passportData;
        this.user = user;
    }
}
