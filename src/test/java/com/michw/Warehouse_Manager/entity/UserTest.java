package com.michw.Warehouse_Manager.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class UserTest {

    @Test
    public void testUserCreation(){
        //given
        User user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        //then
        Assertions.assertEquals("Matthew", user.getFirstName());
        Assertions.assertEquals("Smith", user.getLastName());
        Assertions.assertEquals("Smith@gmail.com", user.getEmail());
        Assertions.assertEquals("123", user.getPassword());
        Assertions.assertEquals(Role.USER, user.getRole());
    }

}
