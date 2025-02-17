package com.project.chatservice.services;

import com.project.chatservice.dto.ConversationDTO;
import com.project.chatservice.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {
    public List<ConversationDTO> findAllConversations(Long userId) {
        List<ConversationDTO> conversations = new ArrayList<>();
        return conversations;
    }

    public List<UserDTO> findActiveUsers() {
        List<UserDTO> users = new ArrayList<>();
        return users;
    }
    
}
