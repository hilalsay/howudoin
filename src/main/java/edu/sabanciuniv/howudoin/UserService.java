package edu.sabanciuniv.howudoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Dependency injection of UserRepository

    public void registerUser(User user) {
        // Here you would typically hash the password and save the user
        user.setPassword(hashPassword(user.getPassword())); // Hashing the password
        userRepository.save(user); // Save user to the database
    }

    private String hashPassword(String password) {
        // Implement password hashing logic here (e.g., BCrypt)
        return password; // Replace with actual hashed password
    }
}
