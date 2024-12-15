package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.FriendRequest;
import edu.sabanciuniv.howudoin.model.FriendRequestDto;
import edu.sabanciuniv.howudoin.model.User;
import edu.sabanciuniv.howudoin.repository.UserRepository;
import edu.sabanciuniv.howudoin.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserRepository userRepository;  // Declare the UserRepository field

    // Inject UserRepository into the constructor
    public FriendController(FriendService friendService, UserRepository userRepository) {
        this.friendService = friendService;
        this.userRepository = userRepository;  // Initialize the userRepository
    }

    @PostMapping("/add")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        // Extract the sender's email from the JWT token
        String senderEmail = extractAuthenticatedUserEmail();

        // Fetch the sender and receiver users from the database
        User sender = userRepository.findByEmail(senderEmail);
        User receiver = userRepository.findByEmail(friendRequestDto.getReceiverEmail());

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Sender or receiver not found.");
        }

        // Create the friend request
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderEmail(senderEmail);
        friendRequest.setReceiverEmail(friendRequestDto.getReceiverEmail());
        friendRequest.setStatus("PENDING");

        // Add the friend request to the sender's sentRequests list
        if (sender.getSentRequests() == null) {
            sender.setSentRequests(new ArrayList<>()); // Initialize if null
        }
        sender.getSentRequests().add(friendRequest);

        // Save the updated sender user in the database
        userRepository.save(sender);

        // Optionally, you can also send the request to the receiver's friend requests (if needed)
        if (receiver.getFriendRequests() == null) {
            receiver.setFriendRequests(new ArrayList<>());
        }
        receiver.getFriendRequests().add(friendRequest);
        userRepository.save(receiver);

        return ResponseEntity.ok("Friend request sent.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        // Extract the authenticated user's email from the JWT token
        String receiverEmail = extractAuthenticatedUserEmail();

        // Find the sender and receiver users in the database
        User sender = userRepository.findByEmail(friendRequestDto.getSenderEmail());
        User receiver = userRepository.findByEmail(receiverEmail);

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Sender or receiver not found.");
        }

        // Find the pending friend request in the receiver's friendRequests list
        FriendRequest request = receiver.getFriendRequests().stream()
                .filter(req -> req.getSenderEmail().equals(friendRequestDto.getSenderEmail()) && req.getStatus().equals("PENDING"))
                .findFirst()
                .orElse(null);

        if (request == null) {
            return ResponseEntity.badRequest().body("Friend request not found or already processed.");
        }

        // Update the request status to 'ACCEPTED'
        request.setStatus("ACCEPTED");

        // Add the sender to the receiver's friends list
        receiver.getFriends().add(sender.getEmail());
        sender.getFriends().add(receiver.getEmail());

        // Save the updated receiver and sender in the database
        userRepository.save(receiver);
        userRepository.save(sender);

        // Update the sent request status to 'ACCEPTED'
        sender.getSentRequests().stream()
                .filter(req -> req.getReceiverEmail().equals(receiverEmail) && req.getStatus().equals("PENDING"))
                .findFirst()
                .ifPresent(req -> req.setStatus("ACCEPTED"));

        // Save the updated sender's sentRequests
        userRepository.save(sender);

        return ResponseEntity.ok("Friend request accepted.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        // Extract the authenticated user's email from the JWT token
        String receiverEmail = extractAuthenticatedUserEmail();

        // Find the sender and receiver users in the database
        User sender = userRepository.findByEmail(friendRequestDto.getSenderEmail());
        User receiver = userRepository.findByEmail(receiverEmail);

        if (sender == null || receiver == null) {
            return ResponseEntity.badRequest().body("Sender or receiver not found.");
        }

        // Find the pending friend request in the receiver's friendRequests list
        FriendRequest request = receiver.getFriendRequests().stream()
                .filter(req -> req.getSenderEmail().equals(friendRequestDto.getSenderEmail()) && req.getStatus().equals("PENDING"))
                .findFirst()
                .orElse(null);

        if (request == null) {
            return ResponseEntity.badRequest().body("Friend request not found or already processed.");
        }

        // Update the request status to 'REJECTED'
        request.setStatus("REJECTED");

        // Save the updated receiver's friendRequests
        userRepository.save(receiver);

        // Update the sent request status to 'REJECTED'
        sender.getSentRequests().stream()
                .filter(req -> req.getReceiverEmail().equals(receiverEmail) && req.getStatus().equals("PENDING"))
                .findFirst()
                .ifPresent(req -> req.setStatus("REJECTED"));

        // Save the updated sender's sentRequests
        userRepository.save(sender);

        return ResponseEntity.ok("Friend request rejected.");
    }


    @GetMapping
    public ResponseEntity<List<String>> getFriends() {
        String userEmail = extractAuthenticatedUserEmail();
        List<String> friends = friendService.getFriends(userEmail);
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestDto>> getFriendRequests() {
        String receiverEmail = extractAuthenticatedUserEmail();
        List<FriendRequestDto> requests = friendService.getAllFriendRequests(receiverEmail);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/sent-requests")
    public ResponseEntity<List<FriendRequestDto>> getSentFriendRequests() {
        String senderEmail = extractAuthenticatedUserEmail();
        List<FriendRequestDto> sentRequests = friendService.getAllSentFriendRequests(senderEmail);
        return ResponseEntity.ok(sentRequests);
    }

    private String extractAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            return ((org.springframework.security.core.userdetails.User) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        } else {
            throw new RuntimeException("Unable to extract user email from security context");
        }
    }
}
