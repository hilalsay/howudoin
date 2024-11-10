package edu.sabanciuniv.howudoin;

//import edu.sabanciuniv.howudoin.model.Group;
//import edu.sabanciuniv.howudoin.model.GroupMessage;
//import edu.sabanciuniv.howudoin.service.GroupService;
import org.springframework.http.ResponseEntity;
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
        Group createdGroup = groupService.createGroup(group.getName(), group.getMembers());
        return ResponseEntity.ok(createdGroup);
    }

    // Endpoint to add a member to an existing group
    @PostMapping("/{groupId}/add-member")
    public ResponseEntity<String> addMember(@PathVariable String groupId, @RequestParam String memberEmail) {
        groupService.addMember(groupId, memberEmail);
        return ResponseEntity.ok("Member added to group.");
    }

    // Endpoint to send a message to a group
    @PostMapping("/{groupId}/send")
    public ResponseEntity<String> sendMessage(@PathVariable String groupId, @RequestParam String senderEmail, @RequestParam String content) {
        groupService.sendMessage(groupId, senderEmail, content);
        return ResponseEntity.ok("Message sent to group.");
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
