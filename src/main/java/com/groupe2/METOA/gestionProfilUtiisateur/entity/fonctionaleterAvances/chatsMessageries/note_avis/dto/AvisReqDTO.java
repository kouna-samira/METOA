package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class AvisReqDTO {

    @Min(1)
    @Max(5)
    private int note;

    @NotBlank
    private String commentaire;

    private String trajetId;

    private String auteurId;

    private String cibleId;
}