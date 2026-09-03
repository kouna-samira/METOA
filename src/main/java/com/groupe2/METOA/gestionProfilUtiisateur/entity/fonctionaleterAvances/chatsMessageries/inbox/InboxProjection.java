package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import java.time.LocalDateTime;

public interface InboxProjection {

    String getConversationId();

    String getOtherUserId();
    String getOtherUsername();
    String getOtherPhoto();

    String getLastMessage();
    LocalDateTime getLastMessageDate();

    long getUnreadCount();
}