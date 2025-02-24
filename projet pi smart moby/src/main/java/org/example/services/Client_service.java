package org.example.services;

import org.example.models.Admin;
import org.example.models.Client;
import org.example.models.Utilisateur;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Client_service implements IClient_service {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Client client) {
        String req = "INSERT INTO client (id) VALUES (" + client.getId() + ")";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("client ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de le client : " + e.getMessage());
        }
    }

    @Override
    public void modifier(Client client, Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();


            String req2 = "UPDATE utilisateur SET nom='" + utilisateur.getNom() + "', prenom='" + utilisateur.getPrenom() +
                    "', nom_utilisateur='" + utilisateur.getNom_utilisateur() + "', email='" + utilisateur.getEmail() +
                    "', mot_de_passe='" + utilisateur.getMot_de_passe() + "' WHERE id=" + utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Client et utilisateur modifiés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(Client client, Utilisateur utilisateur) {
        try {
            Statement st = connection.createStatement();

            String req1 = "DELETE FROM client WHERE id="+client.getId();
            st.executeUpdate(req1);

            String req2 = "DELETE FROM utilisateur WHERE id="+utilisateur.getId();
            st.executeUpdate(req2);

            System.out.println("Client et utilisateur supprime");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Client> afficher() {
        List<Client> clients = new ArrayList<>();

        // Requête pour récupérer les données des admins avec les informations du département
        String req = "SELECT u.id , u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role " +
                "FROM utilisateur u " +
                "JOIN client c ON u.id = c.id";  // JOIN uniquement avec les admins

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase())
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return clients;
    }
}
