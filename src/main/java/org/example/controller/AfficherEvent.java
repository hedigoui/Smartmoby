package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.modeles.event;
import org.example.service.event_serv;

import java.io.IOException;
import java.util.List;

public class AfficherEvent {

    @FXML
    private ListView<event> liste;

    @FXML
    private Button supp;

    @FXML
    private Button modif;

    private final event_serv eventService = new event_serv();
    private final ObservableList<event> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        chargerListe();
    }

    // Charge la liste des événements
    void chargerListe() {
        List<event> events = eventService.afficher();
        if (events.isEmpty()) {
            afficherAlerte("Aucun événement", "⚠️ Aucun événement trouvé en base.", Alert.AlertType.WARNING);
        }
        eventList.setAll(events);
        liste.setItems(eventList);
    }

    @FXML
    void supprimer(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        boolean deleted = eventService.supprimer(selectedEvent);
        if (deleted) {
            eventList.remove(selectedEvent);
            afficherAlerte("Suppression réussie", "L'événement '" + selectedEvent.getNom() + "' a été supprimé avec succès.", Alert.AlertType.INFORMATION);
        } else {
            afficherAlerte("Erreur", "La suppression a échoué. L'événement n'existe peut-être plus.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à modifier.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            modifierEvent controller = loader.getController();
            controller.initData(selectedEvent, this); // Passer l'événement sélectionné

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier un événement");
            stage.showAndWait();

            chargerListe();  // Rafraîchir la liste après modification

        } catch (IOException e) {
            afficherAlerte("Erreur", "❌ Erreur lors de l'ouverture de la fenêtre de modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Méthode pour afficher des alertes avec différents types
    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
