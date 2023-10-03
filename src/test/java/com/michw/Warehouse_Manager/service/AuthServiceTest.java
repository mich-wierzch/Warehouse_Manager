package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.dto.AuthRequest;
import com.michw.Warehouse_Manager.dto.RegisterRequest;
import com.michw.Warehouse_Manager.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                .password("123AHA@@@@@aaa")
                .build();
        //when
        var response = authService.register(request);

        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Smither@gmail.com", jwtService.extractUsername(response.getBody()));
        Assertions.assertNotNull(userRepository.findByEmail(jwtService.extractUsername(response.getBody())));
    }

    @Test
    public void shouldAuthenticateUser(){
        //given
        RegisterRequest request = RegisterRequest.builder()
                .firstName("Angelina")
                .lastName("Smith")
                .email("Angelina@gmail.com")
                .password("123AHA@@@@@aaa")
                .build();
        AuthRequest authRequest = AuthRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        //when
        authService.register(request);
        var response = authService.authenticate(authRequest);
        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Angelina@gmail.com", jwtService.extractUsername(response.getBody()));



    }

    }


