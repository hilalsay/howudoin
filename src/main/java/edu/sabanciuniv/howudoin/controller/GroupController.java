package edu.sabanciuniv.howudoin.controller;


import edu.sabanciuniv.howudoin.model.Group;
import edu.sabanciuniv.howudoin.model.GroupMessage;
import edu.sabanciuniv.howudoin.model.User;
import edu.sabanciuniv.howudoin.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // Endpoint to create a new group
    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        String senderEmail = extractAuthenticatedUserEmail();
        group.getMembers().add(senderEmail);
        Group createdGroup = groupService.createGroup(group.getName(), group.getMembers());
        return ResponseEntity.ok(createdGroup);
    }
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<String> addMember(@PathVariable String groupId, @RequestParam String memberEmail) {
        groupService.addMember(groupId, memberEmail);
        return ResponseEntity.ok("User added to the group.");
    }

    // Endpoint to send a message to a group
    @PostMapping("/{groupId}/send")
    public ResponseEntity<String> sendMessage(@PathVariable String groupId, @RequestParam String content) {
        // Extract the logged-in user's email from the JWT token
        String senderEmail = extractAuthenticatedUserEmail();

        // Pass the extracted sender email along with the group ID and content to the service
        groupService.sendMessage(groupId, senderEmail, content);
        return ResponseEntity.ok("Message sent to group.");
    }

    // Utility method to extract authenticated user's email
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


    // Endpoint to retrieve group message history
    @GetMapping("/{groupId}/messages")
    public ResponseEntity<List<GroupMessage>> getMessages(@PathVariable String groupId) {
        List<GroupMessage> messages = groupService.getMessages(groupId);
        return ResponseEntity.ok(messages);
    }

    // Endpoint to retrieve group members
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<String>> getMembers(@PathVariable String groupId) {
        List<String> members = groupService.getMembers(groupId);
        return ResponseEntity.ok(members);
    }
}
