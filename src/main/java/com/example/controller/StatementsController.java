package com.example.controller;


import com.example.impl.StatementImpl;
import com.example.impl.UserImpl;
import com.example.model.Role;
import com.example.model.StatementStatus;
import com.example.model.Statements;
import com.example.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/statements")
public class StatementsController {
    private final StatementImpl statementImpl;
    private final UserImpl userImpl;

    @GetMapping("/admin/all")
    public String getAllStatements(Model model, Principal principal) {
        String username = principal.getName();
        List<Statements> statements = statementImpl.getAllStatementsForUser(username);

        Collections.reverse(statements);
        model.addAttribute("statement", statements);

        User user = userImpl.findByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));

        return "/admin/adminAllStatements";
    }

    @PostMapping("/updateStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        statementImpl.updateStatementStatus(id, StatementStatus.valueOf(status));
        return "redirect:/statements/admin/all";
    }

    @GetMapping("user")
    public String getUserStatements(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            List<Statements> statements = statementImpl.getAllStatementsForUser(username);
            Collections.reverse(statements);
            model.addAttribute("statement", statements);

            User user = userImpl.findByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));

            return "Statements";
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/create")
    public String getUserAddStatements(Model model, Principal principal) {
        String username = principal.getName();
        List<Statements> userStatements = statementImpl.getAllStatementsForUser(username);
        model.addAttribute("userStatements", userStatements);

        User user = userImpl.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));

        return "AddStatements";
    }

    @PostMapping("/add")
    public String addStatement(@ModelAttribute Statements statements, Principal principal, Model model) {
        if (principal != null) {
            String username = principal.getName();
            statementImpl.save(statements, username);

            User user = userImpl.findByUsername(username);
            model.addAttribute("user", user);

            return "redirect:/statements/user";
        }
        return "redirect:/login?logout";
    }

}
