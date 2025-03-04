package org.example.services;

import org.example.models.event;
import org.example.utils.DataSource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.example.services.GoogleCalendar;

public class event_serv implements Ievent {
    private Connection connection;
    private GoogleCalendar googlecalendar;

    // ✅ Constructeur qui initialise correctement la connexion
    public event_serv() {
        this.connection = DataSource.getInstance().getConnection();
        this.googlecalendar = new GoogleCalendar();
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
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(event.getDate());

            String startDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(event.getDate());
            String timeZone = "Africa/Tunis";
            String endDateTime = startDateTime;
            System.out.println("✅ Événement ajouté avec succès !");
            googlecalendar.AjouterEventInCalendar(event.getNom(), event.getLieu(), "Nouvelle description", startDateTime, timeZone, endDateTime);

        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout de l'événement : " + e.getMessage());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

                String eventId = googlecalendar.recupererEventId(event.getNom());
                System.out.println(eventId);
                if (eventId != null) {

                    String startDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(event.getDate());
                    String timeZone = "Africa/Tunis";
                    String endDateTime = startDateTime;
                    googlecalendar.ModifierEventInCalendar(eventId, event.getNom(), event.getLieu(), "Nouvelle description", startDateTime, timeZone, endDateTime);
                } else {
                    System.out.println("⚠ Aucun événement trouvé dans Google Calendar avec ce résumé.");
                }
            } else {
                System.out.println("⚠ Aucun événement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification : " + e.getMessage());
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Supprimer un événement de la base de données
    @Override
    public boolean supprimer(event event) {
        String req = "DELETE FROM evenment WHERE id_event=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, event.getId());
            String eventId = googlecalendar.recupererEventId(event.getNom());
            if (eventId != null) {
                // Supprimer l'événement de Google Calendar
                googlecalendar.SupprimerEventInCalendar(eventId);
            }

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
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
