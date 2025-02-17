package com.project.chatservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "conversation_type")
    private ConversationType conversationType;

    @OneToMany(mappedBy = "conversation")
    private List<ConversationParticipant> participants;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    public enum ConversationType {
        PRIVATE,
        GROUP
    }
}


