package com.deeksha.codereview.controller;
import com.deeksha.codereview.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import com.deeksha.codereview.dto.RegisterRequest;
import com.deeksha.codereview.entity.User;
import com.deeksha.codereview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.deeksha.codereview.dto.UserResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

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

    if (user != null) {
        return ResponseEntity.ok(user);
    }

    return ResponseEntity.status(401).body("Invalid credentials");
}
}