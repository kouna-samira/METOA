package com.groupe2.METOA.gestionProfilUtiisateur.dto.message;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.MessageStatus;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Builder
@Slf4j
public class MessageResDTO {
    private String messageId;
    private String content;

    private String senderId;
    private String receiverId;

    private LocalDateTime sentAt;
    private MessageStatus status;


}
