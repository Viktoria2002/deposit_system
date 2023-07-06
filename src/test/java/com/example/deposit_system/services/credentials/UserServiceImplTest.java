package com.example.deposit_system.services.credentials;

import com.example.deposit_system.entity.credentials.Role;
import com.example.deposit_system.entity.credentials.User;
import com.example.deposit_system.repositories.credentials.RoleRepository;
import com.example.deposit_system.repositories.credentials.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private User mockedUser;
    @Mock
    private RoleRepository roleRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testLoadUserByEmail() {
        userService.loadUserByEmail("email@gmail.com");
        verify(userRepository, times(1)).findUserByEmail(any());
    }

    @Test
    void testCreateUser() {
        String email = "user@gmail.com";
        User user = new User(email, null);
        userService.createUser(email, null);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertEquals(user, capturedUser);
    }

    @Test
    void testAssignRoleToUser() {
        Role role = new Role();
        role.setId(1L);
        when(userRepository.findUserByEmail(any())).thenReturn(mockedUser);
        when(roleRepository.findRoleByRoleName(any())).thenReturn(role);
        userService.assignRoleToUser("email@gmail.com","pass");
        verify(mockedUser, times(1)).assignRoleToUser(role);
    }
}