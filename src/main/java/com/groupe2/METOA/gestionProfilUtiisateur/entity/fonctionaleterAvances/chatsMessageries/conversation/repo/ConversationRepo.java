package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConversationRepo extends JpaRepository<Conversation,String> {

    @Query("""
SELECT c FROM Conversation c
WHERE (c.user1.idUser = :u1 AND c.user2.idUser = :u2)
   OR (c.user1.idUser = :u2 AND c.user2.idUser = :u1)
""")
    Optional<Conversation> findConversation(String u1, String u2);

    @Query("""
SELECT c FROM Conversation c
WHERE c.user1.idUser = :userId
   OR c.user2.idUser = :userId
ORDER BY c.createdAt DESC
""")
    List<Conversation> findByUser(String userId);
}
