package edu.sabanciuniv.howudoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Dependency injection of UserRepository

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Dependency injection of JwtTokenUtil

    public String registerUser(User user) {
        // Here you would typically hash the password and save the user
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        user.setPassword(hashPassword(user.getPassword())); // Hashing the password
        userRepository.save(user); // Save user to the database

        // Generate JWT token for the user after registration
        return jwtTokenUtil.generateToken(user.getName()); // Assuming User has a getUsername() method
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email); // Find user by email

        // Check if user exists and if the passwords match
        if (user == null || !user.getPassword().equals(hashPassword(password))) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user; // Return user if authentication is successful
    }

    private String hashPassword(String password) {
        // Implement password hashing logic here (e.g., BCrypt)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }


}
