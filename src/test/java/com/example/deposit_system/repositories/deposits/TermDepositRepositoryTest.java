package com.example.deposit_system.repositories.deposits;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.deposits.TermDeposit;
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
class TermDepositRepositoryTest extends AbstractTest {
    @Autowired
    private TermDepositRepository termDepositRepository;
    @Test
    void testFindTermDepositsByNameContains() {
        List<TermDeposit> termDeposits = termDepositRepository.findTermDepositsByDepositNameContains("termDeposit");
        int expectResult = 2;
        assertEquals(expectResult, termDeposits.size());
    }

    @Test
    void testFindTermDepositsByBankName() {
        List<TermDeposit> termDeposits = termDepositRepository.findTermDepositsByBankBankName("bank1");
        int expectResult = 1;
        assertEquals(expectResult, termDeposits.size());
    }
}

