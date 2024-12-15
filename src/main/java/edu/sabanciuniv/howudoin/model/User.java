package edu.sabanciuniv.howudoin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String password; // Store hashed password
    private String salt;

    private List<String> friends; // List of accepted friend emails
    private List<FriendRequest> friendRequests; // List of FriendRequest objects
    private List<FriendRequest> sentRequests;
    // Default constructor
    public User() {
        this.friends = new ArrayList<>(); // Initialize as an empty list
        this.friendRequests = new ArrayList<>(); // Initialize as an empty list
        this.sentRequests = new ArrayList<>();
    }

    // Parameterized constructor
    public User(String id, String name, String lastname, String email, String password) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.friends = new ArrayList<>(); // Initialize as an empty list
        this.sentRequests = new ArrayList<>();
        this.friendRequests = new ArrayList<>(); // Initialize as an empty list
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // Getter and Setter for friends
    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    // Getter and Setter for friendRequests
    public List<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(List<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name and lastname ='" + name + " " + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<FriendRequest> getSentRequests() {
        return sentRequests;
    }
    public void setSentRequests(List<FriendRequest> friendRequests) {
        this.sentRequests = friendRequests;
    }
}
