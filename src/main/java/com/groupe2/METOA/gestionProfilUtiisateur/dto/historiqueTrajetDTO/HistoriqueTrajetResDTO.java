package com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO;

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
public class HistoriqueTrajetResDTO {
    private String historiqueTrajetId;
    private String trajetId;
    private String dateTrajet;
    private String rolUseInTrajet;
    private String statutTrajet;
}
