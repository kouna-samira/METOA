package com.groupe2.METOA.dto;

import lombok.Data;
import java.util.List;

@Data
public class PassagerResDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private List<Long> reservationsIds;
}
