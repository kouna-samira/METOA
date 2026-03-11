package com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur;

import com.groupe2.METOA.gestionProfileUtilisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.profil.TyperDocument;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileConducteurService {

    ProfileConducteurResDTO createOrUpdateProfile(ProfileConducteurReqDTO dto) throws Exception;

    ProfileConducteurResDTO getProfileByUserId(String userId);

    ProfileConducteurResDTO updateProfileByUserId(String userId, ProfileConducteurReqDTO dto, MultipartFile file);

    void deleteProfileByUserId(String userId);

    String uploadPhotoByUserId(String userId, MultipartFile file);

    String getPhotoByUserId(String userId);

    void deletePhotoByUserId(String userId);

    String uploadDocument(String userId, MultipartFile file, TyperDocument typerDocument);

    String downloadDocument(String userId);

    String viewDocument(String profileId) ;
    void deleteDocument(String userId);

}