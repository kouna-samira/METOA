package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO.HistoriqueTrajetReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO.HistoriqueTrajetResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.HistoriqueTrajet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoriqueTrajetMapper {
    HistoriqueTrajetResDTO toResDTO(HistoriqueTrajet historiqueTrajet);

    @Mapping(target = "historiqueTrajetId", ignore = true)
    @Mapping(target = "user", ignore = true)
    HistoriqueTrajet toEntity(HistoriqueTrajetReqDTO historiqueTrajetReqDTO);
    List<HistoriqueTrajetResDTO> toResDTOList(List<HistoriqueTrajet> historiqueTrajetList);
}
