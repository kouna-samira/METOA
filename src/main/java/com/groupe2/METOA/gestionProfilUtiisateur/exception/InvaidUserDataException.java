package com.groupe2.METOA.gestionProfilUtiisateur.exception;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;

public class InvaidUserDataException extends RuntimeException {
    public InvaidUserDataException(User user) {
        super("Un utilisateur invalide ...");
    }
}
