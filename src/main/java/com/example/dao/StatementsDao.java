package com.example.dao;

import com.example.model.StatementStatus;
import com.example.model.Statements;
import com.example.model.User;

import java.util.List;

public interface StatementsDao {
    Statements save(Statements statements, String username);
    List<Statements> getAllStatementsForUser(String username);
    void updateStatementStatus(Long statementId, StatementStatus status);
}