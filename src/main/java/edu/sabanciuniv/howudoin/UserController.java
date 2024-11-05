package edu.sabanciuniv.howudoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
