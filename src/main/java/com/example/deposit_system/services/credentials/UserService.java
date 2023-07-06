package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.User;

public interface UserService {
    User loadUserByEmail(String email);

    User createUser(String email, String password);
    User createOrUpdateUser(User user);

    void assignRoleToUser(String email, String roleName);

//    boolean doesCurrentUserHasRole(String roleName);
}
