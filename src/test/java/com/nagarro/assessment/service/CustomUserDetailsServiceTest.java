package com.nagarro.assessment.service;

import com.nagarro.assessment.model.entity.User;
import com.nagarro.assessment.model.repository.UserRepository;
import com.nagarro.assessment.model.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a test user
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("password123");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Given
        String username = "testUser";
        when(userRepository.findByUsername(username)).thenReturn(testUser);

        // When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Then
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Given
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // When & Then
        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(username)
        );

        // Assert exception message
        assertEquals("User not found", thrown.getMessage());
    }
}
