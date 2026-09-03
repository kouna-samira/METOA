package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.StorageService;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.AttachmentDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();
    AttachmentDTO store(MultipartFile file);
    Resource loadAsResource(String filename);
}
