package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;



import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfileConducteurMapper {

    //  ENTITY -> RESPONSE

    ProfileConducteurResDTO toDto(ProfileConducteur profileConducteur);


    // req -> entity

    ProfileConducteur toEntity(ProfileConducteurReqDTO dto);


    // modifier
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProfileConducteurReqDTO dto, @MappingTarget ProfileConducteur entity);



}
