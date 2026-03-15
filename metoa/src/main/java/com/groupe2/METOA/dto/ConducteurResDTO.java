package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class ConducteurResDTO {
    @Schema(description = "Identifiant du conducteur")
    private Long id;

    @Schema(description = "Nom du conducteur")
    private String nom;

    @Schema(description = "Prénom du conducteur")
    private String prenom;

    @Schema(description = "Email du conducteur")
    private String email;

    @Schema(description = "Téléphone du conducteur")
    private String telephone;

    @Schema(description = "Liste des IDs des trajets du conducteur")
    private List<Long> trajetsIds;

    @Schema(description = "Liste des IDs des véhicules du conducteur")
    private List<Long> vehiculesIds;
}
