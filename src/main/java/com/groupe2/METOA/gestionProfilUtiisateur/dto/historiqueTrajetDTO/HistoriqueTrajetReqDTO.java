package com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class HistoriqueTrajetReqDTO {
    private String historiqueTrajetId;
    private String trajetId;
    @NotEmpty(message = "entrez votre role !")
    private String rolUseInTrajet;
}
