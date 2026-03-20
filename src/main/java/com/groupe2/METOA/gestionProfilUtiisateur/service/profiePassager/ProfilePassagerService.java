package com.groupe2.METOA.gestionProfilUtiisateur.service.profiePassager;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfilePassagerService {

    ProfilePassagerResDTO create(ProfilePassagerReqDTO dto) ;

    ProfilePassagerResDTO getProfileByUserId(String userId);

    Page<ProfilePassagerResDTO> getAllPassage(Pageable pageable);

    ProfilePassagerResDTO updateProfileByUserId( ProfilePassagerReqDTO dto);

    void deleteProfileByUserId(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    String updatePhotoByUserId(String userId, MultipartFile file);

    void deletePhotoByUserId(String userId);
}