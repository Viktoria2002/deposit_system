package com.example.deposit_system.web.credentials;

import com.example.deposit_system.services.credentials.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthorizationController {
    private UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(value = "/login")
    public String authorized() {
        return "home";
    }
    @GetMapping(value = "/login")
    public String login() {
        return "credential-views/login";
    }
}
