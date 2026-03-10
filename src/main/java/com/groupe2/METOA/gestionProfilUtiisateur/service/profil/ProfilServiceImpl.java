package com.groupe2.METOA.gestionProfilUtiisateur.service.profil;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.ProfilNotFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfilRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class ProfilServiceImpl implements ProfilService{
    private final ProfilMapper profilMapper;
    private final ProfilRepo profilRepo;
    private final UserRepo userRepo;

    public ProfilServiceImpl(ProfilMapper profilMapper, ProfilRepo profilRepo, UserRepo userRepo) {
        this.profilMapper = profilMapper;
        this.profilRepo = profilRepo;
        this.userRepo = userRepo;
    }

    @Override
    public ProfilResDTO getProfilByUserId(String userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new UserNoteFoundException(userId));

        if (user.getProfil() == null) {
            throw new ProfilNotFoundException("Cet utilisateur n'a pas encore de profil");
        }
        return this.profilMapper.toResDTO(user.getProfil());
    }

    @Override
    public void updateProfilByUserId(String userId, ProfilReqDTO profilReqDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNoteFoundException(userId));

        if (user.getProfil() == null) {
            throw new ProfilNotFoundException("Impossible de modifier : aucun profil existant");
        }


        profilMapper.updateProfilFromDTO(profilReqDTO, user.getProfil());

        this.userRepo.save(user);


    }

    @Override
    public void deleteProfilByUserId(String userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new UserNoteFoundException(userId));


        if (user.getProfil() == null) {
            throw new RuntimeException("Aucun profil à supprimer");
        }
        user.setProfil(null);
        this.userRepo.save(user);

    }
}
