package com.metoa.service;

import com.metoa.entity.Ville;
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
        return villeRepository.save(ville);
    }

    @Override
    public void supprimerVille(Long villeId) {
        villeRepository.deleteById(villeId);
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
