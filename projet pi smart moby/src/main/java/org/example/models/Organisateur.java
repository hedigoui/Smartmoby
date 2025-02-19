package org.example.models;
import java.util.ArrayList;
import java.util.List;


public class Organisateur extends Utilisateur{
    private int id_organisateur;
    private int num_badge;

    public Organisateur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_organisateur, int num_badge) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_organisateur = id_organisateur;
        this.num_badge = num_badge;
    }

    public Organisateur(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_organisateur, int num_badge) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_organisateur = id_organisateur;
        this.num_badge = num_badge;
    }

    public Organisateur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int num_badge) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.num_badge = num_badge;
    }

    public Organisateur(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int num_badge) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.num_badge = num_badge;
    }

    public int getId_organisateur() {
        return id_organisateur;
    }

    public void setId_organisateur(int id_organisateur) {
        this.id_organisateur = id_organisateur;
    }

    public int getNum_badge() {
        return num_badge;
    }

    public void setNum_badge(int num_badge) {
        this.num_badge = num_badge;
    }

    @Override
    public String toString() {
        return "Organisateur{" +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", nom_utilisateur='" + getNom_utilisateur() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", mot_de_passe='" + getMot_de_passe() + '\'' +
                ", role=" + getRole() + '\'' +
                ", num_badge=" + num_badge +
                '}';
    }
}
