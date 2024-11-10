package edu.sabanciuniv.howudoin;

//import edu.sabanciuniv.howudoin.model.Message;
//import edu.sabanciuniv.howudoin.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // Method to send a message
    public void sendMessage(String senderEmail, String receiverEmail, String content) {
        Message message = new Message(senderEmail, receiverEmail, content);
        messageRepository.save(message);
    }

    // Method to retrieve conversation history between two users
    public List<Message> getConversation(String userEmail1, String userEmail2) {
        return messageRepository.findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestamp(
                userEmail1, userEmail2, userEmail1, userEmail2);
    }
}
