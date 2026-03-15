package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.dto;

import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.entity.Badge;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class StatistiqueAvisDTO {

    private double moyenne;
    private int totalAvis;

    private long cinqEtoiles;
    private long quatreEtoiles;
    private long troisEtoiles;
    private long deuxEtoiles;
    private long uneEtoile;

    private double tauxSatisfaction;

    private Badge badge;
}