package com.groupe2.METOA.gestionProfilUtiisateur.classMapp;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.message.MessageResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.message.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {


    @Mapping(source = "sender.idUser", target = "senderId")
    @Mapping(source = "receiver.idUser", target = "receiverId")
    MessageResDTO toResDTO(Message message);

    List<MessageResDTO> toResDTOList(List<Message> messages);}
