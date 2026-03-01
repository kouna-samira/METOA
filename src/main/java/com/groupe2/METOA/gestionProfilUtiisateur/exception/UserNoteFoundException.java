package com.groupe2.METOA.gestionProfilUtiisateur.exception;

public class UserNoteFoundException extends RuntimeException {
    public UserNoteFoundException(String idUser) {

        super("Utilisateur avec l'identifiant " + idUser + " introuvable");
    }
}
