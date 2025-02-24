package com.esprit.models;

import java.util.Arrays;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String nom_utilisateur;
    private String email;
    private String mot_de_passe;
    private Role role;
    public Utilisateur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nom_utilisateur = nom_utilisateur;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
    }
    public Utilisateur( String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.nom_utilisateur = nom_utilisateur;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.role = role;
    }

    public Utilisateur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.nom_utilisateur = nom_utilisateur;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public Utilisateur(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe) {
        this.nom = nom;
        this.prenom = prenom;
        this.nom_utilisateur = nom_utilisateur;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", role=" + role +
                '}';
    }

    public enum Role {
        CLIENT,
        ADMIN,
        ORGANISATEUR,
        CONDUCTEUR
    }

}