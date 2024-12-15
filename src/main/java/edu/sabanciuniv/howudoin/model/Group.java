package edu.sabanciuniv.howudoin.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "groups")
public class Group {
    @Id
    private String id; // MongoDB will auto-generate this ID
    private String name;
    private List<String> members;
    private List<GroupMessage> messages;


    @CreatedDate
    private Instant createdAt; // Field to store creation time


    public Group() {
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.createdAt = Instant.now(); // Set creation time to current time
    }

    public Group(String name, List<String> members) {
        this.name = name;
        this.members = members != null ? members : new ArrayList<>();
        this.messages = new ArrayList<>();
        this.createdAt = Instant.now(); // Set creation time to current time
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }

    public List<GroupMessage> getMessages() { return messages; }
    public void setMessages(List<GroupMessage> messages) { this.messages = messages; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    // Method to add a message to the group
    public void addMessage(String senderEmail, String content) {
        this.messages.add(new GroupMessage(senderEmail, content));
    }
}
