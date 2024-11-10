package edu.sabanciuniv.howudoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Dependency injection of UserRepository

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Dependency injection of JwtTokenUtil

    public String registerUser(User user) {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Hashing the password before saving the user
        String salt = generateSalt();
        String hashedPassword = hashPassword(user.getPassword(), salt);
        user.setPassword(hashedPassword);
        user.setSalt(salt); // Save the salt as well
        userRepository.save(user); // Save user to the database

        // Generate JWT token for the user after registration
        return jwtTokenUtil.generateToken(user.getEmail());
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email); // Find user by email

        // Check if user exists and if the passwords match
        if (user == null || !isPasswordCorrect(password, user.getPassword(), user.getSalt())) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return user; // Return user if authentication is successful
    }

    private String generateSalt() {
        // Generate a secure random salt
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16]; // 16-byte salt
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt); // Encode salt to a string
    }

    private String hashPassword(String password, String salt) {
        try {
            // Use PBKDF2 hashing algorithm with SHA-256
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 10000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash); // Encode hashed password
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private boolean isPasswordCorrect(String providedPassword, String storedPasswordHash, String storedSalt) {
        // Hash the provided password with the stored salt and compare it with the stored hash
        String hashedPassword = hashPassword(providedPassword, storedSalt);
        return hashedPassword.equals(storedPasswordHash);
    }
}
