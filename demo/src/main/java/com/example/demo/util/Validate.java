package com.example.demo.util;


import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

@Component
public final class Validate {


    public boolean isValidId(Integer id){
        return id != null && id >= 0;
    }

    public boolean isValidKey(String apikey){

        if(apikey == null || apikey.equals("") || !apikey.equals(UserService.APIKEY)){
            return false;
        }
        return true;
    }
}
