package com.groupe2.METOA.gestionProfilUtiisateur.entity.authentification;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Slf4j
@Table(name = "Authentification")
public class Authentification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String authentificationId;
    private String login;
    private String motDePasse;
    private LocalDateTime dernierAcces;
    private CompteurActivite compteurActivite;
    @OneToOne
    private User user;
}
