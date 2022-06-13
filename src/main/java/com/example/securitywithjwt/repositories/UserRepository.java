package com.example.securitywithjwt.repositories;

import com.example.securitywithjwt.models.entity.User;
import com.example.securitywithjwt.repositories.custom.CustomUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>, CustomUserRepository {
    User findByUsername(String username);
}
