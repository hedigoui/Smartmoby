package org.example.services;

import org.example.models.Conducteur;
import org.example.models.Organisateur;
import org.example.models.Utilisateur;
import org.example.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Organisateur_service implements IOrganisateur_service {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Organisateur organisateur) {
        String req = "INSERT INTO organisateur (id, num_badge) VALUES (" + organisateur.getId() + ", '" + organisateur.getNum_badge() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("organisateur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'admin : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Organisateur organisateur, Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "UPDATE organisateur SET num_badge='" + organisateur.getNum_badge() + "' WHERE id=" + organisateur.getId();
            st.executeUpdate(req1);

            String req2 = "UPDATE utilisateur SET nom='" + utilisateur.getNom() + "', prenom='" + utilisateur.getPrenom() +
                    "', nom_utilisateur='" + utilisateur.getNom_utilisateur() + "', email='" + utilisateur.getEmail() +
                    "', mot_de_passe='" + utilisateur.getMot_de_passe() + "' WHERE id=" + utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Organisateur et utilisateur modifiés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Organisateur organisateur, Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "DELETE FROM organisateur WHERE id="+organisateur.getId();
            st.executeUpdate(req1);

            String req2 = "DELETE FROM utilisateur WHERE id="+utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Organisateur et utilisateur supprime");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Organisateur> afficher() {
        List<Organisateur> organisateurs = new ArrayList<>();

        // Requête pour récupérer les données des admins avec les informations du département
        String req = "SELECT u.id , u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, o.num_badge " +
                "FROM utilisateur u " +
                "JOIN organisateur o ON u.id = o.id";
        // JOIN uniquement avec les admins

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                organisateurs.add(new Organisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("num_badge")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return organisateurs;
    }

    @Override
    public Organisateur getOrganisateurById(int id) {
        Organisateur organisateur = null;
        try {
            String query = "SELECT u.id, u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role , o.num_badge " +
                    "FROM organisateur o " +
                    "JOIN utilisateur u ON o.id = u.id " +
                    "WHERE o.id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                organisateur = new Organisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("num_badge")

                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return organisateur;
    }
}
