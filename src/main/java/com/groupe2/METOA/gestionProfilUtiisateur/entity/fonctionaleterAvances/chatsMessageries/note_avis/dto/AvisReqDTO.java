package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvisReqDTO {

    private String cibleId;

    private String trajetId;

    private double note;

    private String commentaire;
}