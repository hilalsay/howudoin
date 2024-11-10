package edu.sabanciuniv.howudoin;

import org.springframework.http.ResponseEntity;
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
        String message = friendService.sendFriendRequest(friendRequestDto.getSenderEmail(), friendRequestDto.getReceiverEmail());
        return ResponseEntity.ok(message);
    }


    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        friendService.acceptFriendRequest(friendRequestDto.getSenderEmail(), friendRequestDto.getReceiverEmail());
        return ResponseEntity.ok("Friend request accepted.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestBody FriendRequestDto friendRequestDto) {
        friendService.rejectFriendRequest(friendRequestDto.getSenderEmail(), friendRequestDto.getReceiverEmail());
        return ResponseEntity.ok("Friend request rejected.");
    }

    @GetMapping
    public ResponseEntity<List<String>> getFriends(@RequestParam String userEmail) {
        List<String> friends = friendService.getFriends(userEmail);
        return ResponseEntity.ok(friends);
    }
}
