package com.groupe2.METOA.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrajetSuggestionDto {
    private LocalDate dateTrajet;
    private String depart;
    private String destination;
    private double prix;
    private String vehiculeNom;
    private String vehiculeTelephone;
    private String idTrajet;

    public TrajetSuggestionDto(String idTrajet, LocalDate dateTrajet, String depart, String destination, double prix) {
    }
}
