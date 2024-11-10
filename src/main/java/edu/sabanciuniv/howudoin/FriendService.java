package edu.sabanciuniv.howudoin;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class FriendService {

    private final UserRepository userRepository;

    public FriendService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String sendFriendRequest(String senderEmail, String receiverEmail) {
        // Check if the sender is trying to send a request to themselves
        if (senderEmail.equals(receiverEmail)) {
            return "You cannot send a friend request to yourself.";
        }

        User sender = userRepository.findByEmail(senderEmail);
        if (sender == null) {
            throw new IllegalArgumentException("Sender not found");
        }

        User receiver = userRepository.findByEmail(receiverEmail);
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver not found");
        }

        // Check if they are already friends
        if (receiver.getFriends().contains(senderEmail)) {
            return "Friend is already added.";
        }

        // Check if a friend request is already sent
        if (receiver.getFriendRequests().contains(senderEmail)) {
            return "Friend request already sent.";
        }

        // Add sender's request to receiver's friendRequests
        receiver.getFriendRequests().add(senderEmail);
        userRepository.save(receiver);

        return "Friend request sent.";
    }


    public void acceptFriendRequest(String senderEmail, String receiverEmail) {
        User sender = userRepository.findByEmail(senderEmail);
        if (sender == null) {
            throw new IllegalArgumentException("Sender not found");
        }

        User receiver = userRepository.findByEmail(receiverEmail);
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver not found");
        }

        if (!receiver.getFriendRequests().contains(senderEmail)) {
            throw new IllegalArgumentException("No friend request found");
        }



        // Remove the friend request and add each other as friends
        receiver.getFriendRequests().remove(senderEmail);
        receiver.getFriends().add(senderEmail);
        sender.getFriends().add(receiverEmail);

        userRepository.save(receiver);
        userRepository.save(sender);
    }

    public List<String> getFriends(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getFriends();
    }
    public void rejectFriendRequest(String senderEmail, String receiverEmail) {
        User sender = userRepository.findByEmail(senderEmail);
        if (sender == null) {
            throw new IllegalArgumentException("Sender not found");
        }

        User receiver = userRepository.findByEmail(receiverEmail);
        if (receiver == null) {
            throw new IllegalArgumentException("Receiver not found");
        }

        if (!receiver.getFriendRequests().contains(senderEmail)) {
            throw new IllegalArgumentException("No friend request found");
        }

        // Remove the friend request without adding to friends
        receiver.getFriendRequests().remove(senderEmail);
        userRepository.save(receiver);
    }


}
