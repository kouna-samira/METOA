package com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileConducteurService {

    ProfileConducteurResDTO createProfile(ProfileConducteurReqDTO dto);

    ProfileConducteurResDTO updateProfile( ProfileConducteurReqDTO dto);

    ProfileConducteurResDTO getProfileByUserId(String userId);

    Page<ProfileConducteurResDTO> getAllProfiles(Pageable pageable);

    List<ProfileConducteurResDTO> getDriversByBadge(Badge badge);

    void deleteProfile(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    void deletePhotoByUserId(String userId);

    String uploadDocument(String userId, MultipartFile file, TyperDocument typerDocument);

    String downloadDocument(String userId);

    String viewDocument(String profileId) ;

    void deleteDocument(String userId);

}