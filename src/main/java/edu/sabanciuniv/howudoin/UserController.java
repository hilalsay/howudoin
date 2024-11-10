package edu.sabanciuniv.howudoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users") // Base URL for user-related endpoints
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
            userService.loginUser(user.getEmail(), user.getPassword()); // Attempt to log in
            return ResponseEntity.ok("Login successful!");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
