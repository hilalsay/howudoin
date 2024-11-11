package edu.sabanciuniv.howudoin.model;

public class FriendRequestDto {
    private String senderEmail;
    private String receiverEmail;

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
}
