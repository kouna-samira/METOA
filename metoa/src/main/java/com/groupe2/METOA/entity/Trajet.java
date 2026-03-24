package com.groupe2.METOA.entity;

import com.metoa.entity.Conducteur;
import com.metoa.entity.Reservation;
import com.metoa.entity.StatutTrajet;
import com.metoa.entity.Vehicule;
import com.metoa.entity.Ville;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trajet",
        indexes = {
                @Index(name = "idx_depart", columnList = "ville_depart_id"),
                @Index(name = "idx_arrivee", columnList = "ville_arrivee_id"),
                @Index(name = "idx_date", columnList = "date_depart")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conducteur_id", nullable = false)
    @NotNull
    private Conducteur conducteur;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", nullable = false)
    @NotNull
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name = "ville_depart_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private com.metoa.entity.Ville villeDepart;

    @ManyToOne
    @JoinColumn(name = "ville_arrivee_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private Ville villeArrivee;

    @Column(name = "date_depart", nullable = false)
    @NotNull
    private LocalDateTime dateDepart;

    @Min(1)
    @Column(nullable = false)
    private int placesDisponibles;

    @Column(nullable = false)
    private double prix;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private com.metoa.entity.StatutTrajet statut;

    @Column(name = "latitude_depart")
    private Double latitudeDepart; // optionnel, sinon on prend celle de la ville

    @Column(name = "longitude_depart")
    private Double longitudeDepart;

    @Column(name = "latitude_arrivee")
    private Double latitudeArrivee;

    @Column(name = "longitude_arrivee")
    private Double longitudeArrivee;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    // Méthode utilitaire pour passer de brouillon à publié
    public void setPublie(boolean publie) {
        this.statut = publie ? com.metoa.entity.StatutTrajet.PUBLIE : StatutTrajet.BROUILLON;
    }
}
