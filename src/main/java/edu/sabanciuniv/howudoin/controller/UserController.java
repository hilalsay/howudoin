package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Group;
import edu.sabanciuniv.howudoin.service.GroupService;
import edu.sabanciuniv.howudoin.config.JwtTokenUtil;
import edu.sabanciuniv.howudoin.model.User;
import edu.sabanciuniv.howudoin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;



@RestController
public class UserController {

    @Autowired
    private UserService userService; // Dependency injection of UserService

    @Autowired
    private GroupService groupService; // Dependency injection of GroupService

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Dependency injection of JwtTokenUtil


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

    // Endpoint to retrieve groups associated with the authenticated user
    // Endpoint to retrieve groups associated with the authenticated user
    @GetMapping("/user")
    public ResponseEntity<List<Group>> getUserGroups(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.replace("Bearer ", "");

        // Use JwtTokenUtil to extract the user's email
        String userEmail = jwtTokenUtil.extractUsername(token);

        // Fetch groups where the user is a member
        List<Group> userGroups = groupService.getGroupsByUserEmail(userEmail);

        // Return groups as the response
        return ResponseEntity.ok(userGroups);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}



