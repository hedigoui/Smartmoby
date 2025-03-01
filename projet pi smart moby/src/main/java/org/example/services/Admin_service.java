package org.example.services;

import org.example.models.Admin;
import org.example.models.Utilisateur;
import org.example.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Admin_service implements IAdmin_service {
    Connection connection = DataSource.getInstance().getConnection();


    @Override
    public void ajouter(Admin admin ) {
        String req = "INSERT INTO admin (id, departement) VALUES (" + admin.getId() + ", '" + admin.getDepartement() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'admin : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Admin admin, Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "UPDATE admin SET departement='" + admin.getDepartement() + "' WHERE id=" + admin.getId();
            st.executeUpdate(req1);

            String req2 = "UPDATE utilisateur SET nom='" + utilisateur.getNom() + "', prenom='" + utilisateur.getPrenom() +
                    "', nom_utilisateur='" + utilisateur.getNom_utilisateur() + "', email='" + utilisateur.getEmail() +
                    "', mot_de_passe='" + utilisateur.getMot_de_passe() + "' WHERE id=" + utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Admin et utilisateur modifiés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void supprimer(Admin admin , Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "DELETE FROM admin WHERE id="+admin.getId();
            st.executeUpdate(req1);

            String req2 = "DELETE FROM utilisateur WHERE id="+utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Admin et utilisateur supprime");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Admin> afficher() {
        List<Admin> admins = new ArrayList<>();

        // Requête pour récupérer les données des admins avec les informations du département
        String req = "SELECT u.id ,u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, a.departement " +
                "FROM utilisateur u " +
                "JOIN admin a ON u.id = a.id";  // JOIN uniquement avec les admins

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                admins.add(new Admin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getString("departement")  // Départment spécifique aux Admins
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return admins;
    }

    @Override
    public Admin getAdminById(int id) {
        Admin admin = null;
        try {
            String query = "SELECT u.id, u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, a.departement " +
                    "FROM admin a " +
                    "JOIN utilisateur u ON a.id = u.id " +
                    "WHERE a.id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getString("departement")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }





}
