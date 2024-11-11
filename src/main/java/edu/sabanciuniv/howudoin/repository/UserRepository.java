package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email); // Method to find user by email
}
