package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.models.Service;
import org.example.services.ServiceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class HelloController {

    @FXML private Button btnAdd, btnModifier, btnSupprimer;
    @FXML private TableColumn<Service, String> desc_c;
    @FXML private TableColumn<Service, Integer> id_c;
    @FXML private TableColumn<Service, String> nom_c;
    @FXML private TableView<Service> table;
    @FXML private TableColumn<Service, Double> tarif_c;
    @FXML private TextField txtDescription, txtNom, txtTarif;
    @FXML private TextField searchService;

    private ObservableList<Service> serviceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        id_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nom_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        desc_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        tarif_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTarif()).asObject());

        loadServices();

        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty()) {
                Service selectedService = table.getSelectionModel().getSelectedItem();
                txtNom.setText(selectedService.getNom());
                txtDescription.setText(selectedService.getDescription());
                txtTarif.setText(String.valueOf(selectedService.getTarif()));
            }
        });
    }

    private void loadServices() {
        serviceList.clear();
        try {
            List<Service> services = ServiceDAO.getAllServices();
            serviceList.addAll(services);
            table.setItems(serviceList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les services.", Alert.AlertType.ERROR);
        }
    }


    @FXML private Pane displayPane;
    @FXML private Pane managePane;

    @FXML
    public void showDisplaySection() {
        displayPane.setVisible(true);
        managePane.setVisible(false);
    }

    @FXML
    public void showManageSection() {
        displayPane.setVisible(false);
        managePane.setVisible(true);
    }
    @FXML
    public void searchService() {
        String filter = searchService.getText().toLowerCase();
        ObservableList<Service> filteredList = FXCollections.observableArrayList();

        for (Service service : serviceList) {
            if (service.getNom().toLowerCase().contains(filter) ||
                    service.getDescription().toLowerCase().contains(filter) ||
                    Double.valueOf(service.getTarif()).toString().toLowerCase().contains(filter)) {
                filteredList.add(service);
            }
        }

        table.setItems(filteredList);
    }
    @FXML
    void Add(ActionEvent event) {
        if (!validerChamps()) return; // Validation avant ajout

        try {
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            double tarif = Double.parseDouble(txtTarif.getText());

            ServiceDAO.ajouterService(new Service(0, nom, description, tarif));
            showAlert("Succès", "Service ajouté avec succès.", Alert.AlertType.INFORMATION);
            loadServices();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le service.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        Service selectedService = table.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert("Erreur", "Veuillez sélectionner un service à modifier.", Alert.AlertType.ERROR);
            return;
        }

        if (!validerChamps()) return; // Validation avant modification

        try {
            selectedService = new Service(selectedService.getId(), txtNom.getText(), txtDescription.getText(), Double.parseDouble(txtTarif.getText()));
            ServiceDAO.modifierService(selectedService);
            showAlert("Succès", "Service modifié avec succès.", Alert.AlertType.INFORMATION);
            loadServices();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de modifier le service.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Service selectedService = table.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert("Erreur", "Veuillez sélectionner un service à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce service ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            try {
                ServiceDAO.supprimerService(selectedService.getId());
                showAlert("Succès", "Service supprimé avec succès.", Alert.AlertType.INFORMATION);
                loadServices();
                clearFields();
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de supprimer le service.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void VersProduit(ActionEvent event) {
        switchScene(event, "/produit.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Erreur", "Impossible de changer de scène.", Alert.AlertType.ERROR);
        }
    }

    /**
     * Vérifie si les champs sont bien remplis avant d'ajouter ou modifier un service.
     */
    private boolean validerChamps() {
        String nom = txtNom.getText().trim();
        String description = txtDescription.getText().trim();
        String tarifStr = txtTarif.getText().trim();

        if (nom.isEmpty() || description.isEmpty() || tarifStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return false;
        }

        try {
            double tarif = Double.parseDouble(tarifStr);
            if (tarif <= 0) {
                showAlert("Erreur", "Le tarif doit être un nombre positif.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le tarif doit être un nombre valide.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    /**
     * Affiche une alerte à l'utilisateur.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Efface les champs de saisie après une opération réussie.
     */
    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
        txtTarif.clear();
    }
}
