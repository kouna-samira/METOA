package com.groupe2.METOA.gestionProfilUtiisateur.service.profiePassager;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfilePassagerService {

    ProfilePassagerResDTO createOrUpdateProfile(ProfilePassagerReqDTO dto) ;

    ProfilePassagerResDTO getProfileByUserId(String userId);

    ProfilePassagerResDTO updateProfileByUserId( ProfilePassagerReqDTO dto);

    void deleteProfileByUserId(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    String updatePhotoByUserId(String userId, MultipartFile file);

    void deletePhotoByUserId(String userId);
}