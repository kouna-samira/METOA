package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/users/{userId}/document")
@Tag(name = "upload/download", description = "import/export document  ")

public class DocumentController {

    private final DocumentService service;

    public DocumentController(DocumentService service) {
        this.service = service;
    }

    // UPLOAD
    @Operation(summary = "import document")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Map<String,String>> uploadDocument(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("type") TyperDocument typerDocument) {

        String url = service.uploadDocument(userId, file, typerDocument);
        return ResponseEntity.ok(Map.of("documentUrl", url));
    }

    // VIEW
    @Operation(summary = "view et download document")
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> viewDocument(
            @PathVariable String userId,
            @PathVariable String filename) {

        String path = service.viewDocument(userId, filename);
        Resource resource = new FileSystemResource(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                .body(resource);
    }

    // DELETE
    @Operation(summary = "delete document")
    @DeleteMapping("/{filename}")
    public ResponseEntity<String> deleteDocument(
            @PathVariable String userId,
            @PathVariable String filename) {

        service.deleteDocument(userId, filename);
        return ResponseEntity.ok("Document supprimé");
    }

    // LIST
    @Operation(summary = "list document")
    @GetMapping
    public ResponseEntity<List<String>> listDocuments(@PathVariable String userId) {
        return ResponseEntity.ok(service.listDocuments(userId));
    }
}