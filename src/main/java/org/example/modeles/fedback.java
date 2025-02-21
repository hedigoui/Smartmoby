package org.example.modeles;

public class fedback {
        private int id;
        private String commentaire;
        private int note;

    public fedback(int id, String commentaire, int note) {
        this.id = id;
        this.commentaire = commentaire;
        this.note = note;

    }

    public fedback(String commentaire, int note) {
        this.commentaire = commentaire;
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "fedback{" +
                "id=" + id +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                '}';
    }
}
