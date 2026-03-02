package com.groupe2.METOA.gestionProfilUtiisateur.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class MessageReqDTO {
    @NotBlank
    private String senderId;
    @NotBlank
    private String receiverId;
    @NotBlank
    private String content;
}
