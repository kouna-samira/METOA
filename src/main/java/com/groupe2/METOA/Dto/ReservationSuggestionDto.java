package com.groupe2.METOA.Dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationSuggestionDto {
    private String message;
    private String idReservation;
    private List<TrajetSuggestionDto>suggestions;
}
