package org.example.controllers;

import autovalue.shaded.com.google.common.collect.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.property.UnitValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.example.models.fedback;
import org.example.services.fedback_serv;
import org.example.utils.DataSource;

import javax.swing.text.Document;
import java.beans.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class afficherfedback {

    Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private ListView<fedback> feedbackList;  // ListView pour afficher les feedbacks sous forme d'objets fedback

    private final fedback_serv fedbackService = new fedback_serv();  // Service pour récupérer les feedbacks

    // Initialiser les données pour afficher les feedbacks
    public void initData(int eventId) {
        List<fedback> feedbacks = fedbackService.getFeedbacksByEvent(eventId); // Récupération des feedbacks
        ObservableList<fedback> fedbacksList = FXCollections.observableArrayList(feedbacks); // Convertir en ObservableList

        // Lier la ListView avec l'ObservableList
        feedbackList.setItems(fedbacksList);

        // Définir un CellFactory pour afficher les feedbacks correctement
        feedbackList.setCellFactory(param -> new ListCell<fedback>() {
            @Override
            protected void updateItem(fedback feedback, boolean empty) {
                super.updateItem(feedback, empty);
                if (empty || feedback == null) {
                    setText(null);
                } else {
                    setText(feedback.getCommentaire() + " (Note: " + feedback.getNote() + ")");
                }
            }
        });
    }


    @FXML
    private void modifierFeedback() {
        // Récupérer le feedback sélectionné dans la TableView
        fedback selectedFeedback = feedbackList.getSelectionModel().getSelectedItem();

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

    // Méthode pour afficher une alerte
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}
