package org.example.models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Date;

public class event {
    private int id;
    private StringProperty nom;  // Change String en StringProperty
    private Date date;
    private String lieu;

    // ✅ Constructeur avec id (si utilisé pour la récupération depuis la BDD)
    public event(int id, String nom, Date date, String lieu) {
        this.id = id;
        this.nom = new SimpleStringProperty(nom);  // Initialisation avec SimpleStringProperty
        this.date = date;
        this.lieu = lieu;
    }

    // ✅ Constructeur sans id (utilisé pour l'ajout d'un nouvel événement)
    public event(String nom, Date date, String lieu) {
        this.nom = new SimpleStringProperty(nom);  // Initialisation avec SimpleStringProperty
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
        return nom.get();  // Récupérer la valeur de nom
    }

    public void setNom(String nom) {
        this.nom.set(nom);  // Mettre à jour la valeur de nom
    }

    public StringProperty nomProperty() {
        return nom;  // Retourner la propriété StringProperty
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
                ", nom='" + nom.get() + '\'' +  // Utilise nom.get() pour afficher la valeur
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
