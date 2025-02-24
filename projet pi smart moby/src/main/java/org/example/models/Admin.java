package org.example.models;

import java.util.Arrays;

public class Admin extends Utilisateur {
    private int id_admin;
    private String departement;

    public Admin(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_admin, String departement) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_admin = id_admin;
        this.departement = departement;
    }

    public Admin(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_admin, String departement) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_admin = id_admin;
        this.departement = departement;
    }

    public Admin(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, String departement) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.departement = departement;
    }

    public Admin(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, String departement) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.departement = departement;
    }



    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "Admin{" +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nom_utilisateur='" + getNom_utilisateur() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", mot_de_passe='" + getMot_de_passe() + '\'' +
                ", role=" + getRole() + '\'' +
                "id_admin=" + id_admin + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}
