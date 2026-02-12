package com.groupe2.METOA.Entity;

import jakarta.persistence.*;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> cc79e66 (fin des entites)
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
<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

>>>>>>> cc79e66 (fin des entites)

    public Client() {
    }

<<<<<<< HEAD
=======
    public Client(String idclient, String nom, String prenom, String email, String telephone, List<Reservation> reservations) {
        this.idclient = idclient;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.reservations = reservations;
    }

>>>>>>> cc79e66 (fin des entites)
    public Client(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

<<<<<<< HEAD
=======
    public String getIdclient() {
        return idclient;
    }

    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

>>>>>>> cc79e66 (fin des entites)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
