package com.esprit.models;

public class Vehicule {
    private int id,capacite;
    private String type,statut;
    private boolean dispo;
    public Vehicule() {
    }

    public Vehicule(int id,String type, int capacite,  String statut, boolean dispo) {
        this.id=id;
        this.type = type;
        this.capacite = capacite;
        this.statut = statut;
        this.dispo = dispo;

    }

    public Vehicule( String type,int capacite, String statut, boolean dispo) {
        this.type = type;
        this.capacite = capacite;
        this.statut = statut;
        this.dispo = dispo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public boolean isDispo() { return dispo; }
    public void setDispo(boolean dispo) { this.dispo = dispo; }
    @Override
    public String toString() { return type + " " + capacite + " " + statut; }
}
