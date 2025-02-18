package com.esprit.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Avis {
    private int avisId; // Match the database column name
    private StringProperty name;
    private StringProperty comment;
    private Date date;
    private int blogId; // Foreign key to associate the comment with a blog

    // Constructor without id (for insertion)
    public Avis(String name, String comment, Date date) {
        this.name = new SimpleStringProperty(name);
        this.comment = new SimpleStringProperty(comment);
        this.date = date;
    }

    // Constructor with id (for retrieval)
    public Avis(int avisId, String name, String comment, Date date) {
        this.avisId = avisId;
        this.name = new SimpleStringProperty(name);
        this.comment = new SimpleStringProperty(comment);
        this.date = date;
    }

    // Default constructor
    public Avis() {
        this.name = new SimpleStringProperty();
        this.comment = new SimpleStringProperty();
    }

    // Getters and setters
    public int getAvisId() {
        return avisId;
    }

    public void setAvisId(int avisId) {
        this.avisId = avisId;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getComment() {
        return comment.get();
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return name.get() + ": " + comment.get() + " (" + date + ")";
    }
}