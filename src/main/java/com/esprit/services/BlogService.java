package com.esprit.services;

import com.esprit.models.Blog;
import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
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

    /*
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
    */

    public ObservableList<Blog> getAll() {
        ObservableList<Blog> blogs = FXCollections.observableArrayList();
        String req = "SELECT * FROM blog";
        try (PreparedStatement statement = connection.prepareStatement(req);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Blog blog = new Blog();
                int blogId = rs.getInt("blog_id");  // Get blog_id from result set
                System.out.println("Fetched blog_id: " + blogId);  // Debugging line
                blog.setId(blogId);  // Set the ID
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
    public void update(Blog blog) {
        String req = "UPDATE `blog` SET `title`=?, `content`=?, `date`=? WHERE `blog_id`=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {

            // Set title and content
            pst.setString(1, blog.getTitle());
            pst.setString(2, blog.getContent());

            // Check if the date is null and set accordingly
            if (blog.getDate() == null) {
                // If date is null, use the current date
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis()); // Current date
                pst.setDate(3, currentDate);
            } else {
                // If the date is not null, use the provided date
                java.sql.Date sqlDate = new java.sql.Date(blog.getDate().getTime());
                pst.setDate(3, sqlDate);
            }

            // Set the id parameter for the WHERE clause
            pst.setInt(4, blog.getId());

            // Execute the update
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Blog modified successfully.");
            } else {
                System.out.println("No blog found with the given ID.");
                System.out.println(blog.getId());
            }

        } catch (SQLException e) {
            System.out.println("Error during update: " + e.getMessage());
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

    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }


    public ArrayList<Blog> findByNom(String title) throws ItemNotFoundException {
        ArrayList<Blog> blog_list = this.getAllBlogs();
        ArrayList<Blog> found_items = new ArrayList<>();
        Iterator<Blog> itr = blog_list.iterator();
        while (itr.hasNext()) {
            Blog voyage = itr.next();
            if (voyage.getTitle().toLowerCase().contains(title) ) {
                //System.out.println("Voyage trouvé");
                found_items.add(voyage);
            }
        }
        if (found_items.isEmpty()) {
            throw new ItemNotFoundException("Le Blog du titre " + title + " n'existe pas.");
        }
        return found_items;
    }

    @Override
    public ArrayList<Blog> getAllBlogs() {
        //1-req SELECT
        //2-recuperation de la base de donné remplissage dans Array
        //3-retour du tableau done
        ArrayList<Blog> blogs = new ArrayList<>();
        String qry ="SELECT * FROM `blog`";
        try {
            Statement stm = connection.createStatement();

            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()){
                Blog b = new Blog();
                b.setId(rs.getInt(1));
                b.setTitle(rs.getString("title"));
                b.setDate(rs.getDate("date"));
                b.setContent(rs.getString("content"));
                blogs.add(b);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return blogs;
    }
    @Override
    public boolean deleteBlog(int voyageId){
        String qry = "DELETE FROM `voyage` WHERE `id` =?";
        try {
            PreparedStatement pstm = connection.prepareStatement(qry);
            pstm.setInt(1, voyageId);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le voyage à ID " + voyageId + " à été supprimé avec succès.");
                return true;
            } else {
                System.out.println("La suppression a échoué.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("La suppression a échoué: " + e.getMessage());
            return false;
        }
    }
}
