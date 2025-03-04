package com.esprit.services;

import com.esprit.models.Blog;

import java.util.ArrayList;
import java.util.List;

public interface IBlogService<T> {

    void ajouter(Blog blog);

    void modifier(Blog blog);


    void update(Blog blog);

    boolean delete(Blog b);

    void ajouter(T t);
    void modifier(T t);
    void supprimer(T t);
    List<T> rechercher();

    ArrayList<Blog> getAllBlogs();

    boolean deleteBlog(int voyageId);
}