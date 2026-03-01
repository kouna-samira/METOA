package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "utilisateur", description = "Gestion des utilisateurs")
@RestController
@RequestMapping(path = "api/v1/User")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "Créer un utilisateur ")
    @PostMapping(path = "/add")
    public ResponseEntity<String> createNewUser(@RequestBody @Valid UserReqDTO userReqDTO){
        this.userService.createUser(userReqDTO);
        return  ResponseEntity.status(201).body("Customer created successfully !");
    }


    @Operation(summary = "consuter un utilisateur")
    @GetMapping(path = "/get_user_by_id/{idUser}")
    public ResponseEntity<UserResDTO> getUser(@Valid @PathVariable String idUser){
        return ResponseEntity.status(200).body(this.userService.getFindByIdUser(idUser));
    }

    @Operation(summary = "modifier un user")
    @PatchMapping(path = "/update_by_id/{idUser}")
    public ResponseEntity<String> updateUser(@PathVariable @Valid String idUser,@RequestBody @Valid UserReqDTO userReqDTO){
        this.userService.UpdateUser(userReqDTO,idUser);
        return ResponseEntity.status(202).body("Updated successfully!");
    }


    @Operation(summary = "supprimer un utiisateur")
    @DeleteMapping(path = "/delete_by_id/{idUser}")
    public ResponseEntity<String> deleteUser(@PathVariable String idUser){
        this.userService.deleteUser(idUser);
        return ResponseEntity.status(202).body("Deleted successfully!");
    }

}
