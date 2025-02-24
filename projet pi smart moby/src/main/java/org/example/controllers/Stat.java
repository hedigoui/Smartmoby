package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Stat {
    Connection conn = DataSource.getInstance().getConnection();
    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        loadUserStatistics();
    }

    private void loadUserStatistics() {
        String query = "SELECT role, COUNT(*) as count FROM utilisateur GROUP BY role";
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            pieChartData.clear();

            while (rs.next()) {
                String role = rs.getString("role");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(role, count));
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }

        pieChart.setData(pieChartData);
    }


    public void refreshChart() {
        loadUserStatistics();
    }
}
