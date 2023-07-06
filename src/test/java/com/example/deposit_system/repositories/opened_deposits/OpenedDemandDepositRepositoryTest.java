package com.example.deposit_system.repositories.opened_deposits;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
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
class OpenedDemandDepositRepositoryTest {
    @Autowired
    private OpenedDemandDepositRepository openedDemandDepositRepository;
    @Test
    void testFindOpenedDemandDepositByClientId() {
        List<OpenedDemandDeposit> openedDemandDeposits = openedDemandDepositRepository.findOpenedDemandDepositsByClientClientId(1L);
        int expectResult = 1;
        assertEquals(expectResult, openedDemandDeposits.size());
    }

    @Test
    void findOpenedDemandDepositsByDemandDepositDepositName() {
        List<OpenedDemandDeposit> openedDemandDeposits = openedDemandDepositRepository.findOpenedDemandDepositsByDemandDepositDepositName("demandDeposit1");
        int expectResult = 1;
        assertEquals(expectResult, openedDemandDeposits.size());
    }
}

