package com.mywebsite.backend.controller;

import com.mywebsite.backend.model.User;
import com.mywebsite.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) throws Exception {
        User registeredUser = userService.registerUser(request.getIdToken(), request.getFullName());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) throws Exception {
        User loggedInUser = userService.loginUser(request.getIdToken());
        return ResponseEntity.ok(loggedInUser);
    }
}

class RegisterRequest {
    private String idToken;
    private String fullName;

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
}

class LoginRequest {
    private String idToken;

    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }
}