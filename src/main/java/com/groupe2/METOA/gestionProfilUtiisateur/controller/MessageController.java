package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.MessageMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.Message;
import com.groupe2.METOA.gestionProfilUtiisateur.service.message.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "chats", description = "envoyer des messages ")
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;

        this.messageMapper = messageMapper;
    }
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
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<?> getMessages(@PathVariable String conversationId) {
        return ResponseEntity.ok(
                messageService.getMessages(conversationId)
        );
    }
    @PutMapping("/conversation/{conversationId}/read/{userId}")
    public ResponseEntity<Void> markAsRead(
            @PathVariable String conversationId,
            @PathVariable String userId
    ) {
        messageService.markConversationAsRead(conversationId, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/conversation/{conversationId}/unread/{userId}")
    public ResponseEntity<Long> countUnreadMessages(
            @PathVariable String conversationId,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(
                messageService.countUnreadMessages(conversationId, userId)
        );
    }
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
}
