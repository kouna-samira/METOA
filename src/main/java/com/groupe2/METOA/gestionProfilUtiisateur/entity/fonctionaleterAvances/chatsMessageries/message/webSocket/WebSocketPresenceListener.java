package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.webSocket;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.MessageAckDTO.PresenceDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service.PresenceService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

public class WebSocketPresenceListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final PresenceService presenceService;

    public WebSocketPresenceListener(SimpMessagingTemplate messagingTemplate, PresenceService presenceService) {
        this.messagingTemplate = messagingTemplate;
        this.presenceService = presenceService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = getUserIdFromAccessor(headerAccessor);

        if (userId != null) {
            presenceService.setUserOnlineStatus(userId, true);

            PresenceDTO presenceUpdate = PresenceDTO.builder()
                    .userId(userId)
                    .isOnline(true)
                    .build();

            // Diffuse l'événement à tous les abonnés
            messagingTemplate.convertAndSend("/topic/presence", presenceUpdate);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = getUserIdFromAccessor(headerAccessor);

        if (userId != null) {
            LocalDateTime lastSeen = LocalDateTime.now();
            presenceService.setUserOfflineStatus(userId, lastSeen);

            PresenceDTO presenceUpdate = PresenceDTO.builder()
                    .userId(userId)
                    .isOnline(false)
                    .lastSeen(lastSeen.toString())
                    .build();

            messagingTemplate.convertAndSend("/topic/presence", presenceUpdate);
        }
    }

    private String getUserIdFromAccessor(StompHeaderAccessor accessor) {
        if (accessor.getUser() != null) {
            return accessor.getUser().getName();
        }
        // Fallback si l'ID est transmis dans les headers connect
        return accessor.getFirstNativeHeader("userId");
    }
}
