package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.dto.ConversationDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final UserRepo userRepo;
    private final ConversationRepo conversationRepo;

    public ConversationServiceImpl(UserRepo userRepo, ConversationRepo conversationRepo) {
        this.userRepo = userRepo;
        this.conversationRepo = conversationRepo;
    }

    @Override
    @Transactional
    public Conversation createConversation(String senderId, String receiverId) {
        // Double vérification pour éviter la création en double
        return conversationRepo.findConversation(senderId, receiverId)
                .orElseGet(() -> {
                    User sender = userRepo.findById(senderId)
                            .orElseThrow(() -> new UserNoteFoundException("Expéditeur introuvable"));
                    User receiver = userRepo.findById(receiverId)
                            .orElseThrow(() -> new UserNoteFoundException("Récepteur introuvable"));

                    Conversation conversation = Conversation.builder()
                            .user1(sender)
                            .user2(receiver)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return conversationRepo.save(conversation);
                });
    }

    @Override
    public List<ConversationDTO> getAllConversationsByUser(String userId) { // ou le nom de votre méthode
        List<Conversation> conversations = conversationRepo.findByUserWithMessages(userId);

        return conversations.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ConversationDTO mapToDTO(Conversation conversation) {
        ConversationDTO dto = new ConversationDTO();
        dto.setId(conversation.getConversationId());
        // Mappez ici les autres champs (user1, user2, messages, etc.)
        return dto;
    }
}