package com.groupe2.METOA.gestionProfilUtiisateur.entity;




public enum Role {
    PASSAGER("passager"),
    CHAUFFEUR("CONDUCTEUR"),
    ADMIN("adminsistrateur");


    private final String label;

    Role(String label) {
        this.label = label;
    }
}