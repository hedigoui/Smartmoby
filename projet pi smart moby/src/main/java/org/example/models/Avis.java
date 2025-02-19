package org.example.models;

import java.util.Date;

public class Avis {
    private int id;
    private String name;
    private String comment;
    private Date date;

    // Constructor without id (for insertion)
    public Avis(String name, String comment, Date date) {
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    // Constructor with id (for retrieval)
    public Avis(int id, String name, String comment, Date date) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.date = date;
    }

    public Avis() {

    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Avis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}