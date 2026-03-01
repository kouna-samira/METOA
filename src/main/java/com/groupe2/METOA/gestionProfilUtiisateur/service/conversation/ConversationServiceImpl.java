package com.groupe2.METOA.gestionProfilUtiisateur.service.conversation;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
@Builder
public class ConversationServiceImpl implements ConversationService{
    private  final UserRepo userRepo;
    private final ConversationRepo conversationRepo;

    public ConversationServiceImpl(UserRepo userRepo, ConversationRepo conversationRepo) {
        this.userRepo = userRepo;
        this.conversationRepo = conversationRepo;
    }

    @Override
    public Conversation createConversation(String senderId, String receiverId) {
        User sender = userRepo.findById(senderId).orElseThrow(()->new RuntimeException("expediteur introuvable"));
        User receiver = userRepo.findById(receiverId).orElseThrow(()->new RuntimeException("recepteur introuvable"));

        Conversation conversation = Conversation.builder()
                .user1(sender)
                .user2(receiver)
                .createdAt(LocalDateTime.now())
                .build();

        return this.conversationRepo.save(conversation);
    }
}
