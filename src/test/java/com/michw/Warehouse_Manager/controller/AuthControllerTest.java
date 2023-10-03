package com.michw.Warehouse_Manager.controller;


import com.michw.Warehouse_Manager.dto.AuthRequest;
import com.michw.Warehouse_Manager.dto.RegisterRequest;
import com.michw.Warehouse_Manager.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;


public class AuthControllerTest {

    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        authController = new AuthController(authService);
    }

    @Test
    public void shouldReturnTokenAsAResponseToSuccessfulRegistration(){
        //given
        RegisterRequest registerRequest = new RegisterRequest("Testttt", "Userrr", "TestUser123@gmail.com", "TesAdat123123@@");
        String expectedToken = generateRandomJwtToken();

        //when
        when(authService.register(registerRequest)).thenReturn(new ResponseEntity<>(expectedToken, HttpStatus.OK));
        var response = authController.register(registerRequest);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedToken, response.getBody());

        verify(authService, times(1)).register(registerRequest);
    }

    @Test
    public void shouldAuthenticateAndReturnToken(){
        //given
        RegisterRequest registerRequest = new RegisterRequest("Testttt", "Userrr", "TestUser123@gmail.com", "TesAdat123123@@");
        authService.register(registerRequest);
        AuthRequest authRequest = new AuthRequest(
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );
        String jwtToken = generateRandomJwtToken();

        //when
        when(authService.authenticate(authRequest)).thenReturn(new ResponseEntity<>(jwtToken, HttpStatus.OK));
        var response = authController.authenticate(authRequest);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(jwtToken, response.getBody());

        verify(authService, times(1)).authenticate(authRequest);




    }

    private String generateRandomJwtToken(){
        return Jwts.builder()
                .setSubject("TestUser@gmail.com")
                .claim("roles", "user")
                .signWith(SignatureAlgorithm.HS256, "14b8b0acee3bd31cd8d3bb141cd6f1921fec7adfa77e28164f0ab8db172eada8")
                .compact();
    }


}
