package com.example.deposit_system.entity;

import com.example.deposit_system.entity.deposits.DemandDeposit;
import com.example.deposit_system.entity.deposits.TermDeposit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
@Table(name = "banks")
@Data
@AllArgsConstructor
@Builder
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;
    @Column(name = "logo")
    private String logo;
    @NotBlank(message = "Поле является обязательным.")
    @Size(max = 25, message = "Название банка не должно превышать 25 символов")
    @Column(name = "bank_name", nullable = false)
    private String bankName;
    @NotBlank(message = "Поле является обязательным.")
    @Size(max = 35, message = "Адрес банка не должен превышать 35 символов")
    @Column(name = "address", nullable = false)
    private String address;
    @Pattern(regexp = "^$|^\\+375\\((29|44)\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$", message = "Номер сотового оператора A1 должен быть введеен в формате +375(29|44)###-##-##")
    @Column(name = "a1_number")
    private String a1Number;
    @Pattern(regexp = "^$|^\\+375\\((29|33)\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$", message = "Номер сотового оператора МТС должен быть введеен в формате +375(29|33)###-##-##")
    @Column(name = "mts_number")
    private String mtsNumber;
    @Pattern(regexp = "^$|^\\+375\\((25)\\)[0-9]{3}-[0-9]{2}-[0-9]{2}$", message = "Номер сотового оператора Life должен быть введеен в формате +375(25)###-##-##")
    @Column(name = "life_number")
    private String lifeNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank")
    @JsonIgnore
    private List<DemandDeposit> demandDeposits = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bank")
    @JsonIgnore
    private List<TermDeposit> termDeposits = new ArrayList<>();

    public Bank() {
    }
}
