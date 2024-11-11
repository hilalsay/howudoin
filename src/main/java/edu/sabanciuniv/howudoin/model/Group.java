package edu.sabanciuniv.howudoin.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "groups")
public class Group {
    @Id
    private String id; // MongoDB will auto-generate this ID
    private String name;
    private List<String> members;
    private List<GroupMessage> messages;

    public Group() {
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public Group(String name, List<String> members) {
        this.name = name;
        this.members = members != null ? members : new ArrayList<>();
        this.messages = new ArrayList<>();
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

    // Method to add a message to the group
    public void addMessage(String senderEmail, String content) {
        this.messages.add(new GroupMessage(senderEmail, content));
    }
}
