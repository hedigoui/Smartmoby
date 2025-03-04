package org.example.models;

public class Produit {
    private int id_produit;
    private String nom;
    private String type;
    private double prix;

    public Produit(int id_produit, String nom, String type, double prix) {
        this.id_produit = id_produit;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
    }

    public int getId() {
        return id_produit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return id_produit + " -- " + nom + "--" + type + " -- " + prix + "€";
    }
}
