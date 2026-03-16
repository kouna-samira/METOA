package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
@Builder
public class AvisResDTO {

    private String avisId;

    private int note;

    private String commentaire;

    private LocalDateTime dateAvis;

    private String auteurNom;

    private String cibleNom;
}