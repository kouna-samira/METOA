package com.groupe2.METOA.gestionProfilUtiisateur.exception;

public class UserAlreadyExisteException extends RuntimeException {
    public UserAlreadyExisteException(String email) {

        super("Un utilisateur avec l'email " + email + " existe déjà");
    }
}
