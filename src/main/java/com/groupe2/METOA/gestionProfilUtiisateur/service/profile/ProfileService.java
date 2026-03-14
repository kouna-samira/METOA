package com.groupe2.METOA.gestionProfilUtiisateur.service.profile;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    ProfileResDTO createProfile(ProfileReqDTO dto);

    ProfileResDTO getProfileByUserId(String userId);

    Page<ProfileResDTO> getAllProfiles(Pageable pageable);

    ProfileResDTO updateProfileByUserId( ProfileReqDTO dto);

    void deleteProfileByUserId(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    void deletePhotoByUserId(String userId);
}