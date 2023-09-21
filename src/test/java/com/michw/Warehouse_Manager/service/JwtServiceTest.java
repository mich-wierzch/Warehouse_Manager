package com.michw.Warehouse_Manager.service;

import com.michw.Warehouse_Manager.entity.Role;
import com.michw.Warehouse_Manager.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class JwtServiceTest {

    @Test
    public void shouldGenerateNewToken(){
        //given
        var user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        JwtService jwtService = new JwtService();
        //when
        var jwtToken = jwtService.generateToken(user);
        //then
        Assertions.assertNotNull(jwtToken);
    }
    @Test
    public void shouldExtractUsernameWhichIsEmailInThisCase(){
        //given
        var user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        JwtService jwtService = new JwtService();
        //when
        var jwtToken = jwtService.generateToken(user);
        //then
        String userEmail = jwtService.extractUsername(jwtToken);
        Assertions.assertEquals("Smith@gmail.com", userEmail);
    }

    @Test
    public void shouldExtractClaim(){
        //given
        var user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        JwtService jwtService = new JwtService();
        //when
        var jwtToken = jwtService.generateToken(user);

        //then
        Date claim = jwtService.extractClaim(jwtToken, Claims::getExpiration);
        Assertions.assertNotNull(claim);
    }
    @Test
    public void shouldReturnTrueIfTokenIsValid(){
        var user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        JwtService jwtService = new JwtService();
        //when
        var jwtToken = jwtService.generateToken(user);
        //then
        boolean isValid = jwtService.isTokenValid(jwtToken, user);
        Assertions.assertTrue(isValid);
    }

}
