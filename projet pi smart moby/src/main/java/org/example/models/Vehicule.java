package org.example.models;



public class Vehicule {
    private int id;  // Auto-incrémenté, ne pas inclure dans l'ajout
    private int capacite;
    private String type, statut;
    private boolean dispo;  // Utilisation de boolean pour la disponibilité
    private Integer conducteurId;  // Nouveau champ
    private String conducteurNom;  // Pour l'affichage
    private int conducteurTelephone; // Nouveau champ

    public Vehicule() {}

    // Constructeur pour récupérer depuis la BD (avec id)
    public Vehicule(int id, String type, int capacite, String statut, boolean dispo, Integer conducteurId, String conducteurNom, int conducteurTelephone) {
        this.id = id;
        this.type = type;
        this.capacite = capacite;
        this.statut = statut;
        this.dispo = dispo;
        this.conducteurId = conducteurId;
        this.conducteurNom = conducteurNom;
        this.conducteurTelephone = conducteurTelephone;
    }

    // Constructeur pour l'ajout (sans id car auto-incrémenté)
    public Vehicule(String type, int capacite, String statut, boolean dispo, Integer conducteurId) {
        this.type = type;
        this.capacite = capacite;
        this.statut = statut;
        this.dispo = dispo;
        this.conducteurId = conducteurId;
    }

    public Vehicule(String type, int capacite, String statut, boolean dispo) {
        this.type = type;
        this.capacite = capacite;
        this.statut = statut;
        this.dispo = dispo;
    }

    // Méthode pour récupérer la disponibilité sous forme de String ("Oui" ou "Non")
    public String getDisponibilite() {
        return dispo ? "Oui" : "Non";  // Retourne "Oui" si dispo est true, "Non" sinon
    }

    // Getters & Setters
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
    public Integer getConducteurId() { return conducteurId; }
    public void setConducteurId(Integer conducteurId) { this.conducteurId = conducteurId; }
    public String getConducteurNom() { return conducteurNom; }
    public void setConducteurNom(String conducteurNom) { this.conducteurNom = conducteurNom; }
    public int getConducteurTelephone() { return conducteurTelephone; }
    public void setConducteurTelephone(int conducteurTelephone) { this.conducteurTelephone = conducteurTelephone; }

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Capacité: " + capacite + ", Statut: " + statut + ", Dispo: " + (dispo ? "Oui" : "Non") +
                ", Conducteur: " + (conducteurNom != null ? conducteurNom : "Non assigné") + ", Email : " + conducteurTelephone;
    }
}