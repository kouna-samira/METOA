package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserSearchDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody UserReqDTO dto){
        this.userService.createUser(dto);

         return ResponseEntity.status(201).body("create user successfully");
    }

    // GET USER BY ID
    @Operation(summary = "Afficher un utilisateur")
    @GetMapping("/{id}")
    public ResponseEntity<UserResDTO> getUserById(@PathVariable String id){

        return ResponseEntity.status(200).body(userService.getUserById(id));
    }

    // GET ALL USERS (pagination)
    @Operation(summary = "Afficher tous les utilisateur")
    @GetMapping
    public ResponseEntity<Page<UserResDTO>>getAllUsers(
     @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "30") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(userService.getAllUsers(pageable));
    }

    // UPDATE USER
    @Operation(summary = "Modifier un utilisateur")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserReqDTO dto){
        this.userService.updateUser(id,dto);
        return ResponseEntity.status(202).body("update user successfully");
    }

    // DELETE USER
    @Operation(summary = "Supprimer un utilisateur")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){

        userService.deleteUser(id);
        return ResponseEntity.status(202).body("user delete successfully");
    }

    // FIND BY EMAIL
    @Operation(summary = "Afficher un utilisateur par email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResDTO> getUserByEmail(@PathVariable String email){

        return ResponseEntity.status(200).body(userService.getUserByEmail(email));
    }

    // FIND BY NOM
    @Operation(summary = "Afficher les utilisateur par nom ")
    @GetMapping("/nom/{nom}")
    public ResponseEntity<List<UserResDTO>> getUserByNom(@PathVariable String nom){
        return ResponseEntity.status(200).body(userService.getUserByNom(nom));
    }

    // FIND BY PRENOM
    @Operation(summary = "Afficher les utilisateur par prenom")

    @GetMapping("/prenom/{prenom}")
    public ResponseEntity<List<UserResDTO>> getUserByPrenom(@PathVariable String prenom){
        return ResponseEntity.status(200).body(userService.getUserByPrenom(prenom));
    }

    // GLOBAL SEARCH
    @Operation(summary = "recherche global")

    @GetMapping("/search")
    public ResponseEntity<Page<UserResDTO>> globalSearch(
            @RequestParam String keyword,
            Pageable pageable){

        return ResponseEntity.status(200).body(userService.globalSearch(keyword,pageable));
    }

    // ADVANCED SEARCH
    @Operation(summary = "recherche avancer ")
    @PostMapping("/advanced-search")
    public ResponseEntity<Page<UserResDTO>>searchUsers(
            @RequestBody UserSearchDTO searchDTO,
            Pageable pageable){

        return ResponseEntity.status(200).body(userService.searchUsers(searchDTO,pageable));
    }

}