package com.project.chatservice.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String email;
    private String username;
    private String profilePicture;
    private String phone;
    private String fullName;
}
