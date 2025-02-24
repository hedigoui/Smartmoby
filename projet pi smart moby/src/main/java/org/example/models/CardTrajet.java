package org.example.models;

import org.example.utils.DataSource;
import org.example.services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;



import java.sql.Connection;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static com.sun.javafx.event.EventUtil.fireEvent;

public class CardTrajet {
    @FXML
    public Button update;
    @FXML
    public Button delete;
    @FXML
    public Label pointA;
    @FXML
    private Label dateD;

    @FXML
    private Label dateA;

    @FXML
    private Label pointD;
    @FXML
    private Label distance;
    @FXML
    private Label prix;
    private Trajet trajet;
    private Connection cnx;
    @FXML
    private Label vehiculeType;

    public void ServiceStock() {
        cnx = DataSource.getInstance().getConnection();
    }

    public void initialize() {

    }

    public void setData(Trajet trajet) {
        Services t = new Services();

        if (trajet != null) {
            this.trajet = trajet;
            pointD.setText(trajet.getPointD());
            pointA.setText(trajet.getPointA());
            dateD.setText(formatDate(trajet.getDateD()));
            dateA.setText(formatDate(trajet.getDateA()));
            distance.setText(String.format("%.1f km", trajet.getDistance()));
            prix.setText(String.format("%.2f DT", trajet.getPrix()));

            String type = trajet.getVehicule();
            if (type != null && !type.isEmpty()) {
                vehiculeType.setText(type.toUpperCase());
                setVehicleTypeStyle(type);
            } else {
                vehiculeType.setText("NON DÉFINI");
                vehiculeType.setStyle("-fx-background-color: #ffebee; -fx-text-fill: #d32f2f;");
            }
        }
    }
    private void setVehicleTypeStyle(String type) {
        String style = "-fx-padding: 5 10; -fx-background-radius: 15; -fx-font-weight: bold; ";

        switch (type.toLowerCase()) {
            case "bus":
                style += "-fx-background-color: #e3f2fd; -fx-text-fill: #1976d2;";
                break;
            case "métro":
                style += "-fx-background-color: #f3e5f5; -fx-text-fill: #7b1fa2;";
                break;
            case "car":
                style += "-fx-background-color: #e8f5e9; -fx-text-fill: #2e7d32;";
                break;
            case "vélo":
                style += "-fx-background-color: #fff3e0; -fx-text-fill: #f57c00;";
                break;
            case "trottinette":
                style += "-fx-background-color: #fce4ec; -fx-text-fill: #c2185b;";
                break;
            default:
                style += "-fx-background-color: #fafafa; -fx-text-fill: #616161;";
                break;
        }

        vehiculeType.setStyle(style);
    }
    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) return "";
        return timestamp.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"));
    }
}
