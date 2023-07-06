package com.example.deposit_system.web.credentials;

import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.services.credentials.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/index")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public User user(Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        return user;
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('Admin', 'Client')")
    public User update(@RequestBody User user, Principal principal) {
        User updatedUser = userService.loadUserByEmail(principal.getName());
        String password = passwordEncoder.encode(user.getPassword());
        updatedUser.setPassword(password);
        return userService.createOrUpdateUser(updatedUser);
    }
}
