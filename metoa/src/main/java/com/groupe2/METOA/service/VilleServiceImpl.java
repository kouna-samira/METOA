package com.groupe2.METOA.service;

import com.metoa.entity.Ville;
import com.metoa.exception.ResourceExistsException;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.TrajetRepository;
import com.metoa.repository.VilleRepository;
import com.metoa.service.VilleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VilleServiceImpl implements VilleService {

    private final VilleRepository villeRepository;
    private final TrajetRepository trajetRepository;

    public VilleServiceImpl(VilleRepository villeRepository, TrajetRepository trajetRepository) {
        this.villeRepository = villeRepository;
        this.trajetRepository = trajetRepository;
    }

    @Override
    public Ville ajouterVille(Ville ville) {
        if (villeRepository.existsByNomIgnoreCase(ville.getNom())) {
            throw new ResourceExistsException("Une ville avec ce nom existe déjà.");
        }
        return villeRepository.save(ville);
    }

    @Override
    public Ville modifierVille(Ville ville) {
        if (!villeRepository.existsById(ville.getId())) {
            throw new ResourceNotFoundException("Ville non trouvée avec id: " + ville.getId());
        }
        return villeRepository.save(ville);
    }

    @Override
    public void supprimerVille(Long villeId) {
        Ville ville = villeRepository.findById(villeId)
                .orElseThrow(() -> new ResourceNotFoundException("Ville non trouvée avec id: " + villeId));

        // Vérifier si la ville est référencée comme départ OU arrivée dans un trajet
        boolean estUtilisee = trajetRepository.existsByVilleDepartIdOrVilleArriveeId(villeId, villeId);
        if (estUtilisee) {
            throw new ResourceExistsException("Impossible de supprimer cette ville car elle est associée à des trajets existants (départ ou arrivée).");
        }

        villeRepository.delete(ville);
    }

    @Override
    public Optional<Ville> getVille(Long villeId) {
        return villeRepository.findById(villeId);
    }

    @Override
    public List<Ville> getAllVilles() {
        return villeRepository.findAll();
    }
}
