package com.project.chatservice.controllers;

import com.project.chatservice.entities.Message;
import com.project.chatservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void sendMessage(@Payload Message message) {
        messageService.sendMessage(message);
        messageService.logSubscriptions();
    }
}
