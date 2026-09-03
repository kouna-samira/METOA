package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.webSocket;

import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.MessageMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox.InboxDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.inbox.InboxService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.MessageAckDTO.MessageAckDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.MessageAckDTO.MessageStatusUpdateDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.notification.NotificationService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.notification.NotificationType;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service.MessageService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final UserRepo userRepo;
    private final InboxService inboxService;

    public ChatWebSocketController(
            MessageService messageService,
            MessageMapper messageMapper,
            SimpMessagingTemplate messagingTemplate,
            NotificationService notificationService,
            UserRepo userRepo,
            InboxService inboxService
    ) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
        this.userRepo = userRepo;
        this.inboxService = inboxService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(MessageReqDTO dto) {

        Message message = messageService.sendMessage(
                dto.getSenderId(),
                dto.getReceiverId(),
                dto.getContent()
        );

        String receiverId = dto.getReceiverId();
        String conversationId = message.getConversation().getConversationId();

        // 1. Envoyer le message en temps réel au destinataire
        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/messages",
                messageMapper.toResDTO(message)
        );

        // 2. Envoyer la notification persistée
        User receiver = userRepo.findById(receiverId).orElseThrow();
        notificationService.sendNotification(
                receiver,
                "Nouveau message reçu",
                NotificationType.MESSAGE
        );

        // 3. Mettre à jour la boîte de réception (Inbox) du destinataire
        InboxDTO inbox = inboxService.getSingleInbox(receiverId, conversationId);
        messagingTemplate.convertAndSendToUser(
                receiverId,
                "/queue/inbox",
                inbox
        );
    }

    /**
     * Endpoint appelé par le client du destinataire dès que le message a été reçu sur son écran/appareil.
     */
    @MessageMapping("/chat.ack.delivered")
    public void markAsDelivered(MessageAckDTO ack) {
        // 1. Mettre à jour le statut en BDD
        Message message = messageService.markAsDelivered(ack.getMessageId());

        // 2. Informer l'expéditeur original en temps réel que son message est délivré
        MessageStatusUpdateDTO statusUpdate = MessageStatusUpdateDTO.builder()
                .messageId(message.getMessageId())
                .conversationId(message.getConversation().getConversationId())
                .status(message.getStatus())
                .build();

        messagingTemplate.convertAndSendToUser(
                ack.getSenderId(),
                "/queue/message-status",
                statusUpdate
        );
    }
}