package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){

        User retrievedUser = userService.findUserByName(user.getUsername());

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }
}
