package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.webSocket;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.MessageAckDTO.TypingEventDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

public class TypingController {
    private final SimpMessagingTemplate messagingTemplate;

    public TypingController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.typing")
    public void handleTypingEvent(@Payload TypingEventDTO typingEvent) {
        // Transmet l'événement directement au destinataire sur son canal privé
        messagingTemplate.convertAndSendToUser(
                typingEvent.getReceiverId(),
                "/queue/typing",
                typingEvent
        );
    }
}
