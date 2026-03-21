package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.service.AvisService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name = "avis", description = "notation et avis")
@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @PostMapping
    public ResponseEntity<AvisResDTO> create(@RequestBody AvisReqDTO dto){
        return ResponseEntity.ok(avisService.createAvis(dto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<AvisResDTO>> getAvisUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(avisService.getAvisUser(userId, pageable));
    }

    // 🔥 Filtre par note
    @GetMapping("/{userId}/note")
    public ResponseEntity<Page<AvisResDTO>> getByNote(
            @PathVariable String userId,
            @RequestParam int note,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(avisService.getAvisByNote(userId, note, pageable));
    }

    @GetMapping("/user/{userId}")
    public Page<AvisResDTO> getAvisUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "dateAvis") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return avisService.getAvisUser(userId, page, size, sortBy, direction);
    }

    // 🔥 Moyenne
    @GetMapping("/{userId}/moyenne")
    public ResponseEntity<Double> moyenne(@PathVariable String userId){
        return ResponseEntity.ok(avisService.calculerNoteMoyenne(userId));
    }

    // 🗑 supprimer
    @DeleteMapping("/{avisId}")
    public ResponseEntity<Void> delete(@PathVariable String avisId){
        avisService.deleteAvis(avisId);
        return ResponseEntity.noContent().build();
    }
}