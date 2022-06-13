package com.example.securitywithjwt.services;

import com.example.securitywithjwt.models.entity.Role;
import com.example.securitywithjwt.models.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();
}
