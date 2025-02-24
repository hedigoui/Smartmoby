package org.example.controllers;

import org.example.models.Trajet;
import org.example.models.Vehicule;
import org.example.services.Services;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.Timestamp;
import javafx.event.ActionEvent;

public class UpdateTrajet {

    @FXML
    private TextField pointd, pointa, distance, prix;

    @FXML
    private DatePicker dated, datea;

    @FXML
    private ComboBox<Vehicule> id_veh; // Add ComboBox for vehicle selection

    private Trajet trajetToUpdate;
    private Services ps = new Services();

    @FXML
    public void initialize() {
        // Initialize ComboBox with vehicles
        loadVehicules();
    }

    private void loadVehicules() {
        id_veh.setItems(FXCollections.observableArrayList(ps.getAllVehicule()));
        id_veh.setCellFactory(param -> new ListCell<Vehicule>() {
            @Override
            protected void updateItem(Vehicule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType() + " - " + item.getCapacite());
                }
            }
        });
    }

    public void initData(Trajet trajet) {
        this.trajetToUpdate = trajet;

        // Fill fields with selected trajet data
        pointd.setText(trajet.getPointD());
        pointa.setText(trajet.getPointA());
        dated.setValue(trajet.getDateD().toLocalDateTime().toLocalDate());
        datea.setValue(trajet.getDateA().toLocalDateTime().toLocalDate());
        distance.setText(String.valueOf(trajet.getDistance()));
        prix.setText(String.valueOf(trajet.getPrix()));

        // Set selected vehicle in ComboBox
        for (Vehicule v : id_veh.getItems()) {
            if (v.getId() == trajet.getId_veh()) {
                id_veh.setValue(v);
                break;
            }
        }
    }

    @FXML
    void UpdateTrajet(ActionEvent event) {
        try {
            // Validation
            if (pointd.getText().isEmpty() || pointa.getText().isEmpty() ||
                    dated.getValue() == null || datea.getValue() == null ||
                    distance.getText().isEmpty() || prix.getText().isEmpty() ||
                    id_veh.getValue() == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            // Get new values
            String pointDepart = pointd.getText();
            String pointArrivee = pointa.getText();
            double distanceValue = Double.parseDouble(distance.getText());
            double prixValue = Double.parseDouble(prix.getText());
            Vehicule selectedVehicle = id_veh.getValue();

            // Convert dates
            Timestamp dateDepart = Timestamp.valueOf(dated.getValue().atStartOfDay());
            Timestamp dateArrivee = Timestamp.valueOf(datea.getValue().atStartOfDay());

            // Validate dates
            if (dateDepart.after(dateArrivee)) {
                showAlert("Erreur", "La date de départ doit être antérieure à la date d'arrivée.");
                return;
            }

            // Update Trajet object
            trajetToUpdate.setPointD(pointDepart);
            trajetToUpdate.setPointA(pointArrivee);
            trajetToUpdate.setDateD(dateDepart);
            trajetToUpdate.setDateA(dateArrivee);
            trajetToUpdate.setDistance(distanceValue);
            trajetToUpdate.setPrix(prixValue);
            trajetToUpdate.setId_veh(selectedVehicle.getId());
            trajetToUpdate.setVehicule(selectedVehicle.getType());

            // Update in database
            boolean success = ps.updateTrajet(trajetToUpdate);

            if (success) {
                showSuccess("Succès", "Le trajet a été mis à jour avec succès.");
                closeWindow();
            } else {
                showAlert("Erreur", "Échec de la mise à jour du trajet.");
            }

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite: " + e.getMessage());
        }
    }

    private void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) pointd.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}