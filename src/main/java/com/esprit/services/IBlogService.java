package com.esprit.services;

import com.esprit.models.Blog;

import java.util.List;

public interface IBlogService<T> {

    void ajouter(Blog blog);

    void modifier(Blog blog);

    void supprimer(Blog blog);

    void ajouter(T t);
    void modifier(T t);
    void supprimer(T t);
    List<T> rechercher();
}