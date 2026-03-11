package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AvisResDTO {

    private String id;
    private String auteurId;
    private String cibleId;
    private String trajetId;
    private double note;
    private String commentaire;
    private LocalDateTime dateCreation;
}