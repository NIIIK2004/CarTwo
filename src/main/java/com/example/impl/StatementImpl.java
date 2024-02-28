package com.example.impl;

import com.example.dao.StatementsDao;
import com.example.model.Role;
import com.example.model.StatementStatus;
import com.example.model.Statements;
import com.example.model.User;
import com.example.repo.StatementsRepo;
import com.example.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementImpl implements StatementsDao {

    private final StatementsRepo statementsRepo;
    private final UserRepo userRepo;

    @Override
    public Statements save(Statements statements, String username) {
        User user = userRepo.findByUsername(username);
        statements.setUser(user);
        return statementsRepo.save(statements);
    }

    @Override
    public List<Statements> getAllStatementsForUser(String username) {
        User user = userRepo.findByUsername(username);
        if (user.getRoles().contains(Role.ADMIN)) {
            return statementsRepo.findAll();
        } else {
            user = userRepo.findByUsername(username);
            return statementsRepo.findByUser(user);
        }
    }

    @Override
    public void updateStatementStatus(Long statementId, StatementStatus status) {
        Statements statements = statementsRepo.findById(statementId)
                .orElseThrow(() -> new IllegalThreadStateException("Невалидный короче ID Заявки"));
        statements.setStatus(status);
        statementsRepo.save(statements);
    }
}