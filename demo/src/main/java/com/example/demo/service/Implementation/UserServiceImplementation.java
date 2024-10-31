package com.example.demo.service.Implementation;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public User saveUser(User newUser) {

        String password = newUser.getPassword();
        String encodedPassword = encoder.encode(password);
        newUser.setPassword(encodedPassword);

        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserByName(String name) {
        return null;
    }
}
