package com.groupe2.METOA.entity;

import com.metoa.entity.Passager;
import com.metoa.entity.ReservationStatut;
import com.metoa.entity.Trajet;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    @ManyToOne
    @JoinColumn(name = "passager_id", nullable = false)
    private Passager passager;

    @Column(name = "places_reservees", nullable = false)
    private int placesReservees;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatut statut;

    @Column(name = "date_reservation", nullable = false)
    private LocalDateTime dateReservation;
}
