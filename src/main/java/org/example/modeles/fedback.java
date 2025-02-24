package org.example.modeles;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class fedback {
    private IntegerProperty id;
    private StringProperty commentaire;
    private IntegerProperty note;
    private IntegerProperty id_event;

    // Constructeur complet
    public fedback(int id, String commentaire, int note, int id_event) {
        this.id = new SimpleIntegerProperty(id);
        this.commentaire = new SimpleStringProperty(commentaire);
        this.note = new SimpleIntegerProperty(note);
        this.id_event = new SimpleIntegerProperty(id_event);
    }

    // Constructeur sans ID (utile pour l'ajout)
    public fedback(String commentaire, int note, int id_event) {
        this.commentaire = new SimpleStringProperty(commentaire);
        this.note = new SimpleIntegerProperty(note);
        this.id_event = new SimpleIntegerProperty(id_event);
    }

    // Getters et Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getCommentaire() {
        return commentaire.get();
    }

    public void setCommentaire(String commentaire) {
        this.commentaire.set(commentaire);
    }

    public int getNote() {
        return note.get();
    }

    public void setNote(int note) {
        this.note.set(note);
    }

    public int getIdEvent() {
        return id_event.get();
    }

    public void setIdEvent(int idEvent) {
        this.id_event.set(idEvent);
    }

    // Méthodes pour les propriétés
    public StringProperty commentaireProperty() {
        return commentaire;
    }

    public IntegerProperty noteProperty() {
        return note;
    }

    public IntegerProperty idEventProperty() {
        return id_event;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    // Méthode toString pour affichage
    @Override
    public String toString() {
        return "fedback{" +
                "id=" + id.get() +
                ", commentaire='" + commentaire.get() + '\'' +
                ", note=" + note.get() +
                ", id_event=" + id_event.get() +
                '}';
    }
}
