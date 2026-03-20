package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "conversations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String conversationId;

    @OneToMany(mappedBy = "conversation")
    @JsonManagedReference
    private List<Message> messages;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    private LocalDateTime createdAt;
}
