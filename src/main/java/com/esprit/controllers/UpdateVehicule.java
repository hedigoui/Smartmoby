package com.esprit.controllers;

import com.esprit.models.Vehicule;
import com.esprit.services.Services;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateVehicule {

    @FXML
    private TextField idv, capacite, stat;

    @FXML
    private ComboBox<String> vtc;

    @FXML
    private RadioButton oui, non;

    private Vehicule vehiculeToUpdate;
    private Services ps = new Services();

    // Méthode pour initialiser les données dans le contrôleur
    public void initData(Vehicule vehicule) {
        this.vehiculeToUpdate = vehicule;

        // Remplir les champs avec les données du véhicule sélectionné
        capacite.setText(String.valueOf(vehicule.getCapacite()));
        stat.setText(vehicule.getStatut());
        // Initialiser le ComboBox avec le type de véhicule
        vtc.getItems().addAll("Bus", "Métro", "Car", "Vélo", "Trottinette");
        vtc.setValue(vehicule.getType());

//         Initialiser les boutons radio pour la disponibilité
        if (vehicule.getDisponibilite().equals("Oui")) {
            oui.setSelected(true);
        } else {
            non.setSelected(false);
        }
    }

    // Méthode d'action pour mettre à jour un véhicule
    @FXML
    private void update() {
        try {
            // Vérifier que tous les champs sont remplis
            if ( capacite.getText().isEmpty() || stat.getText().isEmpty() ||
                    vtc.getValue() == null ) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            // Récupérer les nouvelles valeurs
            int capaciteValue = Integer.parseInt(capacite.getText());
            String statutValue = stat.getText();
            String typeVtcValue = vtc.getValue();
            String disponibilite = oui.isSelected() ? "oui" : "non";

            // Mettre à jour l'objet Vehicule
            vehiculeToUpdate.setCapacite(capaciteValue);
            vehiculeToUpdate.setStatut(statutValue);
            vehiculeToUpdate.setType(typeVtcValue);
            vehiculeToUpdate.setDispo(vehiculeToUpdate.isDispo());

            // Appeler le service pour mettre à jour le véhicule
            boolean success = ps.UpdateVehicule(vehiculeToUpdate);

            if (success) {
                showAlert("Succès", "Véhicule mis à jour avec succès.");
                closeWindow();
            } else {
                showAlert("Erreur", "Erreur lors de la mise à jour du véhicule.");
            }

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour la capacité.");
        }
    }

    // Méthode pour fermer la fenêtre
    private void closeWindow() {
        Stage stage = (Stage) idv.getScene().getWindow();
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

