package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox.InboxProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation, String> {

    @Query("""
        SELECT c FROM Conversation c
        WHERE (c.user1.idUser = :u1 AND c.user2.idUser = :u2)
           OR (c.user1.idUser = :u2 AND c.user2.idUser = :u1)
        """)
    Optional<Conversation> findConversation(@Param("u1") String u1, @Param("u2") String u2);

    @Query("""
        SELECT c FROM Conversation c
        WHERE c.user1.idUser = :userId
           OR c.user2.idUser = :userId
        ORDER BY c.createdAt DESC
        """)
    List<Conversation> findByUser(@Param("userId") String userId);

    // CORRECTION 1: Remplacement du type de retour par List<Conversation> (car FETCH JOIN charge les entités)
    @Query("""
        SELECT DISTINCT c FROM Conversation c
        LEFT JOIN FETCH c.messages m
        LEFT JOIN FETCH c.user1
        LEFT JOIN FETCH c.user2
        WHERE c.user1.idUser = :userId OR c.user2.idUser = :userId
        """)
    List<Conversation> findByUserWithMessages(@Param("userId") String userId);

    // CORRECTION 2: Optimisation des requêtes de projection
    @Query(value = """
        SELECT
            c.conversation_id AS conversationId,
            CASE
                WHEN c.user1_id = :userId THEN c.user2_id
                ELSE c.user1_id
            END AS otherUserId,
            CASE
                WHEN c.user1_id = :userId THEN u2.user_name
                ELSE u1.user_name
            END AS otherUsername,
            CASE
                WHEN c.user1_id = :userId THEN u2.photo_url
                ELSE u1.photo_url
            END AS otherPhoto,
            (
                SELECT m.content FROM message m
                WHERE m.conversation_id = c.conversation_id
                ORDER BY m.sent_at DESC
                LIMIT 1
            ) AS lastMessage,
            (
                SELECT MAX(m.sent_at) FROM message m
                WHERE m.conversation_id = c.conversation_id
            ) AS lastMessageDate,
            (
                SELECT COUNT(m.message_id) FROM message m
                WHERE m.conversation_id = c.conversation_id
                  AND m.is_read = false
                  AND m.receiver_id = :userId
            ) AS unreadCount
        FROM conversation c
        LEFT JOIN user u1 ON c.user1_id = u1.id_user
        LEFT JOIN user u2 ON c.user2_id = u2.id_user
        WHERE c.user1_id = :userId OR c.user2_id = :userId
        """,
            countQuery = "SELECT COUNT(*) FROM conversation c WHERE c.user1_id = :userId OR c.user2_id = :userId",
            nativeQuery = true)
    Page<InboxProjection> getInbox(@Param("userId") String userId, Pageable pageable);
    @Query(value = """
        SELECT
            c.conversation_id AS conversationId,
            CASE
                WHEN c.user1_id = :userId THEN c.user2_id
                ELSE c.user1_id
            END AS otherUserId,
            CASE
                WHEN c.user1_id = :userId THEN u2.user_name
                ELSE u1.user_name
            END AS otherUsername,
            CASE
                WHEN c.user1_id = :userId THEN u2.photo_url
                ELSE u1.photo_url
            END AS otherPhoto,
            (
                SELECT m.content FROM message m
                WHERE m.conversation_id = c.conversation_id
                ORDER BY m.sent_at DESC
                LIMIT 1
            ) AS lastMessage,
            (
                SELECT MAX(m.sent_at) FROM message m
                WHERE m.conversation_id = c.conversation_id
            ) AS lastMessageDate,
            (
                SELECT COUNT(m.message_id) FROM message m
                WHERE m.conversation_id = c.conversation_id
                  AND m.is_read = false
                  AND m.receiver_id = :userId
            ) AS unreadCount
        FROM conversation c
        LEFT JOIN user u1 ON c.user1_id = u1.id_user
        LEFT JOIN user u2 ON c.user2_id = u2.id_user
        WHERE c.conversation_id = :conversationId
          AND (c.user1_id = :userId OR c.user2_id = :userId)
        """, nativeQuery = true)
    Optional<InboxProjection> getSingleInboxForUser(@Param("userId") String userId, @Param("conversationId") String conversationId);}