package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.MessageMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.entity.Conversation;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.conversation.service.ConversationService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "chats", description = "envoyer des messages ")
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ConversationService conversationService;


    public MessageController(MessageService messageService, MessageMapper messageMapper, ConversationService conversationService) {
        this.messageService = messageService;

        this.messageMapper = messageMapper;
        this.conversationService = conversationService;
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
    public ResponseEntity<List<Conversation>> getUserConversations(
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
    public ResponseEntity<Page<Message>> getMessagesPaginated(
            @PathVariable String conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        return ResponseEntity.ok(
                messageService.getMessagesPaginated(conversationId, page, size)
        );
    }
    @PutMapping
    public ResponseEntity<Message> editeNessage(@PathVariable String idMessage , @PathVariable String content){
        return ResponseEntity.ok(this.messageService.editMessage(idMessage, content));


    }
    @DeleteMapping
    public ResponseEntity<String> deleteMessage(@PathVariable String messageId){
        this.messageService.deleteMessage(messageId);
        return ResponseEntity.status(202).body("message supprimer");
    }
}
