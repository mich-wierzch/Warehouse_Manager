package com.michw.Warehouse_Manager.repository;

import com.michw.Warehouse_Manager.entity.Role;
import com.michw.Warehouse_Manager.entity.User;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest

public class UserRepositoryTest {
@Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUserToDatabase(){
        //given
        User user = User.builder()
                .firstName("Matthew")
                .lastName("Smith")
                .email("Smith@gmail.com")
                .password("123")
                .role(Role.USER)
                .build();
        //when
        userRepository.save(user);
        //then
        Optional<User> savedUser = userRepository.findByEmail("Smith@gmail.com");
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("Matthew", user.getFirstName());
        Assertions.assertEquals("Smith", user.getLastName());


    }

}
