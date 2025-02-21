package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.modeles.event;
import org.example.service.event_serv;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class modifierEvent {

    @FXML
    private DatePicker date;

    @FXML
    private TextField lieu;

    @FXML
    private TextField nom;

    @FXML
    private Button btnModifier;

    private event currentEvent; // L'événement à modifier
    private final event_serv eventService = new event_serv();
    private AfficherEvent mainController; // Référence au contrôleur principal

    public void initData(event selectedEvent, AfficherEvent mainController) {
        if (selectedEvent == null) {
            System.out.println("❌ Erreur: Aucun événement sélectionné.");
            return;
        }

        this.currentEvent = selectedEvent;
        this.mainController = mainController;

        // Pré-remplir les champs
        nom.setText(currentEvent.getNom());
        lieu.setText(currentEvent.getLieu());
        if (currentEvent.getDate() != null) {
            // Si c'est un java.util.Date, convertis-le en java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(currentEvent.getDate().getTime());
            // Convertir java.sql.Date en LocalDate
            LocalDate localDate = sqlDate.toLocalDate();
            date.setValue(localDate);
        }
    }

    @FXML
    private void modifierEvent() {
        if (currentEvent == null) {
            System.out.println("❌ Erreur: Aucun événement chargé.");
            return;
        }

        // Récupération des nouvelles valeurs
        String nomEvent = nom.getText().trim();
        String lieuEvent = lieu.getText().trim();
        LocalDate localDate = date.getValue();
        Date dateEvent = (localDate != null) ? Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

        // Contrôle de saisie
        if (nomEvent.isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom' ne peut pas être vide.");
            return;
        }
        if (lieuEvent.isEmpty()) {
            showAlert("Erreur", "Le champ 'Lieu' ne peut pas être vide.");
            return;
        }
        if (dateEvent == null) {
            showAlert("Erreur", "Veuillez sélectionner une date.");
            return;
        }

        // Mise à jour de l'événement actuel
        currentEvent.setNom(nomEvent);
        currentEvent.setLieu(lieuEvent);
        currentEvent.setDate(dateEvent);

        // Mise à jour en base de données
        eventService.modifier(currentEvent);
        System.out.println("✅ Modification réussie !");

        // Rafraîchir la liste des événements
        if (mainController != null) {
            mainController.chargerListe();
        }

        // Fermer la fenêtre après modification
        Stage stage = (Stage) btnModifier.getScene().getWindow();
        stage.close();
    }

    // Méthode pour afficher une alerte avec un titre et un message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
