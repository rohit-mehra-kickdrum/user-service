package com.example.securitywithjwt;

import com.example.securitywithjwt.models.entity.User;
import com.example.securitywithjwt.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SecurityWithJwtApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void checkCustomRepositoryWorking() {
        User user = new User();
        user.setName("Rakesh Singh");
        user.setUsername("rakesh");
        user.setPassword("1234");

        User persistedUser = userRepository.save(user);

        assertEquals(persistedUser.getName(), userRepository.customFindMethod(user.getUsername()).getName());
    }
}
