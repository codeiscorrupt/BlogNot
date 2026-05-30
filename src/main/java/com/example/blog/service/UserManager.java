package com.example.blog.service;

import com.example.blog.dao.entities.User;

import java.util.List;

public interface UserManager {

    User createUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}