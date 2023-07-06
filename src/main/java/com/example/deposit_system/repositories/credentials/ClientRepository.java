package com.example.deposit_system.repositories.credentials;

import com.example.deposit_system.entity.credentials.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select c from Client as c where c.name like %:name% or c.surname like %:name%")
    List<Client> findClientsByName(@Param("name") String name);

    @Query(value = "select c from Client as c where c.user.email=:email")
    Client findClientByEmail(@Param("email") String email);
}
