package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProfileConducteurDocDTO {
    private String profileConducteurId;
    private MultipartFile documentFile;
    private TyperDocument typerDocument;
}

