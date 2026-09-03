package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.MessageMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.dto.ConversationDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service.ConversationService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.MessageAckDTO.MessageStatusUpdateDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "chats", description = "envoyer des messages ")
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ConversationService conversationService;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, MessageMapper messageMapper, ConversationService conversationService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.conversationService = conversationService;
        this.messagingTemplate = messagingTemplate;
    }

    @Operation(summary = "envoyer un message")
    @PostMapping("/send")
    public ResponseEntity<MessageResDTO> sendMessage(
            @RequestBody MessageReqDTO messageReqDTO
    ) {
        Message message = messageService.sendMessage(
                messageReqDTO.getSenderId(),
                messageReqDTO.getReceiverId(),
                messageReqDTO.getContent()
        );
        return ResponseEntity.ok(messageMapper.toResDTO(message));
    }

    @Operation(summary = "les conversation d' un user ")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationDTO>> getUserConversations(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                conversationService.getAllConversationsByUser(userId)
        );
    }

    @Operation(summary = "rechercher une conversation")
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<?> getMessages(@PathVariable String conversationId) {
        return ResponseEntity.ok(
                messageService.getMessages(conversationId)
        );
    }

    @Operation(summary = "messages recus")
    @PutMapping("/conversation/{conversationId}/read/{userId}")
    public ResponseEntity<Void> markAsRead(
            @PathVariable String conversationId,
            @PathVariable String userId
    ) {
        messageService.markConversationAsRead(conversationId, userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "message lus")
    @GetMapping("/conversation/{conversationId}/unread/{userId}")
    public ResponseEntity<Long> countUnreadMessages(
            @PathVariable String conversationId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                messageService.countUnreadMessages(conversationId, userId)
        );
    }

    @Operation(summary = "pagination des messages")
    @GetMapping("/conversation/{conversationId}/paged")
    public ResponseEntity<Page<MessageResDTO>> getMessagesPaginated(
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return ResponseEntity.ok(
                messageService.getMessagesPaginated(conversationId, page, size)
        );
    }

    @Operation(summary = "modifier le contenu d'un message")
    @PutMapping("/{id}")
    public ResponseEntity<Message> editMessage(
            @PathVariable("id") String idMessage,
            @RequestParam String content
    ) {
        return ResponseEntity.ok(this.messageService.editMessage(idMessage, content));
    }

    @Operation(summary = "supprimer un message")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId){
        this.messageService.deleteMessage(messageId);
        return ResponseEntity.status(202).body("message supprimer");
    }

    @PatchMapping("/read/{conversationId}")
    public ResponseEntity<Void> markConversationAsRead(
            @PathVariable String conversationId,
            @RequestParam String userId,
            @RequestParam String otherUserId
    ) {
        // 1. Marquer les messages reçus non lus comme LU en base de données
        messageService.markConversationAsRead(conversationId, userId);

        // 2. Avertir l'autre utilisateur (l'expéditeur) en temps réel via WebSocket
        MessageStatusUpdateDTO statusUpdate = MessageStatusUpdateDTO.builder()
                .conversationId(conversationId)
                .status(MessageStatus.LU)
                .build();

        messagingTemplate.convertAndSendToUser(
                otherUserId,
                "/queue/message-status",
                statusUpdate
        );

        return ResponseEntity.noContent().build();
    }
}