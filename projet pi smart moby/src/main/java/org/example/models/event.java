package org.example.models;

import java.util.Date;

public class event {
    private int id;
    private String nom;
    private Date date;
    private String lieu;

    // ✅ Constructeur avec id (si utilisé pour la récupération depuis la BDD)
    public event(int id, String nom, Date date, String lieu) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    // ✅ Constructeur sans id (utilisé pour l'ajout d'un nouvel événement)
    public event(String nom, Date date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    // Getters et Setters
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
