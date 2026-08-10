package com.deeksha.codereview.service;

import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }


    public User loginUser(String email, String password) {

        Optional<User> userOptional = userRepository.findByEmail(email);

        System.out.println("Email received: " + email);
        System.out.println("User found: " + userOptional.isPresent());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            System.out.println("Password from request: " + password);
            System.out.println("Password from DB: " + user.getPassword());

            boolean match = passwordEncoder.matches(
                    password,
                    user.getPassword()
            );

            System.out.println("Password match: " + match);

            if (match) {
                return user;
            }
        }

        return null;
    }
}