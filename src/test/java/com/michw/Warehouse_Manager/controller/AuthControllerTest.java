package com.michw.Warehouse_Manager.controller;

import com.michw.Warehouse_Manager.dto.AuthRequest;
import com.michw.Warehouse_Manager.dto.AuthResponse;
import com.michw.Warehouse_Manager.dto.RegisterRequest;
import com.michw.Warehouse_Manager.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
        RegisterRequest registerRequest = new RegisterRequest();
        AuthResponse authResponse = new AuthResponse("tokenPlaceholder");
        //when
        when(authService.register(registerRequest)).thenReturn(authResponse);
        ResponseEntity<AuthResponse> responseEntity = authController.register(registerRequest);
        //then
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().getToken().equals(authResponse.getToken());

        Mockito.verify(authService, times(1)).register(registerRequest);
    }
    @Test
    public void shouldReturnTokenAsAResponseToSuccessfulLogin(){
        //given
        AuthRequest authRequest = new AuthRequest();
        AuthResponse authResponse = new AuthResponse("tokenPlaceholder");
        //when
        when(authService.authenticate(authRequest)).thenReturn(authResponse);
        ResponseEntity<AuthResponse> responseEntity = authController.authenticate(authRequest);
        //then
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().getToken().equals(authResponse.getToken());

        Mockito.verify(authService, times(1)).authenticate(authRequest);
    }

}
