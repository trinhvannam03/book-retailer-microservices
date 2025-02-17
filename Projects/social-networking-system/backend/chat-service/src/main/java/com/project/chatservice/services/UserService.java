package com.project.chatservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chatservice.dto.UserDTO;
import com.project.chatservice.entities.User;
import com.project.chatservice.repositories.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Data
public class UserService {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void captureUserChange(String message) throws RuntimeException {
        UserDTO userDTO = objectMapper.convertValue(message, UserDTO.class);
        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .userId(userDTO.getUserId())
                .profilePicture(userDTO.getProfilePicture())
                .fullName(userDTO.getFullName())
                .phone(userDTO.getPhone())
                .build();
        userRepository.save(user);
    }
}
