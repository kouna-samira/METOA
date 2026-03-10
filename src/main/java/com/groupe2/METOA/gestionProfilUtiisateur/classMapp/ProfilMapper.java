package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfilMapper {

    ProfilMapper INSTANCE = Mappers.getMapper(ProfilMapper.class);

    ProfilResDTO toResDTO(Profil profil);
    @Mapping(target = "profilId", ignore = true)
    Profil toEntity(ProfilReqDTO profilReqDTO);
    @Mapping(target = "profilId", ignore = true)
    void updateProfilFromDTO(ProfilReqDTO dto, @MappingTarget Profil profil);


}
