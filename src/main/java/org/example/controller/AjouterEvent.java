package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.modeles.event;
import org.example.service.event_serv;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;

public class AjouterEvent {

    @FXML
    private DatePicker date;

    @FXML
    private TextField lieu;

    @FXML
    private TextField nom;

    private event_serv ps = new event_serv(); // Assurez-vous que ce service est bien initialisé correctement

    @FXML
    void afficherEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_event.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("Le fichier FXML n'a pas pu être trouvé.");
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Afficher les événements");
            stage.show();

            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            showAlert("Erreur", "❌ Erreur lors de l'ouverture de la page d'affichage des événements : " + e.getMessage());
        }
    }

    @FXML
    void ajouterEvent(ActionEvent event) {
        System.out.println("Ajouter un événement");

        // Récupération des valeurs des champs
        String nomText = nom.getText().trim();
        String lieuText = lieu.getText().trim();
        LocalDate localDate = date.getValue();

        // Contrôle de saisie
        if (nomText.isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom' ne peut pas être vide.");
            return;
        }
        if (lieuText.isEmpty()) {
            showAlert("Erreur", "Le champ 'Lieu' ne peut pas être vide.");
            return;
        }
        if (localDate == null) {
            showAlert("Erreur", "Veuillez sélectionner une date.");
            return;
        }

        // Vérification que la date est supérieure à aujourd'hui
        LocalDate today = LocalDate.now();
        if (!localDate.isAfter(today)) {
            showAlert("Erreur", "La date de l'événement doit être après aujourd'hui.");
            return;
        }

        // Conversion de la date
        Date dateConverted = Date.valueOf(localDate);

        // Création de l'événement
        event newEvent = new event(nomText, dateConverted, lieuText);

        // Validation de l'événement
        if (!isValidEvent(newEvent)) {
            showAlert("Erreur", "L'événement n'est pas valide. Vérifiez les informations.");
            return;
        }

        // Envoi à la couche service
        ps.ajouter(newEvent);

        System.out.println("✅ Événement ajouté avec succès : " + newEvent);
    }

    // Méthode pour afficher une alerte avec un titre et un message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode de validation (exemple de validation basique)
    private boolean isValidEvent(event newEvent) {
        // Ajouter ici d'autres règles de validation selon le modèle d'événement
        return newEvent.getNom() != null && !newEvent.getNom().isEmpty() &&
                newEvent.getLieu() != null && !newEvent.getLieu().isEmpty() &&
                newEvent.getDate() != null;
    }
}
