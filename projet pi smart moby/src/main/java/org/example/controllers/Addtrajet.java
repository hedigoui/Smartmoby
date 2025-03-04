package org.example.controllers;


import org.example.models.Trajet;
import org.example.models.*;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Addtrajet {

    @FXML
    private TableView<Trajet> tabt; // TableView pour afficher les trajets

    @FXML
    private TableColumn<Trajet, Integer> idtar;

    @FXML
    private TableColumn<Trajet, String> pd;

    @FXML
    private TableColumn<Trajet, String> pa;

    @FXML
    private TableColumn<Trajet, Timestamp> dd;

    @FXML
    private TableColumn<Trajet, Timestamp> da;

    @FXML
    private TableColumn<Trajet, Double> dis;

    @FXML
    private TableColumn<Trajet, Double> px;
    @FXML
    private ComboBox<Vehicule> id_veh;

    @FXML
    private TextField idt, pointd, pointa, distance, prix;

    @FXML
    private DatePicker dated, datea;

    private Services ps = new Services();
    private ObservableList<Trajet> trajetList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        loadVehicules();

        // Associer les colonnes aux attributs de Trajet
        idtar.setCellValueFactory(new PropertyValueFactory<>("id"));
        pd.setCellValueFactory(new PropertyValueFactory<>("pointD"));
        pa.setCellValueFactory(new PropertyValueFactory<>("pointA"));
        dd.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        da.setCellValueFactory(new PropertyValueFactory<>("dateA"));
        dis.setCellValueFactory(new PropertyValueFactory<>("distance"));
        px.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Charger les trajets existants
        loadTrajets();
    }

    @FXML
    void addtrajet(ActionEvent event) {
        try {
            // Vérifier que tous les champs sont remplis
            if (pointd.getText().isEmpty() || pointa.getText().isEmpty() ||
                    dated.getValue() == null || datea.getValue() == null ||
                    distance.getText().isEmpty() || prix.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            String pointDepart = pointd.getText();
            String pointArrivee = pointa.getText();
            double distanceValue = Double.parseDouble(distance.getText());
            double prixValue = Double.parseDouble(prix.getText());
            Vehicule selectedveh = id_veh.getValue();
            // Convertir LocalDate en Timestamp
            Timestamp dateDepart = Timestamp.valueOf(dated.getValue().atStartOfDay());
            Timestamp dateArrivee = Timestamp.valueOf(datea.getValue().atStartOfDay());

            // Vérifier que la date de départ est antérieure à la date d’arrivée
            if (dateDepart.after(dateArrivee)) {
                showAlert("Erreur", "La date de départ doit être antérieure à la date d'arrivée.");
                return;
            }
            // Créer le trajet avec le type de véhicule
            Trajet trajet = new Trajet(pointDepart, pointArrivee, dateDepart, dateArrivee,
                    distanceValue, prixValue, selectedveh.getId());
            trajet.setVehicule(selectedveh.getType()); // Set the vehicle type
            int id_veh = selectedveh.getId();

            // Créer un objet Trajet et l'ajouter
//            Trajet trajet = new Trajet(pointDepart, pointArrivee, dateDepart, dateArrivee, distanceValue, prixValue, id_veh);
            ps.addtrajet(trajet);

            // Ajouter le trajet à la TableView
            tabt.getItems().add(trajet);

            // Réinitialiser les champs
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides.");
        }
    }

    @FXML
    public void deletetrajet(ActionEvent event) {

        Trajet selectedItem = tabt.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            tabt.getItems().remove(selectedItem);

            Services t = new Services();
            t.delete(selectedItem);
        }

    }

    @FXML
    public void updatetrajet(ActionEvent event) {
        Trajet selectedItem = tabt.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            openUpdateWindowTrajet(selectedItem);
        }
    }
    private void openUpdateWindowTrajet(Trajet selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateTrajet.fxml"));
            Parent root = loader.load();
            UpdateTrajet controller = loader.getController();
            controller.initData(selectedItem); // Passer le trajet sélectionné

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshListView());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void refreshListView() {
        tabt.getItems().clear();

        Services t = new Services();
        ArrayList<Trajet> items = t.getAllTrajet();

        tabt.getItems().addAll(items);
    }
    private void loadTrajets() {
        trajetList.clear();
        trajetList.addAll(ps.getAllTrajet());
        tabt.setItems(trajetList);
    }


    private void clearFields() {
        pointd.clear();
        pointa.clear();
        dated.setValue(null);
        datea.setValue(null);
        distance.clear();
        prix.clear();
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void gotransport(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addtransport.fxml"));
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
    private void loadVehicules() {
        Services service = new Services();
        List<Vehicule> vehicules = service.getAllVehicule(); // You need to implement this method in Services
        id_veh.setItems(FXCollections.observableArrayList(vehicules));

        // Set a cell factory to display vehicle information in ComboBox
        id_veh.setCellFactory(param -> new ListCell<Vehicule>() {
            @Override
            protected void updateItem(Vehicule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType() + " - " + item.getCapacite()); // Adjust based on your Vehicule class
                }
            }
        });
    }

}