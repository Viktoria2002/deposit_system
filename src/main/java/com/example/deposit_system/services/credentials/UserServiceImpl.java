package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Role;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.repositories.credentials.RoleRepository;
import com.example.deposit_system.repositories.credentials.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        User user = loadUserByEmail(email);
        if (user != null) throw new RuntimeException("User with email :" + email + "already exist");
        String encodedPassword = passwordEncoder.encode(password);
        return userRepository.save(new User(email, encodedPassword));
    }

    @Override
    public User createOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleRepository.findRoleByRoleName(roleName);
        user.assignRoleToUser(role);
    }
}
