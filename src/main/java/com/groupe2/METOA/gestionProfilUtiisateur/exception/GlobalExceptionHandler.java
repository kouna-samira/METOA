package com.groupe2.METOA.gestionProfilUtiisateur.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(HttpStatus status, String message){
        return new ErrorResponse(
                status.value(),
                message,
                LocalDateTime.now()
        );
    }


    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingFile(MissingServletRequestPartException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, "Le fichier est obligatoire (clé: file)"));
    }

    @ExceptionHandler(UserNoteFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNoteFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExisteException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(ProfileAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleProfileAlreadyExists(ProfileAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(InvaidUserDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvaidUserDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(ProfilNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfilNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildError(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(NullableFillException.class)
    public ResponseEntity<ErrorResponse> handleNullableFill(NullableFillException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundTrjetException.class)
    public ResponseEntity<ErrorResponse> handleTrajet(NotFoundTrjetException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // 🔥 IMPORTANT POUR DEBUG
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex) {

        ex.printStackTrace(); // 👈 MAINTENANT tu verras l'erreur

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}