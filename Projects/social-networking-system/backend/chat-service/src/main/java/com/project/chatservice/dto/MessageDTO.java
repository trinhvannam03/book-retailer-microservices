package com.project.chatservice.dto;

import com.project.chatservice.enums.ContentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp;
    private ContentType contentType;
}
