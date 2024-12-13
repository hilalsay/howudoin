package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.FriendRequestDto;
import edu.sabanciuniv.howudoin.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        // Extract the sender's email from the JWT token
        String senderEmail = extractAuthenticatedUserEmail();

        // Use the extracted email instead of requiring it in the request body
        String message = friendService.sendFriendRequest(senderEmail, friendRequestDto.getReceiverEmail());
        return ResponseEntity.ok(message);
    }


    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        // Extract the authenticated user's email from the JWT token
        String receiverEmail = extractAuthenticatedUserEmail();

        // Use the extracted email as the receiver
        friendService.acceptFriendRequest(friendRequestDto.getSenderEmail(), receiverEmail);
        return ResponseEntity.ok("Friend request accepted.");
    }


    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        String receiverEmail = extractAuthenticatedUserEmail();

        // Use the extracted email as the receiver
        friendService.rejectFriendRequest(friendRequestDto.getSenderEmail(), receiverEmail);
        return ResponseEntity.ok("Friend request rejected.");
    }

    @GetMapping
    public ResponseEntity<List<String>> getFriends() {
        // Extract the authenticated user from the SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userEmail;


        if (principal instanceof org.springframework.security.core.userdetails.User) {
            userEmail = ((org.springframework.security.core.userdetails.User) principal).getUsername(); // Extract the username
        } else if (principal instanceof String) {
            userEmail = (String) principal;
        } else {
            throw new RuntimeException("Unable to extract user email from security context");
        }

        List<String> friends = friendService.getFriends(userEmail);
        return ResponseEntity.ok(friends);
    }
    @GetMapping("/requests")
    public ResponseEntity<List<FriendRequestDto>> getFriendRequests() {
        // Extract the authenticated user's email from the JWT token
        String receiverEmail = extractAuthenticatedUserEmail();
        List<FriendRequestDto> requests = friendService.getAllFriendRequests(receiverEmail);
        return ResponseEntity.ok(requests);
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
