package com.example.deposit_system.repositories.opened_deposits;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"file:src/test/resources/db/clearAll.sql", "file:src/test/resources/db/inserts.sql"})
class OpenedTermDepositRepositoryTest extends AbstractTest {
    @Autowired
    private OpenedTermDepositRepository openedTermDepositRepository;

    @Test
    void testFindOpenedTermDepositsByClientId() {
        List<OpenedTermDeposit> openedTermDeposits = openedTermDepositRepository.findOpenedTermDepositsByClientClientId(1L);
        int expectResult = 1;
        assertEquals(expectResult, openedTermDeposits.size());
    }

    @Test
    void findOpenedTermDepositsByTermDepositDepositName() {
        List<OpenedTermDeposit> openedTermDeposits = openedTermDepositRepository.findOpenedTermDepositsByTermDepositDepositName("termDeposit2");
        int expectResult = 1;
        assertEquals(expectResult, openedTermDeposits.size());
    }
}