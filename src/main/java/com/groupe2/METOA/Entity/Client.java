package com.groupe2.METOA.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idclient;
    private String nom;
    private String prenom;
    private String email;
    @Column(name = "phone",nullable = false,unique = true)
    private String telephone;


    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;




}
