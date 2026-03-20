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
    @Mapping(target = "profileConducteurId", ignore = true)
    @Mapping(target = "dateCreationProfile", ignore = true)
    @Mapping(target = "dateModificationProfile", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    ProfileConducteur toEntity(ProfileConducteurReqDTO dto);


    // modifier
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "profileConducteurId", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateEntityFromDto(ProfileConducteurReqDTO dto, @MappingTarget ProfileConducteur entity);


    // user
    @Named("mapUser")
    default User mapUser(String userId) {
        if (userId == null) return null;
        User user = new User();
        user.setIdUser(userId);
        return user;
    }
}
