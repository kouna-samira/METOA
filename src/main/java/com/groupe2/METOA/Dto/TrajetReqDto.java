package com.groupe2.METOA.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrajetReqDto {
    private LocalDate dateTrajet;
    @NotEmpty(message = "fill this field")
    private String depart;
    @NotEmpty(message = "fill this field")
    private String destination;
    private double distance;

}