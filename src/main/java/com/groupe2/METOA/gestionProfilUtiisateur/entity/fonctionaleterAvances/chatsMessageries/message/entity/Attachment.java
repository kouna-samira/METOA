package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String fileUrl;
    private String fileName;
    private String fileType;
    private Long fileSize;
}
