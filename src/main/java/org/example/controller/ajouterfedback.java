package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.modeles.event;
import org.example.modeles.fedback;
import org.example.service.event_serv;
import org.example.service.fedback_serv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ajouterfedback {

    @FXML
    private ListView<event> liste; // ListView pour afficher les événements



    @FXML
    private TextArea feedbackInput; // TextArea pour le feedback

    @FXML
    private Slider noteSlider; // Slider pour la note

    @FXML
    private Label noteLabel; // Label pour afficher la note

    @FXML
    private Button ajouterFedbackButton; // Bouton pour ajouter un feedback

    @FXML
    private ListView<fedback> feedbackListView;


    private final fedback_serv fedbackService = new fedback_serv();
    private final event_serv eventService = new event_serv();
    private final ObservableList<event> eventList = FXCollections.observableArrayList();
    private event selectedEvent; // L'événement sélectionné
    private fedback selectedFeedback;

    @FXML
    private void initialize() {
        chargerListe();



        System.out.println("📌 Initialisation du contrôleur...");

        if (liste == null) {
            System.out.println("⚠️ Erreur : ListView n'est pas initialisée !");
            return;
        }

        // Charger les événements dans la liste
        loadEvents();

        // Listener pour la sélection d'événement
        liste.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                selectedEvent = newValue;
                System.out.println("✅ Événement sélectionné : " + newValue.getNom());
            }
        });
        feedbackListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedFeedback = newValue;
                initData(selectedFeedback);
            }
        });

    }

    private void loadEvents() {
        try {
            ObservableList<event> events = FXCollections.observableArrayList(eventService.getAllEvents());
            liste.setItems(events);
            liste.setCellFactory(param -> new ListCell<event>() {
                @Override
                protected void updateItem(event item, boolean empty) {
                    super.updateItem(item, empty);
                    setText((empty || item == null) ? null : item.getNom());
                }
            });
        } catch (Exception e) {
            System.out.println("❌ Erreur lors du chargement des événements : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void ajouterFedback() {
        if (selectedEvent == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement avant d'ajouter un feedback.");
            return;
        }

        String feedback = feedbackInput.getText().trim();
        int note = (int) noteSlider.getValue();

        if (!feedback.isEmpty()) {
            try {
                fedback newFeedback = new fedback(feedback, note, selectedEvent.getId());
                fedbackService.ajouter(newFeedback);
                showAlert("Succès", "Le feedback a été ajouté pour l'événement " + selectedEvent.getNom());
                feedbackInput.clear();
            } catch (Exception e) {
                System.out.println("❌ Erreur lors de l'ajout du feedback : " + e.getMessage());
                e.printStackTrace();
                showAlert("Erreur", "Une erreur est survenue lors de l'ajout du feedback.");
            }
        } else {
            showAlert("Erreur", "Le feedback ne peut pas être vide.");
        }
    }

    @FXML
    private void voirFeedbacks(ActionEvent event) {
        if (liste == null) {
            System.out.println("⚠️ Erreur : la liste des événements n'est pas initialisée !");
            showAlert("Erreur", "La liste des événements n'est pas prête.");
            return;
        }

        selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Erreur", "Veuillez sélectionner un événement.");
            return;
        }

        if (feedbackListView == null) {
            System.out.println("⚠️ Erreur : feedbackListView est null !");
            showAlert("Erreur", "Erreur interne : feedbackListView non initialisé.");
            return;
        }

        try {
            int eventId = selectedEvent.getId(); // Récupération de l'ID de l'événement sélectionné
            System.out.println("🔍 Récupération des feedbacks pour l'événement ID: " + eventId);

            List<fedback> feedbacks = fedbackService.getFeedbacksByEvent(eventId); // Récupération des feedbacks
            ObservableList<fedback> fedbacksList = FXCollections.observableArrayList(feedbacks); // Conversion en ObservableList

            System.out.println("📋 Nombre de feedbacks récupérés : " + feedbacks.size());

            if (feedbacks.isEmpty()) {
                showAlert("Aucun Feedback", "Aucun feedback n'a été trouvé pour cet événement.");
            }

            // Trier la liste par la note
            FXCollections.sort(fedbacksList, (f1, f2) -> Integer.compare(f2.getNote(), f1.getNote()));  // Tri décroissant par note

            // Lier la ListView avec l'ObservableList triée
            feedbackListView.setItems(fedbacksList);

            // Définir un CellFactory pour personnaliser l'affichage des feedbacks
            feedbackListView.setCellFactory(param -> new ListCell<fedback>() {
                @Override
                protected void updateItem(fedback feedback, boolean empty) {
                    super.updateItem(feedback, empty);
                    if (empty || feedback == null) {
                        setText(null);
                    } else {
                        setText("Commentaire : " + feedback.getCommentaire() + " | Note : " + feedback.getNote());
                    }
                }
            });

        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la récupération des feedbacks : " + e.getMessage());
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de la récupération des feedbacks.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setEvent(event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void chargerListe() {
        List<event> events = eventService.afficher();
        if (events.isEmpty()) {
            showAlert("Aucun événement", "⚠️ Aucun événement trouvé en base.");
        }
        eventList.setAll(events);
        liste.setItems(eventList);
    }

    // In the ajouterfedback class
    @FXML
    private void modifier(ActionEvent event) {
        // Récupérer le feedback sélectionné dans la TableView
        fedback selectedFeedback = feedbackListView.getSelectionModel().getSelectedItem();

        if (selectedFeedback == null) {
            showAlert("Erreur", "Veuillez sélectionner un feedback à modifier.");
            return;
        }

        try {
            // Charger la fenêtre de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierfedback.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et passer les données
            Modifierfedback modifierController = loader.getController();

            // Charger les données du feedback sélectionné (avec id_event inclus)
            modifierController.initData(selectedFeedback);

            // Afficher la nouvelle fenêtre
            Stage stage = new Stage();
            stage.setTitle("Modifier Feedback");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de modification.");
        }
    }


    @FXML
    public void supprimer(ActionEvent event) {
        System.out.println("🔴 Suppression du feedback en cours...");

        // Vérifier si un feedback est sélectionné
        if (selectedFeedback == null) {
            showAlert("Erreur", "Veuillez sélectionner un feedback à supprimer.");
            return;
        }

        System.out.println("🆔 Feedback ID : " + selectedFeedback.getId()); // Vérification de l'ID du feedback

        // Vérifier si l'ID du feedback est valide
        if (selectedFeedback.getId() == 0) {
            showAlert("Erreur", "Le feedback sélectionné n'a pas d'ID valide.");
            return;
        }

        try {
            // Supprimer le feedback via le service
            fedbackService.supprimer(selectedFeedback);


            System.out.println("✅ Feedback supprimé avec succès.");
            showAlert("Succès", "Le feedback a été supprimé avec succès.");
            initialize();

        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la suppression du feedback : " + e.getMessage());
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de la suppression du feedback.");
        }
    }

    public void initData(fedback feedback) {
        this.selectedFeedback = feedback;

    }
}
