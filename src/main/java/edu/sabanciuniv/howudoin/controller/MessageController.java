package edu.sabanciuniv.howudoin.controller;

import edu.sabanciuniv.howudoin.model.Message;
import edu.sabanciuniv.howudoin.model.MessageDto;
import edu.sabanciuniv.howudoin.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Endpoint to send a message
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDto messageDto) {
        String userEmail1 = extractAuthenticatedUserEmail();
        messageService.sendMessage(userEmail1, messageDto.getReceiverEmail(), messageDto.getContent());
        return ResponseEntity.ok("Message sent successfully.");
    }

    // Endpoint to retrieve conversation history
    @GetMapping
    public ResponseEntity<List<Message>> getConversation(@RequestParam String userEmail2) {
        // Extract userEmail1 from the JWT token
        String userEmail1 = extractAuthenticatedUserEmail();

        // Fetch the conversation between the two users
        List<Message> conversation = messageService.getConversation(userEmail1, userEmail2);
        return ResponseEntity.ok(conversation);
    }

    // Utility method to extract authenticated user's email from JWT token
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
