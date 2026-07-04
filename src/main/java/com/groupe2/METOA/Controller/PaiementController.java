package com.groupe2.METOA.Controller;

import com.groupe2.METOA.Dto.PaiementResDto;
import com.groupe2.METOA.Service.Paiement.PaiementService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/paiements")
public class PaiementController {
    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    // Ajouter un paiement pour une réservation
    @PostMapping(path = "/add")
    public void addPaiement(@RequestParam String reservationId,
                            @RequestParam double montant) {
        paiementService.addPaiement(reservationId, montant);
    }

    // Obtenir tous les paiements
    @GetMapping(path = "/get_all")
    public List<PaiementResDto> getAllPaiements() {
        return paiementService.getAllPaiements();
    }
    
    @GetMapping
    public Page<PaiementResDto> getAllPaiements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return paiementService.getPaiements(page, size);
    }
}
