package edu.sabanciuniv.howudoin.controller;

//import edu.sabanciuniv.howudoin.model.Message;
//import edu.sabanciuniv.howudoin.service.MessageService;
import edu.sabanciuniv.howudoin.model.Message;
import edu.sabanciuniv.howudoin.model.MessageDto;
import edu.sabanciuniv.howudoin.service.MessageService;
import org.springframework.http.ResponseEntity;
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
        messageService.sendMessage(messageDto.getSenderEmail(), messageDto.getReceiverEmail(), messageDto.getContent());
        return ResponseEntity.ok("Message sent successfully.");
    }

    // Endpoint to retrieve conversation history
    @GetMapping
    public ResponseEntity<List<Message>> getConversation(@RequestParam String userEmail1, @RequestParam String userEmail2) {
        List<Message> conversation = messageService.getConversation(userEmail1, userEmail2);
        return ResponseEntity.ok(conversation);
    }
}
