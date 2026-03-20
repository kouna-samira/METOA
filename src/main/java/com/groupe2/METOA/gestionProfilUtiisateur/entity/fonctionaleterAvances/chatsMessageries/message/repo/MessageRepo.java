package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, String> {
    List<Message> findByConversation_ConversationIdOrderBySentAtAsc(String conversationId);
    @Modifying
    @Query("""
   UPDATE Message m
   SET m.status = 'LU'
   WHERE m.conversation.conversationId = :conversationId
   AND m.receiver.idUser = :userId
   AND m.status = 'ENVOYE'
""")
    void markMessagesAsRead(String conversationId, String userId);
    long countByConversation_ConversationIdAndReceiver_IdUserAndStatus(
            String conversationId,
            String userId,
            MessageStatus status
    );
    Page<Message> findByConversation_ConversationIdOrderBySentAtAsc(
            String conversationId,
            Pageable pageable
    );

}
