package com.groupe2.METOA.gestionProfilUtiisateur.service.conversation;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.Conversation;

public interface ConversationService {
    Conversation createConversation(String senderId, String receiverId);
}
