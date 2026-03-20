package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.repo.MessageRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service.ConversationServiceImpl;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Builder
public class MessageServiceImpl implements MessageService{

    private final ConversationRepo conversationRepo;
    private final UserRepo userRepo;
    private final MessageRepo messageRepo;
    private final ConversationServiceImpl conversationService;

    public MessageServiceImpl(ConversationRepo conversationRepo, UserRepo userRepo, MessageRepo messageRepo, ConversationServiceImpl conversationService) {
        this.conversationRepo = conversationRepo;

        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
        this.conversationService = conversationService;
    }

    @Override
    public Message sendMessage(String senderId, String receiverId, String content) {
        User sender = userRepo.findById(senderId)
                .orElseThrow(() -> new UserNoteFoundException("Expéditeur introuvable"));

        User receiver = userRepo.findById(receiverId)
                .orElseThrow(() -> new UserNoteFoundException("Recepteur introuvable"));

        Conversation conversation = conversationRepo
                .findConversation(senderId, receiverId)
                .orElseGet(() -> conversationService.createConversation(senderId, receiverId));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .conversation(conversation)
                .content(content)
                .status(MessageStatus.ENVOYE)
                .sentAt(LocalDateTime.now())
                .build();

        return messageRepo.save(message);
    }

    @Override
    public List<Message> getMessages(String conversationId) {
        return messageRepo.findByConversation_ConversationIdOrderBySentAtAsc(conversationId);
    }

    @Override
    @Transactional
    public void markConversationAsRead(String conversationId, String userId) {

        messageRepo.markMessagesAsRead(conversationId, userId);
    }

    @Override
    public long countUnreadMessages(String conversationId, String userId) {
        return  messageRepo.countByConversation_ConversationIdAndReceiver_IdUserAndStatus(
                conversationId,
                userId,
                MessageStatus.ENVOYE
        );
    }

    @Override
    public Page<Message> getMessagesPaginated(String conversationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return messageRepo
                .findByConversation_ConversationIdOrderBySentAtAsc(conversationId, pageable);

    }
    @Override
    @Transactional
    public Message editMessage(String messageId, String content) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message introuvable"));

        message.setContent(content);
        message.setEdited(true);

        return messageRepo.save(message);
    }

    @Override
    @Transactional
    public void deleteMessage(String messageId) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message introuvable"));

        message.setDeleted(true);
        message.setContent("Message supprimé");

        messageRepo.save(message);
    }
}
