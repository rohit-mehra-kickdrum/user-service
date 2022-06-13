package com.example.securitywithjwt;

import com.example.securitywithjwt.models.entity.Role;
import com.example.securitywithjwt.models.entity.User;
import com.example.securitywithjwt.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SecurityWithJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityWithJwtApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null,"ROLE_USER", List.of("READ")));
            userService.saveRole(new Role(null,"ROLE_MANAGER", List.of("READ")));
            userService.saveRole(new Role(null,"ROLE_ADMIN", List.of("READ", "WRITE")));
            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN", List.of("READ", "WRITE")));

            userService.saveUser(new User(null,"John Doe","john","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Will Smith","will","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Jim Carry","jim","1234",new ArrayList<>()));
            userService.saveUser(new User(null,"Arnold Schwarzenegger","arnold","1234",new ArrayList<>()));

            userService.addRoleToUser("john", "ROLE_USER");
            userService.addRoleToUser("john", "ROLE_MANAGER");
            userService.addRoleToUser("will", "ROLE_MANAGER");
            userService.addRoleToUser("jim", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_USER");
            userService.addRoleToUser("arnold", "ROLE_ADMIN");
            userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
        };
    }
}
