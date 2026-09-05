package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentification",
        description = "Gestion de l'inscription et de la connexion"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Inscrire un nouvel utilisateur")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody UserReqDTO dto
    ) {

        authService.register(dto);

        return ResponseEntity
                .status(201)
                .body("Inscription réussie");
    }


    @Operation(summary = "Connecter un utilisateur")
    @PostMapping("/login")
    public ResponseEntity<LoginResDTO> login(
            @Valid @RequestBody LoginReqDTO dto
    ) {

        return ResponseEntity
                .ok(authService.login(dto));
    }
}