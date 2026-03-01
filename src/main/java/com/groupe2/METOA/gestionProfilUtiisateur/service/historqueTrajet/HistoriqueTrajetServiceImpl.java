package com.groupe2.METOA.gestionProfilUtiisateur.service.historqueTrajet;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.HistoriqueTrajetMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO.HistoriqueTrajetResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.HistoriqueTrajet;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.StatusTrajet;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.NotFoundTrjetException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.HistoriqueTrajetRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueTrajetServiceImpl implements HistoriqueTrajetService{

    private final HistoriqueTrajetRepo historiqueTrajetRepo;
    private  final HistoriqueTrajetMapper historiqueTrajetMapper;

    public HistoriqueTrajetServiceImpl(HistoriqueTrajetRepo historiqueTrajetRepo, HistoriqueTrajetMapper historiqueTrajetMapper) {
        this.historiqueTrajetRepo = historiqueTrajetRepo;
        this.historiqueTrajetMapper = historiqueTrajetMapper;
    }


    @Override
    public List<HistoriqueTrajetResDTO> getFindByUserIdAndStatusTrajet(String idUser, StatusTrajet statusTrajet) {
        List<HistoriqueTrajet> trajets = this.historiqueTrajetRepo.findByUser_IdUserAndStatusTrajet(idUser,statusTrajet);

        if (trajets.isEmpty()) {
            throw new NotFoundTrjetException("Aucun trajet trouvé pour cet utilisateur");
        }

        return historiqueTrajetMapper.toResDTOList(trajets);
    }
}
