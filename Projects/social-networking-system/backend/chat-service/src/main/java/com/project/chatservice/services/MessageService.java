package com.project.chatservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chatservice.dto.MessageDTO;
import com.project.chatservice.entities.Message;
import com.project.chatservice.repositories.MessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Set;

/*
 * The two users having the same subscription path (/user/queue/messages) is
 * EXPECTED BEHAVIOR in a Spring WebSocket application when using user-specific destinations (/user).
 * This is because the @EnableWebSocketMessageBroker configuration ensures that each user gets
 * a unique session and their messages are delivered to the correct user-specific queue.
 * Active WebSocket Subscriptions:
    User: 11217567
    Session: 45221gnd
    Subscription: /user/queue/messages
    *
    User: 11214150
    Session: k0okauag
    Subscription: /user/queue/messages
 */
@Service
@RequiredArgsConstructor
@Data
public class MessageService {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final SimpUserRegistry simpUserRegistry;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void resolveAndForwardMessage(String message) {
        //query all sessions and subscription available to send messages to
    }


    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println("User subscribed to: " + headers.getDestination());
        System.out.println("Session ID: " + headers.getSessionId());
    }


    public void emitMessage(MessageDTO messageDTO) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(messageDTO);
        kafkaTemplate.send("ws_messages", message);
    }

    public void sendMessage(Message message) {
        System.out.println("Preparing to send message to " + message.getReceiverId());
        messagingTemplate.convertAndSendToUser(message.getReceiverId().toString(), "/queue/messages", message);
        System.out.println("Message sent to " + message.getReceiverId());
    }


    public void logSubscriptions() {
        Set<SimpUser> users = simpUserRegistry.getUsers();

        simpUserRegistry.getUsers().forEach(user -> {
            System.out.println("User: " + user.getName());
            user.getSessions().forEach(session -> {
                System.out.println("  Session: " + session.getId());
                session.getSubscriptions().forEach(subscription -> {
                    System.out.println("    Subscription: " + subscription.getDestination());
                });
            });
        });
    }
}

