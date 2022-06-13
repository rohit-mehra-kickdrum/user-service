package com.example.securitywithjwt.repositories.custom;

import com.example.securitywithjwt.models.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User customFindMethod(String username) {
        return (User) entityManager.createQuery("FROM User u WHERE u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
    }
}
