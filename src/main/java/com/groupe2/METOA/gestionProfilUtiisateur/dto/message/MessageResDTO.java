package com.groupe2.METOA.gestionProfilUtiisateur.dto.message;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.AttachmentDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResDTO {
    private String messageId;
    private String content;

    private String senderId;
    private String receiverId;

    private LocalDateTime sentAt;
    private MessageStatus status;
    private String ConversationId;
    private LocalDateTime timestamp;
    private String conversationId;

    // Le champ manquant :
    private MessageType type;
    private AttachmentDTO attachment;


}
