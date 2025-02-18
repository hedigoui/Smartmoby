package com.esprit.services;

import com.esprit.models.Blog;
import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogService implements IBlogService {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Blog blog) {
        String insert = "INSERT INTO blog (title, content, date) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setDate(3, new Date(blog.getDate().getTime()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Blog ajouté avec succès !");

            } else {
                System.out.println("Échec de l'ajout du blog.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
    @FXML
    public ObservableList<Blog> getAll() {
        ObservableList<Blog> blogs = FXCollections.observableArrayList();
        String req = "SELECT * FROM blog";
        try (PreparedStatement statement = connection.prepareStatement(req);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setDate(rs.getDate("date"));
                blog.setContent(rs.getString("content"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des blogs : " + e.getMessage());
        }
        return blogs;
    }
    @Override
    public void modifier(Blog blog) {
        String req = "UPDATE blog SET title='"+blog.getTitle()+"' ,content='"+blog.getContent()+"' WHERE blog_id="+blog.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Blog modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public boolean delete(Blog b) {

        String delete = "delete from blog  where blog_id = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(delete);
            ps.setInt(1,b.getId());
            ps.executeUpdate();
            System.out.println("Post delete avec succès !");//

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public void ajouter(Object o) {

    }

    @Override
    public void modifier(Object o) {

    }

    @Override
    public void supprimer(Object o) {

    }

    @Override
    public List<Blog> rechercher() {
        List<Blog> blog = new ArrayList<>();

        String req = "SELECT * FROM blog";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                blog.add(new Blog(rs.getInt("id"), rs.getString("title"), rs.getString("content") , rs.getDate("date")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blog;
    }
}
