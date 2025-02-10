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

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        datasrc dataSource = datasrc.getInstance();

        Connection connection = dataSource.getConnection();
        event_serv e1 = new event_serv();
        fedback_serv feedbackService = new fedback_serv();
        fedback newFeedback = new fedback("Super événement, très bien organisé!", 5);
        feedbackService.ajouter(newFeedback);
        fedback feedbackToModify = new fedback(1, "Événement génial, mais quelques améliorations.", 4);
        feedbackService.modifier(feedbackToModify);
        fedback feedbackToDelete = new fedback(1, null, 0);
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


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("18/10/2025");

            event newEvent = new event("Conférence Tech", date, "Tunis");
            e1.ajouter(newEvent);
            event eventToModify = new event(2, "Conférence Tech - Nouvelle Edition", date, "Tunis");  // ID de l'événement à modifier.

            // Appeler la méthode modifier pour changer les informations de l'événement dans la base de données
            e1.modifier(eventToModify);
            event eventasupprimer = new event(15, "Conférence Tech - Nouvelle Edition", date, "Tunis");
            eventasupprimer.setId(15);  // Supposons que l'utilisateur avec ID 1 existe dans la base de données

            // Appeler la méthode supprimer pour supprimer l'utilisateur
            e1.supprimer(eventasupprimer);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<event> events = e1.afficher();

        // Afficher les événements récupérés
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



