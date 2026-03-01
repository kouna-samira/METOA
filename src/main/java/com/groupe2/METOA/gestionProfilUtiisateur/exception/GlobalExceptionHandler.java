package com.groupe2.METOA.gestionProfilUtiisateur.exception;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(UserNoteFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFound(UserNoteFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorMessage(
                ex.getMessage()
        ));
    }

    @ExceptionHandler(UserAlreadyExisteException.class)
    public ResponseEntity<ErrorMessage> handleUserAlreadyExists(UserAlreadyExisteException ex) {
        return  ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()
        ));
    }

    @ExceptionHandler(InvaidUserDataException.class)
    public ResponseEntity<?> handleInvalidData(InvaidUserDataException ex) {
        return  ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()
        ));
    }

    @ExceptionHandler(ProfilNotFoundException.class)
    public ResponseEntity<String> handleProfilNotFound(ProfilNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }


    @ExceptionHandler(NotFoundTrjetException.class)
    public ResponseEntity<String> handleAccessDenied(NotFoundTrjetException ex) {
        return ResponseEntity.status(403).body(ex.getMessage());
    }
}
