package com.groupe2.METOA.gestionProfilUtiisateur.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse buildError(HttpStatus status, String message){
        return ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // USER NOT FOUND
    @ExceptionHandler(UserNoteFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNoteFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // USER ALREADY EXISTS
    @ExceptionHandler(UserAlreadyExisteException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    // PROFILE ALREADY EXISTS
    @ExceptionHandler(ProfileAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleProfileAlreadyExists(ProfileAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    // INVALID DATA
    @ExceptionHandler(InvaidUserDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidData(InvaidUserDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // PROFILE NOT FOUND
    @ExceptionHandler(ProfilNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFound(ProfilNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // ACCESS DENIED
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(buildError(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    // NULL FILE / EMPTY
    @ExceptionHandler(NullableFillException.class)
    public ResponseEntity<ErrorResponse> handleNullableFill(NullableFillException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // TRAJET NOT FOUND
    @ExceptionHandler(NotFoundTrjetException.class)
    public ResponseEntity<ErrorResponse> handleTrajet(NotFoundTrjetException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // FALLBACK GLOBAL
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}