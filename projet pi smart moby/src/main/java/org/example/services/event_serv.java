package org.example.services;

import org.example.models.event;
import org.example.utils.DataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class event_serv implements Ievent {
    Connection connection = DataSource.getInstance().getConnection();
    @Override
    public void ajouter(event event) {
        String req = "INSERT INTO evenment (nom, date, lieu) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, event.getNom());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(event.getDate());
            pst.setString(2, formattedDate);

            pst.setString(3, event.getLieu());

            pst.executeUpdate();
            System.out.println("Événement ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(event event) {
        // Requête SQL avec des paramètres pour éviter l'injection SQL
        String req = "UPDATE evenment SET nom=?, date=?, lieu=? WHERE id_event=?";

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            // Remplir les paramètres de la requête
            pst.setString(1, event.getNom());  // Nom de l'événement
            pst.setDate(2, new java.sql.Date(event.getDate().getTime()));  // Date de l'événement
            pst.setString(3, event.getLieu());  // Lieu de l'événement
            pst.setInt(4, event.getId());  // ID de l'événement (utilisé pour la clause WHERE)

            // Exécuter la mise à jour
            int rowsAffected = pst.executeUpdate();

            // Vérifier si des lignes ont été modifiées
            if (rowsAffected > 0) {
                System.out.println("Événement modifié avec succès");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'événement : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(event event) {
        // Requête SQL avec un paramètre pour éviter l'injection SQL
        String req = "DELETE FROM evenment WHERE id_event=?";

        try (PreparedStatement pst = connection.prepareStatement(req)) {
            // Définir le paramètre pour la requête
            pst.setInt(1, event.getId());  // L'id de l'utilisateur à supprimer

            // Exécuter la requête
            int rowsAffected = pst.executeUpdate();

            // Vérifier si une ligne a été supprimée
            if (rowsAffected > 0) {
                System.out.println("event supprimé avec succès");
            } else {
                System.out.println("Aucun event trouvé avec cet ID");
            }

        } catch (SQLException e) {
            // Gérer les erreurs SQL
            System.out.println("Erreur lors de la suppression de l'evenment : " + e.getMessage());
        }
    }


    @Override
    public List<event> afficher() {
        List<event> events = new ArrayList<>();
        String req = "SELECT * FROM evenment";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            // Parcourir les résultats de la requête
            while (rs.next()) {
                // Ajouter chaque événement à la liste
                events.add(new event(
                        rs.getInt("id_event"),  // ID de l'événement
                        rs.getString("nom"),    // Nom de l'événement
                        rs.getDate("date"),     // Date de l'événement
                        rs.getString("lieu")    // Lieu de l'événement
                ));
            }

        } catch (SQLException e) {
            // Gérer l'exception SQL en affichant un message d'erreur
            System.out.println("Erreur lors de la recherche des événements : " + e.getMessage());
        }

        return events;
    }

}
