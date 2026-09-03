package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.repo.ConversationRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.repo.MessageRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.awt.*;

import static org.hibernate.type.StandardBasicTypes.IMAGE;

@Service
public class InboxServiceImpl implements InboxService {

    private final ConversationRepo conversationRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepo messageRepo;

    public InboxServiceImpl(ConversationRepo conversationRepo, SimpMessagingTemplate messagingTemplate, MessageRepo messageRepo) {
        this.conversationRepo = conversationRepo;
        this.messagingTemplate = messagingTemplate;
        this.messageRepo = messageRepo;
    }

    @Override
    public Page<InboxDTO> getInbox(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return conversationRepo.getInbox(userId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public InboxDTO getSingleInbox(String userId, String conversationId) {
        InboxProjection projection = conversationRepo.getSingleInboxForUser(userId, conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation non trouvée pour cet utilisateur"));
        return mapToDTO(projection);
    }

    private InboxDTO mapToDTO(InboxProjection p) {
        return InboxDTO.builder()
                .conversationId(p.getConversationId())
                .otherUserId(p.getOtherUserId())
                .otherUsername(p.getOtherUsername())
                .otherUserPhoto(p.getOtherPhoto())
                .lastMessage(p.getLastMessage())
                .lastMessageDate(p.getLastMessageDate())
                .unreadCount(p.getUnreadCount())
                .build();
    }

    @Override
    public void notifyInboxUpdate(MessageResDTO message) {
        // 1. Notifier le destinataire (avec son nombre de messages non lus)
        long unreadForReceiver = messageRepo.countUnreadMessages(
                message.getConversationId(),
                message.getReceiverId()
        );

        InboxDTO receiverInboxDTO = InboxDTO.builder()
                .conversationId(message.getConversationId())
                .otherUserId(message.getSenderId())
                .lastMessage(buildPreviewText(message))
                .lastMessageDate(message.getTimestamp())
                .unreadCount((int) unreadForReceiver)
                .build();

        messagingTemplate.convertAndSendToUser(
                message.getReceiverId(),
                "/queue/inbox",
                receiverInboxDTO
        );

        // 2. Notifier l'expéditeur (avec unreadCount = 0 pour cette conversation)
        InboxDTO senderInboxDTO = InboxDTO.builder()
                .conversationId(message.getConversationId())
                .otherUserId(message.getReceiverId())
                .lastMessage(buildPreviewText(message))
                .lastMessageDate(message.getTimestamp())
                .unreadCount(0)
                .build();

        messagingTemplate.convertAndSendToUser(
                message.getSenderId(),
                "/queue/inbox",
                senderInboxDTO
        );
    }

    private String buildPreviewText(MessageResDTO message) {
        if (message == null || message.getType() == null) {
            return "";
        }

        switch (message.getType()) {
            case IMAGE:
                return "📷 Photo";

            case AUDIO:

                return "🎤 Message vocal";


            case FILE:
                // Si le nom du fichier est dans l'attachment, on l'affiche, sinon le contenu
                if (message.getAttachment() != null && message.getAttachment().getFileName() != null) {
                    return "📄 Fichier : " + message.getAttachment().getFileName();
                }
                return "📄 Fichier joint";

            case TEXT:
            default:
                // Pour du texte, on retourne le contenu du message
                return message.getContent() != null ? message.getContent() : "";
        }
    }
}