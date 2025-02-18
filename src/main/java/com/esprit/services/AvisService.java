package com.esprit.services;

import com.esprit.models.Avis;
import com.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements IAvisService<Avis> { // Assuming IService is your generic service interface
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Avis avis) {
        String req = "INSERT INTO avis (blog_id, name, comment, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, avis.getBlogId());
            ps.setString(2, avis.getName());
            ps.setString(3, avis.getComment());
            ps.setTimestamp(4, new Timestamp(avis.getDate().getTime()));

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Comment added to the database: " + avis.getComment());
            } else {
                System.out.println("Failed to add comment to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding comment to the database: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Avis avis) {
        String req = "UPDATE avis SET name=?, comment=?, date=? WHERE avis_id=?"; // Use avis_id instead of id
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, avis.getName());
            ps.setString(2, avis.getComment());
            ps.setTimestamp(3, new Timestamp(avis.getDate().getTime()));
            ps.setInt(4, avis.getAvisId()); // Use getAvisId() instead of getId()

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Avis modifié avec succès.");
            } else {
                System.out.println("Aucun avis trouvé avec l'ID: " + avis.getAvisId());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'avis: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(Avis avis) {
        String req = "DELETE FROM avis WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, avis.getAvisId());
            pst.executeUpdate();
            System.out.println("Avis supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter(Object o) {
        // Implement if needed
    }

    @Override
    public void modifier(Object o) {
        // Implement if needed
    }

    @Override
    public void supprimer(Object o) {
        // Implement if needed
    }

    @Override
    public List<Avis> rechercher() {
        List<Avis> avisList = new ArrayList<>();

        String req = "SELECT * FROM avis";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("comment"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return avisList;
    }

    public ObservableList<Avis> getCommentsByBlogId(int blogId) {
        ObservableList<Avis> comments = FXCollections.observableArrayList();
        String query = "SELECT * FROM avis WHERE blog_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, blogId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Avis avis = new Avis(
                        rs.getInt("avis_id"), // Match the database column name
                        rs.getString("name"),
                        rs.getString("comment"),
                        rs.getDate("date")
                );
                avis.setBlogId(rs.getInt("blog_id"));
                comments.add(avis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}