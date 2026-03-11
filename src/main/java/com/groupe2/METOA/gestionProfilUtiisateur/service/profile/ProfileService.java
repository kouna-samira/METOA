package com.groupe2.METOA.gestionProfilUtiisateur.service.profile;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    ProfileResDTO createProfile(ProfileReqDTO dto);

    ProfileResDTO getProfileByUserId(String userId);

    List<ProfileResDTO> getAllProfiles();

    ProfileResDTO updateProfileByUserId(String userId, ProfileReqDTO dto);

    void deleteProfileByUserId(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    void deletePhotoByUserId(String userId);
}