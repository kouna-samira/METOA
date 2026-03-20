package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.webSocket;

import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.MessageMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
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

    public ChatWebSocketController(
            MessageService messageService,
            MessageMapper messageMapper,
            SimpMessagingTemplate messagingTemplate,
            NotificationService notificationService,
            UserRepo userRepo
    ) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
        this.userRepo = userRepo;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(MessageReqDTO dto) {

        Message message = messageService.sendMessage(
                dto.getSenderId(),
                dto.getReceiverId(),
                dto.getContent()
        );

        // 📩 envoyer au receiver (temps réel)
        messagingTemplate.convertAndSendToUser(
                dto.getReceiverId(),
                "/queue/messages",
                messageMapper.toResDTO(message)
        );

        // 🔔 notification
        User receiver = userRepo.findById(dto.getReceiverId()).orElseThrow();

        notificationService.sendNotification(
                receiver,
                "Nouveau message reçu",
                NotificationType.MESSAGE
        );
    }
}