package com.esprit.controllers;

import com.esprit.models.Trajet;
import com.esprit.services.Services;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Timestamp;

public class UpdateTrajet {

    @FXML
    private TableView<Trajet> tabt;

    @FXML
    private TextField idt, pointd, pointa, distance, prix;

    @FXML
    private DatePicker dated, datea;

    private Trajet trajetToUpdate;
    private Services ps = new Services();

    // Méthode pour initialiser les données dans le contrôleur
    public void initData(Trajet trajet) {
        this.trajetToUpdate = trajet;

        // Remplir les champs avec les données du trajet sélectionné
        pointd.setText(trajet.getPointD());
        pointa.setText(trajet.getPointA());
        dated.setValue(trajet.getDateD().toLocalDateTime().toLocalDate());
        datea.setValue(trajet.getDateA().toLocalDateTime().toLocalDate());
        distance.setText(String.valueOf(trajet.getDistance()));
        prix.setText(String.valueOf(trajet.getPrix()));
    }

    @FXML
    private void UpdateTrajet() {
        try {
            // Vérifier que tous les champs sont remplis
            if (pointd.getText().isEmpty() || pointa.getText().isEmpty() ||
                    dated.getValue() == null || datea.getValue() == null || distance.getText().isEmpty() ||
                    prix.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            // Récupérer les nouvelles valeurs
            String pointDepart = pointd.getText();
            String pointArrivee = pointa.getText();
            double distanceValue = Double.parseDouble(distance.getText());
            double prixValue = Double.parseDouble(prix.getText());

            // Convertir LocalDate en Timestamp
            Timestamp dateDepart = Timestamp.valueOf(dated.getValue().atStartOfDay());
            Timestamp dateArrivee = Timestamp.valueOf(datea.getValue().atStartOfDay());

            // Vérifier que la date de départ est antérieure à la date d’arrivée
            if (dateDepart.after(dateArrivee)) {
                showAlert("Erreur", "La date de départ doit être antérieure à la date d'arrivée.");
                return;
            }

            // Mettre à jour l'objet Trajet
            trajetToUpdate.setPointD(pointDepart);
            trajetToUpdate.setPointA(pointArrivee);
            trajetToUpdate.setDateD(dateDepart);
            trajetToUpdate.setDateA(dateArrivee);
            trajetToUpdate.setDistance(distanceValue);
            trajetToUpdate.setPrix(prixValue);

            // Appeler le service pour mettre à jour le trajet
            ps.updateTrajet(trajetToUpdate);

            // Rafraîchir l'affichage
            refreshListView();

            // Fermer la fenêtre de mise à jour
            closeWindow();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides.");
        }
    }

    // Méthode pour rafraîchir la TableView avec les trajets mis à jour
    private void refreshListView() {
        // Charger les trajets mis à jour
        tabt.setItems(FXCollections.observableArrayList(ps.getAllTrajet()));
    }

    // Méthode pour fermer la fenêtre
    private void closeWindow() {
        Stage stage = (Stage) idt.getScene().getWindow();
        stage.close();
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
