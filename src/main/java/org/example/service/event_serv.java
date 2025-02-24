package org.example.service;

import org.example.modeles.event;
import org.example.utilis.datasrc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class event_serv implements Ievent {
    private Connection connection;

    // ✅ Constructeur qui initialise correctement la connexion
    public event_serv() {
        this.connection = datasrc.getInstance().getConnection();
        if (this.connection == null) {
            throw new RuntimeException("❌ Erreur : Impossible d'obtenir la connexion à la base de données !");
        }
    }

    // Méthode pour récupérer tous les événements
    public List<event> getAllEvents() {
        List<event> events = new ArrayList<>();
        String req = "SELECT id_event, nom, date, lieu FROM evenment";

        System.out.println("🔍 Exécution de la requête : " + req);  // Log de la requête exécutée

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                int id = rs.getInt("id_event");
                String nom = rs.getString("nom");
                Date date = rs.getDate("date");
                String lieu = rs.getString("lieu");

                events.add(new event(id, nom, date, lieu));
            }

            if (events.isEmpty()) {
                System.out.println("⚠️ Aucun événement trouvé dans la base de données.");
            } else {
                System.out.println("✅ " + events.size() + " événement(s) trouvé(s).");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL lors de la récupération : " + e.getMessage());
        }

        return events;
    }


    // Ajouter un événement à la base de données
    @Override
    public void ajouter(event event) {
        String req = "INSERT INTO evenment (nom, date, lieu) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, event.getNom());
            pst.setDate(2, new java.sql.Date(event.getDate().getTime()));  // ✅ Conversion correcte de la date
            pst.setString(3, event.getLieu());

            pst.executeUpdate();
            System.out.println("✅ Événement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    // Modifier un événement dans la base de données
    @Override
    public void modifier(event event) {
        String req = "UPDATE evenment SET nom=?, date=?, lieu=? WHERE id_event=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, event.getNom());
            pst.setDate(2, new java.sql.Date(event.getDate().getTime()));
            pst.setString(3, event.getLieu());
            pst.setInt(4, event.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Événement modifié avec succès !");
            } else {
                System.out.println("⚠ Aucun événement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer un événement de la base de données
    @Override
    public boolean supprimer(event event) {
        String req = "DELETE FROM evenment WHERE id_event=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, event.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Événement supprimé avec succès !");
                return true;
            } else {
                System.out.println("⚠ Aucun événement trouvé avec cet ID.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL lors de la suppression : " + e.getMessage());
            return false;
        }
    }

    // Afficher la liste des événements
    @Override
    public List<event> afficher() {
        List<event> events = getAllEvents();

        // Si la liste est vide, afficher une alerte ou message d'erreur
        if (events.isEmpty()) {
            System.out.println("⚠️ Aucun événement trouvé en base.");
        }

        return events;
    }
}
