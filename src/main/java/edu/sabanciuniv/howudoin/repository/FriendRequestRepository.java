package edu.sabanciuniv.howudoin.repository;

import edu.sabanciuniv.howudoin.model.FriendRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendRequestRepository extends MongoRepository<FriendRequest, String> {
    List<FriendRequest> findByReceiverEmailAndStatus(String receiverEmail, String status);
    List<FriendRequest> findByReceiverEmail(String receiverEmail);

    List<FriendRequest> findBySenderEmail(String senderEmail);
}

