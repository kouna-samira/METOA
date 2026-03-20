package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.service;


import com.groupe2.METOA.Entity.Trajet;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Avis;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.mapper.AvisMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.repo.AvisRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfileConducteurRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import com.groupe2.METOA.repository.TrajetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisServiceImpl implements AvisService {

    private final AvisRepo avisRepository;
    private final UserRepo userRepo;
    private final TrajetRepo trajetRepo;
    private final ProfileConducteurRepo profileRepo;
    private final AvisMapper mapper;

    @Override
    public AvisResDTO createAvis(AvisReqDTO dto) {

        if(avisRepository.findAvisByAuteurAndTrajet(
                dto.getAuteurId(),
                dto.getTrajetId()).isPresent()){

            throw new RuntimeException("Avis déjà donné pour ce trajet");
        }

        User auteur = userRepo.findById(dto.getAuteurId())
                .orElseThrow();

        User cible = userRepo.findById(dto.getCibleId())
                .orElseThrow();

        Trajet trajet = trajetRepo.findById(dto.getTrajetId())
                .orElseThrow();

        Avis avis = mapper.toEntity(dto);

        avis.setAuteur(auteur);
        avis.setCible(cible);
        avis.setTrajet(trajet);
        avis.setDateAvis(LocalDateTime.now());
        avis.setVisible(true);

        avisRepository.save(avis);

        updateNoteEtBadge(cible.getIdUser());

        return mapper.toDto(avis);
    }

    @Override
    public List<AvisResDTO> getAvisPublicByUser(String userId){

        return avisRepository.findByCibleIdUserAndVisibleTrue(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
    @Override
    public Page<AvisResDTO> getAvisUser(String userId, Pageable pageable){

        return avisRepository
                .findByCibleIdUser(userId,pageable)
                .map(mapper::toDto);
    }

    @Override
    public double calculerNoteMoyenne(String userId) {

        List<Avis> avis = avisRepository.findByCibleIdUserAndVisibleTrue(userId);

        return avis.stream()
                .mapToInt(Avis::getNote)
                .average()
                .orElse(0);
    }

    private void updateNoteEtBadge(String userId){

        ProfileConducteur profile = profileRepo.findByUserIdUser(userId)
                .orElse(null);

        if(profile == null) return;

        double moyenne = calculerNoteMoyenne(userId);

        profile.setNoteMoyenne(moyenne);

        int trajets = profile.getNombreTrajetsEffectues();

        if(moyenne >= 4.8 && trajets >= 50)
            profile.setBadge(Badge.SUPER_CONDUCTEUR);

        else if(moyenne >= 4)
            profile.setBadge(Badge.CONDUCTEUR_FIABLE);

        else if(moyenne < 3)
            profile.setBadge(Badge.A_RISQUE);

        else
            profile.setBadge(Badge.NOUVEAU);

        profileRepo.save(profile);
    }

    @Override
    public void deleteAvis(String avisId) {

        avisRepository.deleteById(avisId);
    }
}
