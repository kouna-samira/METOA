package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InboxDTO {
    private String conversationId;
    private String otherUserId;
    private String lastMessage;
    private LocalDateTime lastMessageDate;
    private long unreadCount;
}