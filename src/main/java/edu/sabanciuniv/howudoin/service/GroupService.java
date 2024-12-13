package edu.sabanciuniv.howudoin.service;

import edu.sabanciuniv.howudoin.model.Group;
import edu.sabanciuniv.howudoin.model.GroupMessage;
import edu.sabanciuniv.howudoin.repository.GroupRepository;
import edu.sabanciuniv.howudoin.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupRepository groupRepository;


    private final UserRepository userRepository;


    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    // Create a new group
    public Group createGroup(String name, List<String> members) {
        Group group = new Group(name, members);
        return groupRepository.save(group);
    }

    // Add a member to an existing group
    public void addMember(String groupId, String memberEmail) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Check if the user is registered
        if (userRepository.findByEmail(memberEmail) == null) {
            throw new IllegalArgumentException("User with email " + memberEmail + " is not registered");
        }

        if (!group.getMembers().contains(memberEmail)) {
            group.getMembers().add(memberEmail);
            groupRepository.save(group);
        }

    }

    // Send a message to a group
    public void sendMessage(String groupId, String senderEmail, String content) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));
        if (!group.getMembers().contains(senderEmail)) {
            throw new IllegalArgumentException("Sender is not a member of the group");
        }



        group.addMessage(senderEmail, content);
        groupRepository.save(group); // Save the group with the new message
    }

    // Retrieve message history for a group
    public List<GroupMessage> getMessages(String groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return group.getMessages();
    }

    // Retrieve list of members in a group
    public List<String> getMembers(String groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Group not found"));
        return group.getMembers();
    }
    public List<Group> getGroupsByUserEmail(String userEmail) {
        return groupRepository.findAllByMembersContaining(userEmail);
    }


}
