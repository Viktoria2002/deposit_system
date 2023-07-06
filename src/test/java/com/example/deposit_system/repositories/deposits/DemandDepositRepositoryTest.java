package com.example.deposit_system.repositories.deposits;

import com.example.deposit_system.AbstractTest;
import com.example.deposit_system.entity.deposits.DemandDeposit;
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
class DemandDepositRepositoryTest extends AbstractTest {
    @Autowired
    private DemandDepositRepository demandDepositRepository;
    @Test
    void testFindDemandDepositsByNameContains() {
        List<DemandDeposit> demandDeposits = demandDepositRepository.findDemandDepositsByDepositNameContains("demandDeposit");
        int expectResult = 2;
        assertEquals(expectResult, demandDeposits.size());
    }

    @Test
    void testFindDemandDepositsByBankName() {
        List<DemandDeposit> demandDeposits = demandDepositRepository.findDemandDepositsByBankBankName("bank1");
        int expectResult = 1;
        assertEquals(expectResult, demandDeposits.size());
    }
}
