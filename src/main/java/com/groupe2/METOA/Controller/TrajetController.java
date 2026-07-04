package com.groupe2.METOA.Controller;

import com.groupe2.METOA.Dto.TrajetReqDto;
import com.groupe2.METOA.Dto.TrajetResDto;
import com.groupe2.METOA.Service.Trajet.TrajetService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/trajets")
public class TrajetController {
    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }
    // Ajouter un trajet
    @PostMapping(path = "add_trajet")
    public ResponseEntity<String> addTrajet(@RequestBody TrajetReqDto trajetReqDto) {
        trajetService.addTrajet(trajetReqDto);
        return ResponseEntity.ok("Trajet ajouté avec succès !");
    }

    // Récupérer un trajet par ID
    @GetMapping(path = "/Get by id/{id}")
    public ResponseEntity<TrajetResDto> getTrajetById(@PathVariable String idTrajet) {
        TrajetResDto trajet = trajetService.getTrajetById(idTrajet);
        return ResponseEntity.ok(trajet);
    }

    // Récupérer tous les trajets
    @GetMapping(path = "/get_all_trajet")
    public ResponseEntity<List<TrajetResDto>> getAllTrajets() {
        List<TrajetResDto> trajets = trajetService.getAllTrajets();
        return ResponseEntity.ok(trajets);
    }

    // Mettre à jour un trajet
    @PutMapping(path = "/update_trajet_by_id/{idtrajet}")
    public ResponseEntity<String> updateTrajet(@PathVariable String idTrajet, @RequestBody TrajetResDto trajetResDto) {
        trajetService.updateTrajet(idTrajet, trajetResDto);
        return ResponseEntity.ok("Trajet mis à jour avec succès !");
    }

    // Supprimer un trajet
    @DeleteMapping("/Delete/{idTrajet}")
    public ResponseEntity<String> deleteTrajet(@PathVariable String idTrajet) {
        trajetService.deleteTrajet(idTrajet);
        return ResponseEntity.ok("Trajet supprimé avec succès !");
    }
    @GetMapping("/get_all")
    public Page<TrajetResDto> getAllTrajets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return trajetService.getTrajets(page, size);
    }


}
