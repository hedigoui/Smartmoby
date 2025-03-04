package com.esprit.models;

import java.util.Date;

public class Blog {

    private int id;
    private String title;
    private String content;
    private Date date;


    public Blog(int id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;

    }

    public Blog(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Blog() {

    }

    public Blog(String blogTitle, String blogContent) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String nom) {
        this.title = nom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String prenom) {this.content = prenom;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}