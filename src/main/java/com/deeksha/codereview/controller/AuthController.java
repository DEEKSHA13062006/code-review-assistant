package com.deeksha.codereview.controller;

import com.deeksha.codereview.dto.LoginRequest;
import com.deeksha.codereview.dto.LoginResponse;
import com.deeksha.codereview.dto.RegisterRequest;
import com.deeksha.codereview.dto.UserResponse;
import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.service.JwtService;
import com.deeksha.codereview.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userService.registerUser(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.getEmail(),
                request.getPassword()
        );

        if (user == null) {
            return ResponseEntity
                    .status(401)
                    .body("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getEmail()
        );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}