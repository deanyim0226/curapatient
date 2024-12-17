package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.service.UserService;
import com.example.demo.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Validate validate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){

        User retrievedUser = userService.findUserByName(request.getUsername());

        if(retrievedUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if(!userService.checkUser(retrievedUser, request.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        LoginResponse response = new LoginResponse();
        response.setApikey(userService.APIKEY);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        String username = user.getUsername();

        if(username == null || !validate.isValidUsername(username)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User savedUser = userService.saveUser(user);

        if(savedUser == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


}
