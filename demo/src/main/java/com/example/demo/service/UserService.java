package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {

    public User saveUser(User newUser);

    public User findUserByName(String name);

    public boolean checkUser(User existingUser, String password);
}
