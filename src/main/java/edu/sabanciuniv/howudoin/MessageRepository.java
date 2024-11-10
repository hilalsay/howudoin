package edu.sabanciuniv.howudoin;

//import edu.sabanciuniv.howudoin.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderEmailAndReceiverEmailOrReceiverEmailAndSenderEmailOrderByTimestamp(
            String senderEmail1, String receiverEmail1, String receiverEmail2, String senderEmail2);
}
