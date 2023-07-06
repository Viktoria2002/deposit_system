package com.example.deposit_system.entity.credentials;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"roles", "client"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotBlank(message = "Поле является обязательным.")
    @Pattern(regexp = "^$|^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,8})+$",
             message = "Адрес электронной почты введен некорректно")
    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;
    @NotBlank(message = "Поле является обязательным.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[!@#$%^&*./])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*./]{6,}",
//            message = "Пароль должен содержать хотя бы " +
//                    "одну цифру, одну латинскую букву в нижнем и верхнем регистрах, один\n" +
//                    "спецсимвол. Минимальное количество символов - 6.")
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ToString.Exclude
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private Client client;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void assignRoleToUser(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRoleFromUser(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
