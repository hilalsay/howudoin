package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.FriendRequest;
import edu.sabanciuniv.howudoin.model.FriendRequestDto;
import edu.sabanciuniv.howudoin.model.User;
import edu.sabanciuniv.howudoin.repository.FriendRequestRepository;
import edu.sabanciuniv.howudoin.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    public FriendService(UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
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
    public List<FriendRequestDto> getPendingFriendRequests(String receiverEmail) {
        List<FriendRequest> requests = friendRequestRepository.findByReceiverEmailAndStatus(receiverEmail, "PENDING");
        System.out.println("Fetched Requests: " + requests);  // Log the fetched requests
        return requests.stream()
                .map(request -> new FriendRequestDto(request.getSenderEmail(), request.getReceiverEmail(), request.getStatus()))
                .collect(Collectors.toList());
    }
    public List<FriendRequestDto> getAllFriendRequests(String receiverEmail) {
        // Find the user by email (receiverEmail)
        User receiver = userRepository.findByEmail(receiverEmail);

        if (receiver == null) {
            throw new RuntimeException("User not found");
        }

        // Get the list of friend requests (sender emails)
        List<String> friendRequests = receiver.getFriendRequests();

        // Convert the list of sender emails to FriendRequestDto (or whatever structure you use)
        return friendRequests.stream()
                .map(senderEmail -> new FriendRequestDto(senderEmail, receiverEmail, "PENDING"))  // Assuming PENDING status for all
                .collect(Collectors.toList());
    }

}
