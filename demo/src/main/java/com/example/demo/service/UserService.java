package com.example.demo.service;

import com.example.demo.domain.User;

public interface UserService {
    public static final String AUTH_APIKEY_HEADER_NAME = "x-api-key";
    public static final String APIKEY = "hardcodedsecurityapikey";
    public User saveUser(User newUser);

    public User findUserByName(String name);

    public boolean checkUser(User existingUser, String password);
}
