package org.example.controllers;

import org.example.models.Trajet;
import org.example.models.Vehicule;
import org.example.services.Services;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TrajetDetailController {
    @FXML private Label typeVehicule;
    @FXML private Label statusVehicule;
    @FXML private Label conducteurNom;
    @FXML private Label conducteurTelephone;
    @FXML private Label pointDepart;
    @FXML private Label pointArrivee;
    @FXML private Label dateDepart;
    @FXML private Label dateArrivee;
    @FXML private Label capacite;
    @FXML private Label prix;
    @FXML private Button btnReserver;
    @FXML private Label placesRestantes;
    @FXML private VBox reservationDetails;

    private Services services = new Services();
    private Trajet trajet;
    private Vehicule vehicule;

    public void initData(Trajet trajet) {
        this.trajet = trajet;
        this.vehicule = services.getVehiculeById(trajet.getId_veh());

        updateDisplay();
    }

    private void updateDisplay() {
        if (vehicule != null) {
            typeVehicule.setText(vehicule.getType().toUpperCase());
            statusVehicule.setText("Statut: " + vehicule.getStatut());
            conducteurNom.setText("Conducteur: " + (vehicule.getConducteurNom() != null ? vehicule.getConducteurNom() : "Non assigné"));
            conducteurTelephone.setText("Numéro Téléphone : " + vehicule.getConducteurTelephone());
            capacite.setText(vehicule.getCapacite() + " places disponibles");
            placesRestantes.setText("Places restantes: " + vehicule.getCapacite());

            btnReserver.setDisable(vehicule.getCapacite() <= 0);
        } else {
            typeVehicule.setText("VÉHICULE NON DISPONIBLE");
            btnReserver.setDisable(true);
        }

        pointDepart.setText(trajet.getPointD());
        pointArrivee.setText(trajet.getPointA());
        dateDepart.setText(formatDate(trajet.getDateD()));
        dateArrivee.setText(formatDate(trajet.getDateA()));
        prix.setText(String.format("%.2f DT", trajet.getPrix()));
    }

    @FXML
    private void reserverPlace(ActionEvent event) {
        if (vehicule != null && vehicule.getCapacite() > 0) {
            vehicule.setCapacite(vehicule.getCapacite() - 1);  // Decrement the capacity
            services.update(vehicule);  // Update the vehicle in the database
            updateDisplay();
        }
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) return "Non défini";
        return timestamp.toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @FXML
    private void retour(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
}