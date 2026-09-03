package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.StorageService;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.AttachmentUploadController.AttachmentDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService{

    private final Path rootLocation;
    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            "jpg", "jpeg", "png", "gif", "pdf", "docx", "xlsx", "zip"
    );

    public FileSystemStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir);
    }



    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Impossible d'initialiser le dossier de stockage : " + rootLocation, e);
        }
    }

    @Override
    public AttachmentDTO store(MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Impossible de stocker un fichier vide.");
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Sécurité : Empêcher le Path Traversal Attack (ex: ../file.txt)
        if (originalFilename.contains("..")) {
            throw new SecurityException("Nom de fichier invalide (tentative de traversée de répertoire) : " + originalFilename);
        }

        // Générer un nom de fichier unique pour éviter la collision
        String fileExtension = getFileExtension(originalFilename);
        String storedFilename = UUID.randomUUID().toString() + fileExtension;

        try {
            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename))
                    .normalize().toAbsolutePath();

            // Vérification de sécurité
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new SecurityException("Impossible de stocker le fichier en dehors du répertoire configuré.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            // Construire l'URL d'accès public au fichier
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/attachments/files/")
                    .path(storedFilename)
                    .toUriString();

            return AttachmentDTO.builder()
                    .fileUrl(fileDownloadUri)
                    .fileName(originalFilename)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Échec de l'enregistrement du fichier : " + originalFilename, e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Fichier introuvable ou non lisible : " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier : " + filename, e);
        }
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        if (lastIndex == -1) {
            return ""; // Pas d'extension
        }
        return filename.substring(lastIndex);
    }
}
