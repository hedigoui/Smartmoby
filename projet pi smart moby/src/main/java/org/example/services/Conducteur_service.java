package org.example.services;

import org.example.models.Client;
import org.example.models.Conducteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.models.Utilisateur;
import org.example.utils.DataSource;

public class Conducteur_service implements IConducteur_service{
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Conducteur conducteur) {
        String req = "INSERT INTO conducteur (id, numero_permis) VALUES (" + conducteur.getId_conducteur() + ", '" + conducteur.getNumero_permis() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Conducteur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de le conducteur : " + e.getMessage());
        }

    }

    @Override
    public void modifier(Conducteur conducteur , Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "UPDATE conducteur SET numero_permis='" + conducteur.getNumero_permis() + "' WHERE id=" + conducteur.getId();
            st.executeUpdate(req1);

            String req2 = "UPDATE utilisateur SET nom='" + utilisateur.getNom() + "', prenom='" + utilisateur.getPrenom() +
                    "', nom_utilisateur='" + utilisateur.getNom_utilisateur() + "', email='" + utilisateur.getEmail() +
                    "', mot_de_passe='" + utilisateur.getMot_de_passe() + "' WHERE id=" + utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Conducteur et utilisateur modifiés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Conducteur conducteur,Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "DELETE FROM conducteur WHERE id="+conducteur.getId();
            st.executeUpdate(req1);

            String req2 = "DELETE FROM utilisateur WHERE id="+utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Conducteur et utilisateur supprime");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Conducteur> afficher() {
        List<Conducteur> conducteurs = new ArrayList<>();

        // Requête pour récupérer les données des admins avec les informations du département
        String req = "SELECT u.id, u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, c.numero_permis " +
                "FROM utilisateur u " +
                "JOIN conducteur c ON u.id = c.id";
        // JOIN uniquement avec les admins

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                conducteurs.add(new Conducteur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("numero_permis")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conducteurs;
    }

    @Override
    public Conducteur getConducteurById(int id) {
        Conducteur conducteur = null;
        try {
            String query = "SELECT u.id, u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role , c.numero_permis " +
                    "FROM conducteur c " +
                    "JOIN utilisateur u ON c.id = u.id " +
                    "WHERE c.id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                conducteur = new Conducteur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("numero_permis")

                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conducteur;
    }
}
