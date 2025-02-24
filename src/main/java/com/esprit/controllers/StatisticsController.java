package com.esprit.controllers;

import com.esprit.models.Trajet;
import com.esprit.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML private PieChart vehicleTypeChart;
    @FXML private Label statisticsLabel;
    @FXML private Button returnButton;

    private Services services = new Services();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadVehicleStatistics();
    }

    private void loadVehicleStatistics() {
        // Get all trajets
        List<Trajet> trajets = services.getAllTrajet();

        // Count vehicle types
        Map<String, Integer> vehicleCount = new HashMap<>();
        for (Trajet trajet : trajets) {
            String vehicleType = trajet.getVehicule();
            vehicleCount.put(vehicleType, vehicleCount.getOrDefault(vehicleType, 0) + 1);
        }

        // Create pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : vehicleCount.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Set chart data
        vehicleTypeChart.setData(pieChartData);
        vehicleTypeChart.setTitle("Vehicle Type Distribution");

        // Calculate and display percentages
        int total = trajets.size();
        StringBuilder stats = new StringBuilder("Vehicle Statistics:\n\n");
        for (Map.Entry<String, Integer> entry : vehicleCount.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total;
            stats.append(String.format("%s: %d (%.1f%%)\n",
                    entry.getKey(), entry.getValue(), percentage));
        }
        statisticsLabel.setText(stats.toString());

        // Add hover labels to pie chart
        pieChartData.forEach(data ->
                data.getNode().setOnMouseEntered(event ->
                        statisticsLabel.setText(String.format(
                                "%s: %d (%.1f%%)",
                                data.getName(),
                                (int) data.getPieValue(),
                                (data.getPieValue() * 100.0) / total
                        ))
                )
        );
    }

    @FXML
    private void returnToMain(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/affiche.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}