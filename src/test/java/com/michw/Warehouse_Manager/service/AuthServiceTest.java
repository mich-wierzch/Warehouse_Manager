package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.AuthRequest;
import com.michw.Warehouse_Manager.dto.AuthResponse;
import com.michw.Warehouse_Manager.dto.RegisterRequest;
import com.michw.Warehouse_Manager.entity.Role;
import com.michw.Warehouse_Manager.entity.User;
import com.michw.Warehouse_Manager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;

    @Test
    public void shouldRegisterNewUser(){
        //given
        RegisterRequest request = RegisterRequest.builder()
                .firstName("Oscar")
                .lastName("Smith")
                .email("Smither@gmail.com")
                .password("123")
                .build();
        //when
        AuthResponse authResponse = authService.register(request);
        //then
        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals("Smither@gmail.com", jwtService.extractUsername(authResponse.getToken()));
        Assertions.assertNotNull(userRepository.findByEmail(jwtService.extractUsername(authResponse.getToken())));
    }

    @Test
    public void shouldAuthenticateUser(){
        //given
        RegisterRequest request = RegisterRequest.builder()
                .firstName("Angelina")
                .lastName("Smith")
                .email("Angelina@gmail.com")
                .password("123")
                .build();
        AuthRequest authRequest = AuthRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        //when
        authService.register(request);
        AuthResponse authResponse = authService.authenticate(authRequest);
        //then
        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals("Angelina@gmail.com", jwtService.extractUsername(authResponse.getToken()));



    }

    }


