package com.example.demo.util;


import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public final class Validate {

    private static final Pattern validUsername = Pattern.compile("^[A-Za-z]+");
    public boolean isValidId(Integer id){
        return id != null && id >= 0;
    }

    public boolean isValidKey(String apikey){
        if(apikey == null || apikey.equals("") || !apikey.equals(UserService.APIKEY)){
            return false;
        }
        return true;
    }
    public boolean isValidUsername(String username){

        if(username.length() < 2 || !validUsername.matcher(username).matches()){
            return false;
        }
        return true;
    }
}
