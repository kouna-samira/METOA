package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDTO {

    private String id;

    private String otherUserId;
    private String otherUsername;
    private String otherUserPhoto;

    private String lastMessage;
    private LocalDateTime lastMessageDate;

    private long unreadCount;
}