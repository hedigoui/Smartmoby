package org.example.models;

public class Conducteur extends Utilisateur{
    private int id_conducteur;
    private int numero_permis;

    public Conducteur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_conducteur, int numero_permis) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_conducteur = id_conducteur;
        this.numero_permis = numero_permis;
    }

    public Conducteur(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int id_conducteur, int numero_permis) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.id_conducteur = id_conducteur;
        this.numero_permis = numero_permis;
    }

    public Conducteur(int id, String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int numero_permis) {
        super(id, nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.numero_permis = numero_permis;
    }

    public Conducteur(String nom, String prenom, String nom_utilisateur, String email, String mot_de_passe, Role role, int numero_permis) {
        super(nom, prenom, nom_utilisateur, email, mot_de_passe, role);
        this.numero_permis = numero_permis;
    }

    public Conducteur(int id , String nom , String prenom , String email ) {
        super(id , nom , prenom , email);
    }

    public Conducteur(int id , String nom , String prenom  , int numero_permis) {
        super(id , nom , prenom );
        this.numero_permis=numero_permis;
    }


    public int getId_conducteur() {
        return id_conducteur;
    }

    public void setId_conducteur(int id_conducteur) {
        this.id_conducteur = id_conducteur;
    }

    public int getNumero_permis() {
        return numero_permis;
    }

    public void setNumero_permis(int numero_permis) {
        this.numero_permis = numero_permis;
    }

    @Override
    public String
    toString() {
        return "Conducteur{" +
                "id_conducteur=" + id_conducteur +
                ", numero_permis=" + numero_permis +
                '}';
    }
}
