package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class})
public interface UserMapper {
    User toEntity(UserReqDTO dto);

    UserResDTO toDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserReqDTO dto, @MappingTarget User entity);

}