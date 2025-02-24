package org.example.controllers;

import autovalue.shaded.com.google.common.collect.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.property.UnitValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.models.event;
import org.example.services.event_serv;


import java.beans.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;

import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.element.Cell;

import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import org.example.utils.DataSource;


public class AfficherEvent {
    Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private ListView<event> liste;

    @FXML
    private TextField searchField;

    @FXML
    private Button supp;

    @FXML
    private Button modif;

    @FXML
    private Button feedback;

    @FXML
    private ComboBox<String> triComboBox;


    private final event_serv eventService = new event_serv();
    private final ObservableList<event> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        InitComboBox();
        chargerListe();

        // Ajout du filtrage dynamique
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrerEvenements(newValue));

        // Personnalisation de l'affichage des éléments dans la ListView
        liste.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(event ev, boolean empty) {
                super.updateItem(ev, empty);
                if (empty || ev == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'une boîte HBox pour afficher l'événement
                    HBox box = new HBox(10);
                    Text nomEvent = new Text(ev.getNom());
                    Text dateEvent = new Text("📅 " + ev.getDate());
                    Text lieuEvent = new Text("📍 " + ev.getLieu());

                    // Ajout des éléments à la boîte
                    box.getChildren().addAll(nomEvent, dateEvent, lieuEvent);
                    setGraphic(box);
                }
            }
        });
    }

    // Charge la liste des événements
    public void chargerListe() {
        List<event> events = eventService.afficher();
        if (events.isEmpty()) {
            afficherAlerte("Aucun événement", "⚠️ Aucun événement trouvé en base.", Alert.AlertType.WARNING);
        }
        eventList.setAll(events);
        liste.setItems(eventList);
    }

    @FXML
    private void supprimer(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Suppression de l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean deleted = eventService.supprimer(selectedEvent);
            if (deleted) {
                eventList.remove(selectedEvent);
                afficherAlerte("Succès", "L'événement '" + selectedEvent.getNom() + "' a été supprimé.", Alert.AlertType.INFORMATION);
            } else {
                afficherAlerte("Erreur", "Échec de la suppression. Vérifiez que l'événement existe toujours.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
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
            afficherAlerte("Erreur", "❌ Impossible d'ouvrir la fenêtre de modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Méthode pour ouvrir la page des feedbacks
    @FXML
    private void voirFeedback(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement pour voir les feedbacks.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Charger la scène de feedback
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_fedback.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Passer l'événement sélectionné au contrôleur de feedback
            ajouterfedback controller = loader.getController();
            controller.setEvent(selectedEvent);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Feedbacks de l'événement");
            stage.showAndWait();

        } catch (IOException e) {
            afficherAlerte("Erreur", "❌ Impossible d'ouvrir la fenêtre de feedbacks : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Méthode pour filtrer les événements en fonction du champ de recherche
    private void filtrerEvenements(String keyword) {
        ObservableList<event> filteredList = FXCollections.observableArrayList();
        for (event ev : eventList) {
            if (ev.getNom().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(ev);
            }
        }
        liste.setItems(filteredList);
    }

    // Méthode pour afficher des alertes avec différents types
    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void InitComboBox() {
        // Initialiser la ComboBox avec les options de tri par date
        triComboBox.setItems(FXCollections.observableArrayList(
                "Faire tri par date",
                "Tri par date ascendante",
                "Tri par date descendante"
        ));

        // Définir la valeur par défaut
        triComboBox.setValue("Faire tri par date");

        // Ajouter un listener pour trier les événements en fonction du choix
        triComboBox.valueProperty().addListener((observable, oldValue, newValue) -> trierListeParDate(newValue));
    }

    private void trierListeParDate(String critere) {
        switch (critere) {
            case "Tri par date ascendante":
                FXCollections.sort(eventList, (e1, e2) -> e1.getDate().compareTo(e2.getDate()));
                break;
            case "Tri par date descendante":
                FXCollections.sort(eventList, (e1, e2) -> e2.getDate().compareTo(e1.getDate()));
                break;
        }
        liste.setItems(eventList);
    }




}