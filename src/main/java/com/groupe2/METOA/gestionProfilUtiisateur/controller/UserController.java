package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserSearchDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "utilisateur", description = "Gestion des utilisateurs")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @Operation(summary = "Créer un utilisateur")

    @PostMapping
    public UserResDTO createUser(@Valid @RequestBody UserReqDTO dto){
        return userService.createUser(dto);
    }

    // GET USER BY ID
    @Operation(summary = "Afficher un utilisateur")
    @GetMapping("/{id}")
    public UserResDTO getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    // GET ALL USERS (pagination)
    @Operation(summary = "Afficher tous les utilisateur")
    @GetMapping
    public Page<UserResDTO> getAllUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    // UPDATE USER
    @Operation(summary = "Modifier un utilisateur")
    @PutMapping("/{id}")
    public UserResDTO updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserReqDTO dto){

        return userService.updateUser(id,dto);
    }

    // DELETE USER
    @Operation(summary = "Supprimer un utilisateur")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id){
        userService.deleteUser(id);
    }

    // FIND BY EMAIL
    @Operation(summary = "Afficher un utilisateur par email")
    @GetMapping("/email/{email}")
    public UserResDTO getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    // FIND BY NOM
    @Operation(summary = "Afficher les utilisateur par nom ")
    @GetMapping("/nom/{nom}")
    public List<UserResDTO> getUserByNom(@PathVariable String nom){
        return userService.getUserByNom(nom);
    }

    // FIND BY PRENOM
    @Operation(summary = "Afficher les utilisateur par prenom")

    @GetMapping("/prenom/{prenom}")
    public List<UserResDTO> getUserByPrenom(@PathVariable String prenom){
        return userService.getUserByPrenom(prenom);
    }

    // GLOBAL SEARCH
    @Operation(summary = "recherche global")

    @GetMapping("/search")
    public Page<UserResDTO> globalSearch(
            @RequestParam String keyword,
            Pageable pageable){

        return userService.globalSearch(keyword,pageable);
    }

    // ADVANCED SEARCH
    @Operation(summary = "recherche avancer ")
    @PostMapping("/advanced-search")
    public Page<UserResDTO> searchUsers(
            @RequestBody UserSearchDTO searchDTO,
            Pageable pageable){

        return userService.searchUsers(searchDTO,pageable);
    }

}