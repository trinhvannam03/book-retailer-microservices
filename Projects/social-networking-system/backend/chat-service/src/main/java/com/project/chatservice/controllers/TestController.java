package com.project.chatservice.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.chatservice.dto.MessageDTO;
import com.project.chatservice.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final MessageService messageService;

    @PostMapping("/")
    public ResponseEntity<MessageDTO> test(@RequestBody MessageDTO message) throws JsonProcessingException {
        messageService.emitMessage(message);
        return ResponseEntity.ok(message);
    }
}
