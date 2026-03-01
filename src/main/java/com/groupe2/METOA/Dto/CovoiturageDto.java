package com.groupe2.METOA.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CovoiturageDto {
    private String idtrajet;
    private String depart;
    private  String destination;
    private LocalDate dateTrajet;
    private int nombrePlace;
    private List<String> clients;


}
