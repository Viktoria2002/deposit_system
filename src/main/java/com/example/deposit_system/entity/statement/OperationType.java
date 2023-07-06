package com.example.deposit_system.entity.statement;

public enum OperationType {
    OPENING("Открытие"),
    REPLENISHMENT("Пополнение"),
    WITHDRAWAL("Снятие"),
    EARLY_CLOSURE("Досрочное закрытие"),
    CAPITALIZATION("Капитализация"),
    SIMPLE_PERCENTAGES("Простые проценты");

    private String operation;

    OperationType(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
