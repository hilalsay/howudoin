package edu.sabanciuniv.howudoin.model;


public class FriendRequest {
    private String id;
    private String senderEmail; // ID of the user sending the request
    private String receiverEmail; // ID of the user receiving the request
    private String status; // pending, accepted, rejected

    public FriendRequest(String senderEmail, String receiverEmail, String status) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.status = status; // Set the status (e.g., "PENDING")
    }

    public FriendRequest() {

    }

    // Getter and Setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for senderEmail
    public String getSenderEmail() {
        return senderEmail;
    }

    // Getter and Setter for receiverEmail
    public String getReceiverEmail() {
        return receiverEmail;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }
}
