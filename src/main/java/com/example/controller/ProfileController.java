package com.example.controller;

import com.example.impl.UserImpl;
import com.example.model.Role;
import com.example.model.User;
import com.example.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserImpl userImpl;
    private final UserRepo userRepo;

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login?logout";
        }
        String username = principal.getName();
        User user = userImpl.findByUsername(username);

        if (user == null) {
            return "redirect:/login?logout";
        }

        model.addAttribute("isAdmin", user.getRoles().contains(Role.ADMIN));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/delete")
    public String deleteProfile(@RequestParam Long userId) {
        userImpl.delete(userId);
        return "redirect:/logout";
    }
}