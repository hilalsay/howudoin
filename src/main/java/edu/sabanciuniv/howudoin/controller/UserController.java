package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.User;
import edu.sabanciuniv.howudoin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
public class UserController {

    @Autowired
    private UserService userService; // Dependency injection of UserService

    @PostMapping("/register") // Endpoint for user registration
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user); // Call the service to register the user
        return ResponseEntity.ok("User registered successfully!"); // Return success response
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            // Call the service to log in the user and get the JWT token
            String jwtToken = userService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(jwtToken); // Send the token back in the response
        } catch (IllegalArgumentException ex) {
            // If login fails, return a BAD_REQUEST with the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

