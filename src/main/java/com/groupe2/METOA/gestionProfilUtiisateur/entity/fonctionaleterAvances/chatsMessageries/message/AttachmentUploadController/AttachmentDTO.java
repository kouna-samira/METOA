package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {
    private String fileUrl;
    private String fileName;
    private String fileType; // Ex: 'image/jpeg', 'application/pdf'
    private long fileSize ;
}
