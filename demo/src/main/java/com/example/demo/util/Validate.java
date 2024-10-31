package com.example.demo.util;


import org.springframework.stereotype.Component;

@Component
public final class Validate {


    public boolean isValidId(Integer id){
        return id != null && id >= 0;
    }
}
