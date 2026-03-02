package com.groupe2.METOA.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrajetResDto {

    private String idTrajet;
    private LocalDate dateTrajet;
    private String depart;
    private String destination;
    private double distance;
    private double prix;

}
