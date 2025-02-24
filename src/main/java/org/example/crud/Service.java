package org.example.crud;

class Service {
    private int id;
    private String nom;
    private String description;
    private double tarif;

    public Service(int id, String nom, String description, double tarif) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.tarif = tarif;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public double getTarif() { return tarif; }
}