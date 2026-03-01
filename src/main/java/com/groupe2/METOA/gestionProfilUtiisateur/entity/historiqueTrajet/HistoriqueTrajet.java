package com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "historique_Trajets")
public class HistoriqueTrajet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "historique_trajet_id", length = 36, nullable = false)
    private String historiqueTrajetId;

    @Column(name = "trajet_id", nullable = false)
    private String trajetId;

    @Column(name = "date_trajet", nullable = false)
    private LocalDate dateTrajet;

    @Column(name = "role_user_trajet", nullable = false)
    private String roleUserInTrajet;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_trajet", nullable = false)
    private StatusTrajet statusTrajet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id_user",
            referencedColumnName = "id_user",
            nullable = false
    )
    private User user;

}
