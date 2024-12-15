package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    public List<User> findByNameOrEmail(String query);

    // A simple method to find users by email
    List<User> findByEmailContaining(String query); // Assumes 'query' is a search term in email
    User findByEmail(String email);
    // A custom query for both name and email
    @Query("{'$or': [{'email': {'$regex': ?0, $options: 'i'}}, {'name': {'$regex': ?0, $options: 'i'}}]}")
    List<User> searchUsers(String query);

    @Query("{ 'friendRequests': ?0 }")
    List<User> findUsersByFriendRequestsContaining(String senderEmail); // Method to find users with specific sender email in friendRequests
}
