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
        boolean requestAlreadySent = receiver.getFriendRequests().stream()
                .anyMatch(request -> request.getSenderEmail().equals(senderEmail));
        if (requestAlreadySent) {
            return "Friend request already sent.";
        }

        // Create a new FriendRequest object
        FriendRequest newRequest = new FriendRequest(senderEmail, receiverEmail, "PENDING");

        // Add sender's friend request to receiver's list
        receiver.getFriendRequests().add(newRequest);

        // Save the updated receiver
        userRepository.save(receiver);

        return "Friend request sent.";
    }




    public List<String> getFriends(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getFriends();
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

        // Find the friend request from sender to receiver
        FriendRequest friendRequest = receiver.getFriendRequests().stream()
                .filter(request -> request.getSenderEmail().equals(senderEmail))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No friend request found"));

        // Change the status to ACCEPTED
        friendRequest.setStatus("ACCEPTED");

        // Add each other as friends
        receiver.getFriends().add(senderEmail);
        sender.getFriends().add(receiverEmail);

        // Save the updated users
        userRepository.save(receiver);
        userRepository.save(sender);
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

        // Find the friend request from sender to receiver
        FriendRequest friendRequest = receiver.getFriendRequests().stream()
                .filter(request -> request.getSenderEmail().equals(senderEmail))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No friend request found"));

        // Change the status to REJECTED
        friendRequest.setStatus("REJECTED");

        // Save the updated receiver (no changes to friends)
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
        List<FriendRequest> friendRequests = receiver.getFriendRequests();

        // Convert the list of sender emails to FriendRequestDto
        return friendRequests.stream()
                .map(request -> new FriendRequestDto(request.getSenderEmail(), request.getReceiverEmail(), request.getStatus())) // Use request status
                .collect(Collectors.toList());
    }

    public List<FriendRequestDto> getAllSentFriendRequests(String senderEmail) {
        // Fetch the user by their email (sender)
        User sender = userRepository.findByEmail(senderEmail);  // Assuming findByEmail method exists

        // If no sender found, return empty list
        if (sender == null) {
            return new ArrayList<>();
        }

        // Filter the requests to find those sent by this sender
        return sender.getSentRequests().stream()
                .map(request -> new FriendRequestDto(
                        request.getSenderEmail(),          // The sender is the current user
                        request.getReceiverEmail(),        // The receiver is the target user
                        request.getStatus()                // The actual status (PENDING, ACCEPTED, etc.)
                ))
                .collect(Collectors.toList());
    }


}
