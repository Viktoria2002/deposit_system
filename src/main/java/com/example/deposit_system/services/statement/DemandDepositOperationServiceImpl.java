package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedDemandDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperationInfo;
import com.example.deposit_system.entity.statement.DemandDepositOperation;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.repositories.opened_deposits.OpenedDemandDepositRepository;
import com.example.deposit_system.repositories.statement.DemandDepositOperationRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class DemandDepositOperationServiceImpl implements DemandDepositOperationService {
    private DemandDepositOperationRepository operationRepository;
    private OpenedDemandDepositRepository openedDemandDepositRepository;
    private final Map<OperationType, DemandDepositOperation> operationMap;

    public DemandDepositOperationServiceImpl(DemandDepositOperationRepository operationRepository, OpenedDemandDepositRepository openedDemandDepositRepository, @Lazy List<DemandDepositOperation> list) {
        this.operationRepository = operationRepository;
        this.openedDemandDepositRepository = openedDemandDepositRepository;
        this.operationMap = list.stream().collect(Collectors.toMap(DemandDepositOperation::getType, Function.identity()));
    }

    @Override
    public DemandDepositOperationInfo createDemandDepositOperation(LocalDate dateOfOperation, OperationType type, double amount, double balance, Long openedDemandDepositId) {
        OpenedDemandDeposit openedDemandDeposit = openedDemandDepositRepository.findById(openedDemandDepositId).orElseThrow(() -> new EntityNotFoundException("Opened demand deposit with id" + openedDemandDepositId + " Not Found"));
        return operationRepository.save(new DemandDepositOperationInfo(dateOfOperation, type, amount, balance, openedDemandDeposit));
    }

    @Override
    public List<DemandDepositOperationInfo> getDemandDepositOperationsOfOpenedDemandDeposit(Long depositId) {
        return operationRepository.findDemandDepositOperationsByOpenedDemandDepositId(depositId);
    }

    @Override
    public void makeOperation(DemandDepositOperationInfo info) {
        DemandDepositOperation operation = operationMap.get(info.getType());
        operation.makeOperation(info);
    }
}
