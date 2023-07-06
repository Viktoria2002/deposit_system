package com.example.deposit_system.repositories;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql","file:src/test/resources/db/inserts.sql"})
class BankRepositoryTest extends AbstractTest {
    @Autowired
    private BankRepository bankRepository;
    @Test
    void testFindBanksByNameContains() {
        List<Bank> banks = bankRepository.findBanksByBankNameContains("bank");
        int expectResult = 2;
        assertEquals(expectResult, banks.size());
    }

    @Test
    void findBankByBankName() {
        Bank bank = bankRepository.findBankByBankName("bank1");
        Long expectedId = 1L;
        assertEquals(expectedId, bank.getBankId());
    }

    @Test
    void testFindNonExistingBank() {
        String bankName = "newBank";
        Bank bank = bankRepository.findBankByBankName(bankName);
        assertNull(bank);
    }
}
