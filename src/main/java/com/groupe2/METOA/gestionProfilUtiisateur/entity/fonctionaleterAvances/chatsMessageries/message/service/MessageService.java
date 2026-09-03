package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service;



import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    // envoyer un message
    Message sendMessage(String senderId, String receiverId, String content);

    // récupérer tous les messages d'une conversation
    List<MessageResDTO> getMessages(String conversationId);

    // marquer comme lus
    void markConversationAsRead(String conversationId, String userId);

    // nombre de messages non lus
    long countUnreadMessages(String conversationId, String userId);

    // pagination
    Page<MessageResDTO> getMessagesPaginated(String conversationId, int page, int size);

    // ✨ BONUS PRO
    Message editMessage(String messageId, String content);

    void deleteMessage(String messageId);

    Message markAsDelivered(String messageId);

    MessageResDTO saveMessage(MessageReqDTO dto);
}
