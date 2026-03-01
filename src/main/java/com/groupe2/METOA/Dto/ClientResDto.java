package com.groupe2.METOA.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResDto {
    private String idclient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

}
