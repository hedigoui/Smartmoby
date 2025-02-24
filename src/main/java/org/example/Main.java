package org.example;

import org.example.modeles.event;
import org.example.modeles.fedback;
import org.example.service.event_serv;
import org.example.service.fedback_serv;
import org.example.utilis.datasrc;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialisation de la source de données
        datasrc dataSource = datasrc.getInstance();
        Connection connection = dataSource.getConnection();

        // Services pour gérer les événements et les retours
        event_serv e1 = new event_serv();
        fedback_serv feedbackService = new fedback_serv();

        // Gestion des feedbacks
        int eventId = 1;  // Remplacez-le par l'ID réel de l'événement
        fedback newFeedback = new fedback("Super événement, très bien organisé!", 5, eventId);  // Ajout de l'ID de l'événement
        feedbackService.ajouter(newFeedback);

        fedback feedbackToModify = new fedback(1, "Événement génial, mais quelques améliorations.", 4, eventId);  // Ajout de l'ID de l'événement
        feedbackService.modifier(feedbackToModify);

        fedback feedbackToDelete = new fedback(1, null, 0, eventId);  // Ajout de l'ID de l'événement
        feedbackService.supprimer(feedbackToDelete);

        List<fedback> fedbacks = feedbackService.afficher();
        if (fedbacks.isEmpty()) {
            System.out.println("Aucun feedback trouvé.");
        } else {
            System.out.println("Liste des feedbacks :");
            for (fedback feedback : fedbacks) {
                System.out.println("ID : " + feedback.getId() + ", Commentaire : " + feedback.getCommentaire() + ", Note : " + feedback.getNote());
            }
        }

        // Gestion des événements
        try {
            // Parse de la date à partir de chaîne
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("18/10/2025");

            // Création d'un nouvel événement
            event newEvent = new event("Conférence Tech", date, "Tunis"); // Passer des valeurs valides au constructeur
            e1.ajouter(newEvent);

            // Modification d'un événement existant (ID 2 par exemple)
            event eventToModify = new event(2, "Conférence Tech - Nouvelle Edition", date, "Tunis");
            e1.modifier(eventToModify);

            // Suppression d'un événement (ID 15 par exemple)
            event eventToDelete = new event(24, "Conférence Tech - Nouvelle Edition", date, "Tunis");
            e1.supprimer(eventToDelete);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Récupérer et afficher la liste des événements
        List<event> events = e1.afficher();
        if (events.isEmpty()) {
            System.out.println("Aucun événement trouvé.");
        } else {
            System.out.println("Événements trouvés :");
            for (event ev : events) {
                // Afficher les informations de chaque événement
                System.out.println(ev);
            }
        }
    }
}
