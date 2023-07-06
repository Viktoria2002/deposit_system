package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.entity.credentials.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
