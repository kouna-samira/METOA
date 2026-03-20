package com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DocumentService {

    @Value("${document.upload-dir}")
    private String uploadDir;

    // Types MIME autorisés
    private final List<String> allowedMimeTypes = List.of(
            "application/pdf",
            "application/msword", // .doc
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .docx
            "text/plain"
    );

    // Validation du fichier
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }
        String contentType = file.getContentType();
        if (!allowedMimeTypes.contains(contentType)) {
            throw new IllegalArgumentException("Type de fichier non autorisé : " + contentType);
        }
    }

    // Upload document
    public String uploadDocument(String userId, MultipartFile file, TyperDocument type) {
        validateFile(file);
        try {
            Path userDir = Paths.get(uploadDir, userId);
            Files.createDirectories(userDir);

            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = userDir.resolve(filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload du fichier", e);
        }
    }

    // Voir document (retourne le chemin)
    public String viewDocument(String userId, String filename) {
        Path filePath = Paths.get(uploadDir, userId, filename);
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Fichier non trouvé");
        }
        return filePath.toAbsolutePath().toString();
    }

    // Supprimer document
    public void deleteDocument(String userId, String filename) {
        Path filePath = Paths.get(uploadDir, userId, filename);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la suppression du fichier", e);
        }
    }

    // Lister tous les fichiers d'un utilisateur
    public List<String> listDocuments(String userId) {
        Path userDir = Paths.get(uploadDir, userId);
        if (!Files.exists(userDir)) return Collections.emptyList();

        try (Stream<Path> paths = Files.list(userDir)) {
            return paths.map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lister les documents", e);
        }
    }
}