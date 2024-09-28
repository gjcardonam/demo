package com.example.demo.services;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsers() {
        // Dado
        ArrayList<UserModel> users = new ArrayList<>();
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        // Cuando
        ArrayList<UserModel> result = userService.getUsers();

        // Entonces
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void saveUser() {
        // Dado
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.save(any(UserModel.class))).thenReturn(user);

        // Cuando
        UserModel result = userService.saveUser(user);

        // Entonces
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById() {
        // Dado
        UserModel user = new UserModel();
        user.setId(1L);
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Cuando
        Optional<UserModel> result = userService.getUserById(1L);

        // Entonces
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUser() {
        // No hay necesidad de definir el comportamiento de deleteById
        // porque no devuelve nada

        // Cuando
        userService.deleteUser(1L);

        // Entonces
        verify(userRepository, times(1)).deleteById(1L);
    }
}