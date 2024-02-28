package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatementStatus {
    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    REJECTED("Отклонен");

    private final String russianText;

}