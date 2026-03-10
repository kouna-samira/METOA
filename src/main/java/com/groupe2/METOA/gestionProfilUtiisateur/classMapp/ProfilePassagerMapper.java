package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfilePassager;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProfilePassagerMapper {

    ProfilePassager toEntity(ProfilePassagerReqDTO dto);

    ProfilePassagerResDTO toDto(ProfilePassager entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProfilePassagerReqDTO dto, @MappingTarget ProfilePassager entity);
}