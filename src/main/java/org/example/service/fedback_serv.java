package org.example.service;

import org.example.modeles.fedback;
import org.example.utilis.datasrc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class fedback_serv implements Ifedback {
    private final Connection connection = datasrc.getInstance().getConnection();

    @Override
    public void ajouter(fedback fedback) {
        String req = "INSERT INTO fedback (commentaire, note, id_event) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, fedback.getCommentaire());
            pst.setInt(2, fedback.getNote());
            pst.setInt(3, fedback.getIdEvent()); // Utiliser directement l'ID de l'événement

            pst.executeUpdate();
            System.out.println("✅ Feedback ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du feedback : " + e.getMessage());
            e.printStackTrace(); // Afficher la StackTrace pour mieux comprendre l'erreur
        }
    }

    private int getIdEventFromEvent(String eventName) {
        String query = "SELECT id_event FROM evenment WHERE nom = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, eventName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_event");  // Retourne l'id de l'événement
            } else {
                System.out.println("Aucun événement trouvé avec le nom : " + eventName);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'id de l'événement : " + e.getMessage());
        }
        return -1;  // Retourne -1 si l'événement n'est pas trouvé
    }

    public fedback getFeedbackById(int id) {
        String req = "SELECT id, commentaire, note, id_event FROM fedback WHERE id = ?";
        fedback fb = null;

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                fb = new fedback(rs.getInt("id"),
                        rs.getString("commentaire"),
                        rs.getInt("note"),
                        rs.getInt("id_event")); // On récupère bien l'id_event
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du feedback : " + e.getMessage());
        }

        return fb;
    }


    public List<fedback> getFeedbacksByEvent(int eventId) {
        List<fedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM fedback WHERE id_event = ?";

        System.out.println("🔍 Recherche des feedbacks pour l'événement ID: " + eventId);

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, eventId);
            System.out.println("📌 Exécution de la requête : " + pst.toString());  // Afficher la requête SQL

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String commentaire = rs.getString("commentaire");
                int note = rs.getInt("note");
                feedbackList.add(new fedback(id, commentaire, note, eventId));

                System.out.println("✅ Feedback trouvé : " + commentaire + " (Note: " + note + ")");
            }

        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL lors de la récupération des feedbacks : " + e.getMessage());
            e.printStackTrace();  // Afficher l'erreur complète
        }

        System.out.println("📊 Total feedbacks récupérés : " + feedbackList.size());
        return feedbackList;
    }



    public void modifier(fedback f) {
        String req = "UPDATE fedback SET commentaire = ?, note = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, f.getCommentaire());
            pst.setInt(2, f.getNote());
            pst.setInt(3, f.getId()); // On ne touche pas à id_event
            pst.executeUpdate();
            System.out.println("Feedback modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du feedback : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(fedback fedback) {
        String req = "DELETE FROM fedback WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, fedback.getId());
            pst.executeUpdate();
            System.out.println("Feedback supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du feedback : " + e.getMessage());
        }
    }

    @Override
    public List<fedback> afficher() {
        List<fedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM fedback";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String commentaire = rs.getString("commentaire");
                int note = rs.getInt("note");
                int id_event = rs.getInt("id_event");
                feedbackList.add(new fedback(id, commentaire, note, id_event));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des feedbacks : " + e.getMessage());
        }
        return feedbackList;
    }

    // Static method to retrieve feedbacks by event id
    public static List<fedback> recupererParEvenement(int id_event) {
        if (id_event < 0) {
            System.out.println("ID invalide");
            return new ArrayList<>(); // Retourne une liste vide
        }
        fedback_serv service = new fedback_serv();
        return service.getFeedbacksByEvent(id_event); // Call instance method
    }
}
