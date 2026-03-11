package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.service;

import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.StatistiqueAvisDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.entity.Avis;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.repo.AvisRepo;
import com.groupe2.METOA.gestionProfileUtilisateur.repository.ProfileRepo;
import com.groupe2.METOA.gestionProfileUtilisateur.service.TrajetService;
import com.groupe2.METOA.gestionProfileUtilisateur.service.profile.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvisServiceImpl implements AvisService {

    private final AvisRepo avisRepo;
    private final TrajetService trajetService;
    private final ProfileRepo profileRepo;

    public AvisServiceImpl(AvisRepo avisRepo, TrajetService trajetService, ProfileRepo profileRepo) {
        this.avisRepo = avisRepo;
        this.trajetService = trajetService;
        this.profileRepo = profileRepo;
    }

    @Override
    public AvisResDTO donnerAvis(String auteurId, AvisReqDTO dto) {

        if (dto.getNote() < 0 || dto.getNote() > 5) {
            throw new IllegalArgumentException("La note doit être entre 0 et 5");
        }

        // Vérifier participation au trajet
        if (!trajetService.utilisateurAParticipe(dto.getTrajetId(), auteurId)) {
            throw new RuntimeException("Vous ne pouvez noter que si vous avez participé au trajet");
        }

        // Empêcher double notation
        if (avisRepo.findByAuteurIdAndTrajetId(auteurId, dto.getTrajetId()).isPresent()) {
            throw new RuntimeException("Vous avez déjà noté pour ce trajet");
        }

        Avis avis = Avis.builder()
                .auteurId(auteurId)
                .cibleId(dto.getCibleId())
                .trajetId(dto.getTrajetId())
                .note(dto.getNote())
                .commentaire(dto.getCommentaire())
                .modifiable(true)
                .signale(false)
                .dateCreation(LocalDateTime.now())
                .build();

        avisRepo.save(avis);

        profileRepo.updateProfilStats(dto.getCibleId());

        return mapToDTO(avis);
    }

    @Override
    public AvisResDTO modifierAvis(String avisId, double note, String commentaire) {

        Avis avis = avisRepo.findById(avisId)
                .orElseThrow(() -> new RuntimeException("Avis introuvable"));

        if (avis.getDateCreation().plusHours(24).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Modification impossible après 24h");
        }

        avis.setNote(note);
        avis.setCommentaire(commentaire);
        avis.setDateModification(LocalDateTime.now());

        avisRepo.save(avis);

        updateProfilStats(avis.getCibleId());

        return mapToDTO(avis);
    }

    @Override
    public Page<AvisResDTO> getAvisUtilisateur(String userId, Pageable pageable) {
        return avisRepo.findByCibleId(userId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public StatistiqueAvisDTO getStatistiques(String userId) {

        List<Avis> avisList = avisRepo.findByCibleId(userId);

        int total = avisList.size();

        double moyenne = avisList.stream()
                .mapToDouble(Avis::getNote)
                .average()
                .orElse(0);

        long cinq = avisList.stream().filter(a -> a.getNote() == 5).count();
        long quatre = avisList.stream().filter(a -> a.getNote() == 4).count();
        long trois = avisList.stream().filter(a -> a.getNote() == 3).count();
        long deux = avisList.stream().filter(a -> a.getNote() == 2).count();
        long un = avisList.stream().filter(a -> a.getNote() == 1).count();

        double taux = total == 0 ? 0 : ((cinq + quatre) * 100.0) / total;

        Badge badge = calculerBadge(total, moyenne);

        return StatistiqueAvisDTO.builder()
                .moyenne(moyenne)
                .totalAvis(total)
                .cinqEtoiles(cinq)
                .quatreEtoiles(quatre)
                .troisEtoiles(trois)
                .deuxEtoiles(deux)
                .uneEtoile(un)
                .tauxSatisfaction(taux)
                .badge(badge)
                .build();
    }

    private Badge calculerBadge(int total, double moyenne) {

        if (total >= 20 && moyenne >= 4.5)
            return Badge.SUPER_CONDUCTEUR;

        if (total >= 10 && moyenne >= 4)
            return Badge.CONDUCTEUR_FIABLE;

        if (total >= 15 && moyenne >= 4.3)
            return Badge.PASSAGER_PREMIUM;

        if (moyenne < 2 && total >= 5)
            return Badge.A_RISQUE;

        return Badge.NOUVEAU;
    }

    @Override
    public void updateNoteEtBadge(String userId,
                                  Double moyenne,
                                  Integer totalAvis,
                                  String badge) {

        profileRepo.updateStats(userId, moyenne, totalAvis, badge);
    }

    private AvisResDTO mapToDTO(Avis avis) {
        return AvisResDTO.builder()
                .id(avis.getId())
                .auteurId(avis.getAuteurId())
                .cibleId(avis.getCibleId())
                .trajetId(avis.getTrajetId())
                .note(avis.getNote())
                .commentaire(avis.getCommentaire())
                .dateCreation(avis.getDateCreation())
                .build();
    }
}
