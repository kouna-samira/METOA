package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, String> {
    List<MessageResDTO> findByConversation_ConversationIdOrderBySentAtAsc(String conversationId);

    @Modifying
    @Query("""
       UPDATE Message m
       SET m.status = com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus.LU,
           m.readAt = CURRENT_TIMESTAMP
       WHERE m.conversation.conversationId = :conversationId
       AND m.receiver.idUser = :userId
       AND m.status != com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus.LU
    """)
    void markMessagesAsRead(@Param("conversationId") String conversationId, @Param("userId") String userId);

    long countByConversation_ConversationIdAndReceiver_IdUserAndStatusNot(
            String conversationId,
            String userId,
            MessageStatus status
    );

    Page<Message> findByConversation_ConversationIdOrderBySentAtAsc(
            String conversationId,
            Pageable pageable
    );

    Page<MessageResDTO> findByConversationConversationIdOrderBySentAtDesc(
            String conversationId,
            Pageable pageable
    );

    /**
     * Compte le nombre de messages reçus non lus dans une conversation donnée.
     *
     * @param conversationId L'identifiant de la conversation
     * @param receiverId     L'identifiant de l'utilisateur destinataire
     * @return Le nombre de messages non lus
     */
    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.conversationId = :conversationId " +
            "AND m.receiverId = :receiverId " +
            "AND m.status != 'LU'")
    long countUnreadMessages(
            @Param("conversationId") String conversationId,
            @Param("receiverId") String receiverId
    );

    /**
     * Méthode optionnelle : Marquer tous les messages reçus comme LU en BDD
     */
    @Query("SELECT m FROM Message m " +
            "WHERE m.conversationId = :conversationId " +
            "AND m.receiverId = :receiverId " +
            "AND m.status != 'LU'")
    List<Message> findUnreadMessagesInConversation(
            @Param("conversationId") String conversationId,
            @Param("receiverId") String receiverId
    );

}
