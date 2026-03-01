package com.groupe2.METOA.gestionProfilUtiisateur.service.historqueTrajet;



import com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO.HistoriqueTrajetResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.StatusTrajet;

import java.util.List;

public interface HistoriqueTrajetService {
List<HistoriqueTrajetResDTO> getFindByUserIdAndStatusTrajet(String idUser , StatusTrajet statusTrajet);

}
