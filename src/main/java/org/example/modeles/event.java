package org.example.modeles;

import java.util.Date;

public class event {
    private int id;
    private String nom;
    private Date date;
    private String lieu;

    public event(int id, String nom, Date date, String lieu) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }

    public event(String nom, Date date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
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
