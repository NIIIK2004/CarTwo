package com.example.repo;

import com.example.model.StatementStatus;
import com.example.model.Statements;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementsRepo extends JpaRepository<Statements, Long> {
    List<Statements> findByUser(User user);
    List<Statements> findByStatus(StatementStatus status);
}