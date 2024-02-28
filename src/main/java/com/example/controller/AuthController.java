package com.example.controller;

import com.example.impl.UserImpl;
import com.example.model.User;
import com.example.validator.RegistrationValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserImpl userImpl;

    private final RegistrationValidator registrationValidator;

    @GetMapping("/registration")
    public String regPage(Model model) {
        model.addAttribute("user", new User());
        return "reg";
    }

    @PostMapping("/registration/save")
    public String reg(@ModelAttribute("user") @Valid User user, Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        registrationValidator.validate(user, result);

        try {
            if (result.hasErrors()) {
                model.addAttribute("errors", result.getAllErrors());
                model.addAttribute("user", user);
                return "reg";
            }
            userImpl.registerAuthUser(user, redirectAttributes);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            result.rejectValue("username", e.getMessage());
            model.addAttribute("user", user);
            return "reg";
        }
    }
    @GetMapping("login")
    public String login() {
        return "auth";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        userImpl.logout(request, response);
        return "redirect:/login?logout";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/profile";
        }
        return "index";
    }
}
