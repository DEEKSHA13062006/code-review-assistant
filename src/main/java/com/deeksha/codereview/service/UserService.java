package com.deeksha.codereview.service;

import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    if (userOptional.isPresent()) {
        User user = userOptional.get();

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
    }

    return null;
}
}