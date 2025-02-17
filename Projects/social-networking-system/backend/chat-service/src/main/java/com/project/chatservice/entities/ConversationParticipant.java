package com.project.chatservice.entities;

import com.project.chatservice.enums.ParticipantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long conversationParticipantId;

    @Column(name = "conversation_id")
    private long conversationId;

    @Column(name = "user_id")
    private long userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "participant_status")
    private ParticipantStatus participantStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    private Conversation conversation;
}
