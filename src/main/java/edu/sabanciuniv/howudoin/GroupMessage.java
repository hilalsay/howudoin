package edu.sabanciuniv.howudoin;

import java.util.Date;

public class GroupMessage {
    private String senderEmail;
    private String content;
    private Date timestamp;

    public GroupMessage(String senderEmail, String content) {
        this.senderEmail = senderEmail;
        this.content = content;
        this.timestamp = new Date();
    }

    // Getters and Setters
    public String getSenderEmail() { return senderEmail; }
    public void setSenderEmail(String senderEmail) { this.senderEmail = senderEmail; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
