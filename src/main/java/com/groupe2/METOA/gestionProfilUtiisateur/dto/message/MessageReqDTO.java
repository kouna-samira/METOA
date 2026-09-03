package com.groupe2.METOA.gestionProfilUtiisateur.dto.message;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.AttachmentDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReqDTO {
    @NotBlank
    private String senderId;
    @NotBlank
    private String receiverId;
    @NotBlank
    private String content;
    private String conversationId; // 👈 Assure-toi que ce champ est bien présent
    private MessageType type;
    private AttachmentDTO attachment;
}
