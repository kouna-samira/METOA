package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        User sender = userRepo.findById(senderId).orElseThrow(()->new UserNoteFoundException("expediteur introuvable"));
        User receiver = userRepo.findById(receiverId).orElseThrow(()->new UserNoteFoundException("recepteur introuvable"));

        Conversation conversation = Conversation.builder()
                .user1(sender)
                .user2(receiver)
                .createdAt(LocalDateTime.now())
                .build();

        return this.conversationRepo.save(conversation);
    }

    @Override
    public List<Conversation> getAllConversationsByUser(String userId) {

        return conversationRepo.findByUser(userId);
    }
}
