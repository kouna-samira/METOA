package com.groupe2.METOA.gestionProfilUtiisateur.service.message;



import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    Message sendMessage(String senderId, String receiverId, String content);
    List<Message> getMessages(String conversationId);
    void markConversationAsRead(String conversationId, String userId);
    long countUnreadMessages(String conversationId, String userId);
    Page<Message> getMessagesPaginated(String conversationId, int page, int size);
}
