package edu.sabanciuniv.howudoin.model;

public class FriendRequestDto {
    private String senderEmail;
    private String receiverEmail;
    private String status;

    public FriendRequestDto(String senderEmail, String receiverEmail, String status) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
