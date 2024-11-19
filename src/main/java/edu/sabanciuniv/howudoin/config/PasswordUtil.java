package edu.sabanciuniv.howudoin.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {


    private static final int SALT_LENGTH = 16;
    private static final int HASH_LENGTH = 256; // 256 bits, 32 bytes

    private static final int ITERATIONS = 10000;

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        byte[] hashedPassword = hashPasswordWithSalt(password, salt);

        // Combine the salt and hashed password for storage
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Generate a random salt
    private static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return salt;
    }

    // Hash the password + salt using PBKDF2
    private static byte[] hashPasswordWithSalt(String password, byte[] salt) throws NoSuchAlgorithmException {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new NoSuchAlgorithmException("Error hashing password", e);
        }
    }
    public static boolean verifyPassword(String password, String storedPassword) throws NoSuchAlgorithmException {
        // Split the stored password into salt and hashed password
        String[] parts = storedPassword.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        // Hash the entered password with the same salt
        byte[] enteredHash = hashPasswordWithSalt(password, salt);

        // Compare the entered hash with the stored hash
        return java.util.Arrays.equals(storedHash, enteredHash);
    }
}