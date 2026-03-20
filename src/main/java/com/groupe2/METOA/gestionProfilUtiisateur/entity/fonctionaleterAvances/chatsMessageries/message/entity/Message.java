package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
@Entity
@Table(name = "messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String messageId;

    @ManyToOne
    @JsonBackReference
    private Conversation conversation;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    private LocalDateTime sentAt;
    private boolean edited;

    private boolean deleted;

    private LocalDateTime readAt;
}
