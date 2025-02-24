package com.esprit.controllers;

import com.esprit.models.Vehicule;
import com.esprit.services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class UpdateVehicule {

    @FXML
    private TextField capacite, stat;

    @FXML
    private ComboBox<String> vtc;

    @FXML
    private RadioButton oui, non;

    private Vehicule vehiculeToUpdate;
    private Services ps = new Services();

    @FXML
    public void initialize() {
        // Initialize toggle group for radio buttons
        ToggleGroup group = new ToggleGroup();
        oui.setToggleGroup(group);
        non.setToggleGroup(group);

        // Initialize ComboBox
        vtc.getItems().addAll("Car", "Bus", "Métro", "Vélo", "Trottinette");
    }

    public void initData(Vehicule vehicule) {
        this.vehiculeToUpdate = vehicule;

        // Fill fields with selected vehicle data
        capacite.setText(String.valueOf(vehicule.getCapacite()));
        stat.setText(vehicule.getStatut());
        vtc.setValue(vehicule.getType());

        // Set radio button based on availability
        if (vehicule.isDispo()) {
            oui.setSelected(true);
        } else {
            non.setSelected(true);
        }
    }

    @FXML
    void update(ActionEvent event) {
        try {
            // Validation
            if (capacite.getText().isEmpty() || stat.getText().isEmpty() || vtc.getValue() == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs!", Alert.AlertType.ERROR);
                return;
            }

            // Get new values
            int capaciteValue = Integer.parseInt(capacite.getText());
            String statutValue = stat.getText();
            String typeValue = vtc.getValue();
            boolean disponibilite = oui.isSelected();

            // Update vehicle object
            vehiculeToUpdate.setType(typeValue);
            vehiculeToUpdate.setCapacite(capaciteValue);
            vehiculeToUpdate.setStatut(statutValue);
            vehiculeToUpdate.setDispo(disponibilite);

            // Update in database
            if (ps.update(vehiculeToUpdate)) {
                showAlert("Succès", "Véhicule mis à jour avec succès!", Alert.AlertType.INFORMATION);
                closeWindow();
            } else {
                showAlert("Erreur", "Échec de la mise à jour du véhicule.", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            showAlert("Erreur", "La capacité doit être un nombre valide!", Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) capacite.getScene().getWindow();
        stage.close();
    }
}