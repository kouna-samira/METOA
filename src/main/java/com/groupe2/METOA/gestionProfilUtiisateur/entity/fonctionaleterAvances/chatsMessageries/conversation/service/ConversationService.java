package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.dto.ConversationDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation createConversation(String senderId, String receiverId);

    List<ConversationDTO> getAllConversationsByUser(String userId);
}
