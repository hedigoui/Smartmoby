package org.example.controllers;

import org.example.models.Conducteur;
import org.example.models.Vehicule;
import org.example.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Addtransport {
    @FXML
    private TableColumn<Vehicule, Integer> idveh;
    @FXML
    private TableColumn<Vehicule, Integer> ca;
    @FXML
    private TextField capacite;
    @FXML
    private TableColumn<Vehicule, Integer> disp;
    @FXML
    private RadioButton non;
    @FXML
    private RadioButton oui;
    @FXML
    private TableColumn<Vehicule, String> st;
    @FXML
    private TextField stat;
    @FXML
    private TableView<Vehicule> tabv;
    @FXML
    private TableColumn<Vehicule, String> type;
    @FXML
    private ComboBox<String> vtc;
    @FXML
    private ComboBox<Conducteur> conducteurCombo;

    private Services ps = new Services();
    private ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Ajouter les types de véhicules dans le ComboBox
        vtc.getItems().addAll("Car", "Taxi", "Métro", "Bus", "Moto");

        // Associer les colonnes du TableView aux attributs de l'objet Vehicule
        idveh.setCellValueFactory(new PropertyValueFactory<>("id"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        ca.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        st.setCellValueFactory(new PropertyValueFactory<>("statut"));
        disp.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Lier les RadioButtons à un ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        oui.setToggleGroup(toggleGroup);
        non.setToggleGroup(toggleGroup);
        oui.setSelected(true);

        // Charger les véhicules dans la TableView
        loadVehicules();

        // Charger les conducteurs dans le ComboBox
        loadConducteurs();
    }

    @FXML
    void add(ActionEvent event) {
        // Get input values
        String vehicleType = vtc.getValue();
        int capacity = Integer.parseInt(capacite.getText());
        String status = stat.getText();
        boolean available = oui.isSelected();
        Conducteur selectedConducteur = conducteurCombo.getValue();
        Integer conducteurId = selectedConducteur != null ? selectedConducteur.getId() : null;

        // Create a new Vehicule object
        Vehicule vehicule = new Vehicule(vehicleType, capacity, status, available, conducteurId);

        // Add the vehicle using the Services class
        ps.add(vehicule);

        // Refresh the TableView
        loadVehicules();

        // Clear input fields
        clearFields();
    }

    @FXML
    public void delete(ActionEvent event) {
        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tabv.getItems().remove(selectedItem);
            ps.delete(selectedItem);
        }
    }

    public void update(ActionEvent event) {
        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            openUpdateWindow(selectedItem);
        } else {
            System.out.println("Aucun vehicule séle ctionné !");
        }
    }

    private void openUpdateWindow(Vehicule selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateVehicule.fxml"));
            Parent root = loader.load();
            UpdateVehicule controller = loader.getController();
            controller.initData(selectedItem);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshListView());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshListView() {
        tabv.getItems().clear();
        ArrayList<Vehicule> items = ps.getAllVehicule();
        tabv.getItems().addAll(items);
    }

    private void loadVehicules() {
        vehiculeList.clear();
        vehiculeList.addAll(ps.getAllVehicule());
        tabv.setItems(vehiculeList);
    }

    private void loadConducteurs() {
        List<Conducteur> conducteurs = ps.getAllConducteurs();
        conducteurCombo.setItems(FXCollections.observableArrayList(conducteurs));
        conducteurCombo.setCellFactory(param -> new ListCell<Conducteur>() {
            @Override
            protected void updateItem(Conducteur item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom() + " " + item.getPrenom() + " - " + item.getNumero_permis());
                }
            }
        });
    }

    private void clearFields() {
        vtc.getSelectionModel().clearSelection();
        capacite.clear();
        stat.clear();
        oui.setSelected(true);
        conducteurCombo.getSelectionModel().clearSelection();
    }

    @FXML
    void gotrajet(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addtrajet.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}