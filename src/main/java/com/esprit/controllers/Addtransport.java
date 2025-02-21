package com.esprit.controllers;

import com.esprit.models.Vehicule;
import com.esprit.services.Services;
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

public class Addtransport {

    public TableColumn idveh;
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

    private Services ps = new Services();

    private ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        // Ajouter les types de véhicules dans le ComboBox
        vtc.getItems().addAll("Bus", "Métro", "Car", "Vélo", "Trottinette");

        // Associer les colonnes du TableView aux attributs de l'objet Vehicule
        idveh.setCellValueFactory(new PropertyValueFactory<>("id")); // Ajout pour afficher l'ID
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        ca.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        st.setCellValueFactory(new PropertyValueFactory<>("statut"));
        disp.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Lier les RadioButtons à un ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        oui.setToggleGroup(toggleGroup);
        non.setToggleGroup(toggleGroup);
        oui.setSelected(true); // Sélection par défaut

        // Charger les véhicules dans la TableView
        loadVehicules();
    }




    @FXML
    void add(ActionEvent event) {
        // Get input values
//        int id = Integer.parseInt(idv.getText()); // Assuming ID is an integer
        String vehicleType = vtc.getValue();
        int capacity = Integer.parseInt(capacite.getText());
        String status = stat.getText();
        boolean available = oui.isSelected();

        // Create a new Vehicule object
        Vehicule vehicule = new Vehicule( vehicleType, capacity, status, available);

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

            Services t = new Services();
            t.delete(selectedItem);
        }

    }

    public void update(ActionEvent event) {
        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            openUpdateWindow(selectedItem);
        } else {
            System.out.println("Aucun vehicule sélectionné !");
        }
    }

    private void openUpdateWindow(Vehicule selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateVehicule.fxml")); // Vérifie le bon fichier FXML
            Parent root = loader.load();

            // Vérifie le bon contrôleur
            UpdateVehicule controller = loader.getController();
            controller.initData(selectedItem);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Rafraîchir la liste après fermeture de la fenêtre
            stage.setOnHidden(e -> refreshListView());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshListView() {
        tabv.getItems().clear();

        Services t = new Services();
        ArrayList<Vehicule> items = t.getAllVehicule();

        tabv.getItems().addAll(items);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void loadVehicules() {
        // Fetch all vehicles from the database or data source
        vehiculeList.clear();
        vehiculeList.addAll(ps.getAllVehicule());
        tabv.setItems(vehiculeList);
    }

    private void clearFields() {
//        idv.clear();
        vtc.getSelectionModel().clearSelection();
        capacite.clear();
        stat.clear();
        oui.setSelected(true);
    }
    @FXML
    void gotrajet(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addtrajet.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le fichier FXML chargé
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Changer de scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'erreur s'il y a un problème avec le chargement du fichier FXML
        }
    }


}
