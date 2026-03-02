package com.metoa.service;

import com.metoa.entity.Ville;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.VilleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VilleServiceImpl implements VilleService {

    private final VilleRepository villeRepository;

    public VilleServiceImpl(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    @Override
    public Ville ajouterVille(Ville ville) {
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