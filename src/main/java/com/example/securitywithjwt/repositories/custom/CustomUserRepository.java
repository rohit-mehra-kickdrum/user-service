package com.example.securitywithjwt.repositories.custom;

import com.example.securitywithjwt.models.entity.User;

public interface CustomUserRepository {
    User customFindMethod(String username);
}
