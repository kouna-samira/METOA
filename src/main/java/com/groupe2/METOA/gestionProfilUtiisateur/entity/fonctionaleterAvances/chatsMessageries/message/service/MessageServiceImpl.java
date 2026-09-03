package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.AttachmentDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Attachment;
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

import static co.elastic.clients.elasticsearch.ingest.Processor.Kind.Attachment;

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
    public List<MessageResDTO> getMessages(String conversationId) {

        return messageRepo.findByConversation_ConversationIdOrderBySentAtAsc(conversationId);
    }

    @Override
    @Transactional
    public void markConversationAsRead(String conversationId, String userId) {

        messageRepo.markMessagesAsRead(conversationId, userId);
    }

    @Override
    public long countUnreadMessages(String conversationId, String userId) {
        // Compte tous les messages qui ne sont PAS encore marqués comme LU
        return messageRepo.countByConversation_ConversationIdAndReceiver_IdUserAndStatusNot(
                conversationId,
                userId,
                MessageStatus.LU
        );
    }

    @Override
    public Page<MessageResDTO> getMessagesPaginated(String conversationId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return messageRepo
                .findByConversationConversationIdOrderBySentAtDesc(conversationId, pageable);

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
    @Override
    @Transactional
    public Message markAsDelivered(String messageId) {
        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message introuvable"));

        // Ne mettre à jour que si le message n'est pas déjà LU
        if (message.getStatus() == MessageStatus.ENVOYE) {
            message.setStatus(MessageStatus.DELIVRE);
            return messageRepo.save(message);
        }

        return message;
    }

    @Override
    @Transactional
    public MessageResDTO saveMessage(MessageReqDTO dto) {
        // 1. Créer l'entité Message
        Message message = new Message();
        message.setConversationId(dto.getConversationId());
        message.setSenderId(dto.getSenderId());
        message.setReceiverId(dto.getReceiverId());
        message.setContent(dto.getContent());
        message.setType(dto.getType());
        message.setStatus(MessageStatus.ENVOYE);
        message.setTimestamp(LocalDateTime.now());

        // 2. Associer les métadonnées de la pièce jointe si présente
        if (dto.getAttachment() != null) {
            Attachment attachment = new Attachment();
            attachment.setFileUrl(dto.getAttachment().getFileUrl());
            attachment.setFileName(dto.getAttachment().getFileName());
            attachment.setFileType(dto.getAttachment().getFileType());
            attachment.setFileSize(dto.getAttachment().getFileSize());

            message.setAttachment(attachment);
        }

        // 3. Sauvegarder en BDD
        Message savedMessage = messageRepo.save(message);

        // 4. Convertir et retourner le DTO de réponse
        return mapToResDTO(savedMessage);
    }
    private AttachmentDTO mapAttachmentDTO(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        return AttachmentDTO.builder()
                .fileUrl(attachment.getFileUrl())
                .fileName(attachment.getFileName())
                .fileType(attachment.getFileType())
                .fileSize(attachment.getFileSize())
                .build();
    }

    private MessageResDTO mapToResDTO(Message entity) {
        // Logique de conversion Entity -> DTO (ou via MapStruct)
        return MessageResDTO.builder()
                .messageId(entity.getId())
                .conversationId(entity.getConversationId())
                .senderId(entity.getSenderId())
                .receiverId(entity.getReceiverId())
                .content(entity.getContent())
                .type(entity.getType())
                .status(entity.getStatus())
                .timestamp(entity.getTimestamp())
                .attachment(entity.getAttachment() != null ? mapAttachmentDTO(entity.getAttachment()) : null)
                .build();
    }

}
