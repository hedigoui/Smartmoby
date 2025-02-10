package com.esprit.services;

import com.esprit.models.Blog;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BlogService implements IBlogService {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Blog blog) {
        String req = "INSERT INTO blog (title, content, date) VALUES ('"+blog.getTitle()+"', '"+blog.getContent()+"', '" + blog.getDate()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Blog ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Blog blog) {
        String req = "UPDATE blog SET title='"+blog.getTitle()+"' ,content='"+blog.getContent()+"' WHERE id="+blog.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Blog modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Blog blog) {
        String req = "DELETE FROM blog WHERE id="+blog.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Blog supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
