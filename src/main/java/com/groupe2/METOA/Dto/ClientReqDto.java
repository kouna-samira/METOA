package com.groupe2.METOA.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientReqDto {
    @NotEmpty(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotEmpty(message = "Le prénom ne peut pas être vide")
    private String prenom;

    @Email(message = "Email invalide")
    @NotEmpty(message = "L'email ne peut pas être vide")
    private String email;

    @NotEmpty(message = "Le téléphone ne peut pas être vide")
    private String telephone;


}
