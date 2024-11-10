package edu.sabanciuniv.howudoin;


public class FriendRequest {
    private String id;
    private String senderEmail; // ID of the user sending the request
    private String receiverEmail; // ID of the user receiving the request
    private String status; // pending, accepted, rejected

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

    public void setsenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    // Getter and Setter for receiverEmail
    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setreceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
