package com.example.deposit_system.services.statement;

import com.example.deposit_system.entity.opened_deposits.OpenedTermDeposit;
import com.example.deposit_system.entity.statement.DemandDepositOperation;
import com.example.deposit_system.entity.statement.OperationType;
import com.example.deposit_system.entity.statement.TermDepositOperation;
import com.example.deposit_system.entity.statement.TermDepositOperationInfo;
import com.example.deposit_system.repositories.opened_deposits.OpenedTermDepositRepository;
import com.example.deposit_system.repositories.statement.TermDepositOperationRepository;
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
public class TermDepositOperationServiceImpl implements TermDepositOperationService {
    private TermDepositOperationRepository operationRepository;
    private OpenedTermDepositRepository openedTermDepositRepository;
    private final Map<OperationType, TermDepositOperation> operationMap;

    public TermDepositOperationServiceImpl(TermDepositOperationRepository operationRepository, OpenedTermDepositRepository openedTermDepositRepository, @Lazy List<TermDepositOperation> list) {
        this.operationRepository = operationRepository;
        this.openedTermDepositRepository = openedTermDepositRepository;
        this.operationMap = list.stream().collect(Collectors.toMap(TermDepositOperation::getType, Function.identity()));
    }

    @Override
    public TermDepositOperationInfo createTermDepositOperation(LocalDate dateOfOperation, OperationType type, double amount, double balance, Long openedTermDepositId) {
        OpenedTermDeposit openedTermDeposit = openedTermDepositRepository.findById(openedTermDepositId).orElseThrow(() -> new EntityNotFoundException("Opened term deposit with id" + openedTermDepositId + " Not Found"));
        return operationRepository.save(new TermDepositOperationInfo(dateOfOperation, type, amount, balance, openedTermDeposit));
    }

    @Override
    public List<TermDepositOperationInfo> getTermDepositOperationsOfOpenedTermDeposit(Long depositId) {
        return operationRepository.findTermDepositOperationsByOpenedTermDepositId(depositId);
    }

    @Override
    public void makeOperation(TermDepositOperationInfo info) {
        TermDepositOperation operation = operationMap.get(info.getType());
        operation.makeOperation(info);
    }
}
