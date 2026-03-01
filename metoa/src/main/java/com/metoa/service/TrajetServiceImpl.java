package com.metoa.service;

import com.metoa.entity.Trajet;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;

    public TrajetServiceImpl(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    @Override
    public Trajet creerTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet modifierTrajet(Trajet trajet) {
        if (!trajetRepository.existsById(trajet.getId())) {
            throw new ResourceNotFoundException("Trajet non trouvé avec id: " + trajet.getId());
        }
        return trajetRepository.save(trajet);
    }

    @Override
    public void supprimerTrajet(Long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        trajetRepository.delete(trajet);
    }

    @Override
    public Optional<Trajet> getTrajet(Long trajetId) {
        return trajetRepository.findById(trajetId);
    }

    @Override
    public List<Trajet> getAllTrajets() {
        return trajetRepository.findAll();
    }
}