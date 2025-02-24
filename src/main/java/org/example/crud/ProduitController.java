package org.example.crud;

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
import java.io.IOException;
import java.sql.*;

public class ProduitController {
    @FXML private TextField txtNomP, txtTypeP, txtPrixP;
    @FXML private ListView<String> listViewP;
    @FXML private TextField searchProduit;
    @FXML private Button btnAjouterP, btnModifierP, btnSuppP, btnVersService;

    private ObservableList<String> produitList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadProduits();
        listViewP.setItems(produitList);

        // Sélectionner un élément pour affichage dans les champs
        listViewP.setOnMouseClicked(event -> {
            String selected = listViewP.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.equals("ID  --  Nom  --  Type  --  Prix")) {
                String[] parts = selected.split(" -- ");
                txtNomP.setText(parts[1]);
                txtTypeP.setText(parts[2]);
                txtPrixP.setText(parts[3]);
            }
        });
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
    public void searchProduit() {
        String filter = searchProduit.getText().toLowerCase();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for (String item : produitList) {
            if (item.toLowerCase().contains(filter)) {
                filteredList.add(item);
            }
        }

        listViewP.setItems(filteredList);
    }

    private void loadProduits() {
        produitList.clear();
        produitList.add("ID -- Nom -- Type -- Prix"); // En-tête

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM produit")) {
            while (rs.next()) {
                produitList.add(rs.getInt("idproduit") + " -- " + rs.getString("nom") +
                        " -- " + rs.getString("type") + " -- " + rs.getDouble("prix"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void AjouterProduit(ActionEvent event) {
        String nom = txtNomP.getText();
        String type = txtTypeP.getText();
        double prix;

        if (nom.isEmpty() || type.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return;
        }

        try {
            prix = Double.parseDouble(txtPrixP.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide.", Alert.AlertType.ERROR);
            return;
        }

        String query = "INSERT INTO produit (nom, type, prix) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, type);
            stmt.setDouble(3, prix);
            stmt.executeUpdate();
            showAlert("Succès", "Produit ajouté avec succès !", Alert.AlertType.INFORMATION);
            loadProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ModifierProduit(ActionEvent event) {
        String selected = listViewP.getSelectionModel().getSelectedItem();
        if (selected == null || selected.equals("ID -- Nom -- Type -- Prix")) {
            showAlert("Erreur", "Veuillez sélectionner un produit à modifier.", Alert.AlertType.ERROR);
            return;
        }

        int id = Integer.parseInt(selected.split(" -- ")[0]);
        String nom = txtNomP.getText();
        String type = txtTypeP.getText();
        double prix;

        if (nom.isEmpty() || type.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return;
        }

        try {
            prix = Double.parseDouble(txtPrixP.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le prix doit être un nombre valide.", Alert.AlertType.ERROR);
            return;
        }

        String query = "UPDATE produit SET nom = ?, type = ?, prix = ? WHERE idproduit = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, type);
            stmt.setDouble(3, prix);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            showAlert("Succès", "Produit modifié avec succès !", Alert.AlertType.INFORMATION);
            loadProduits();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SupprimerProduit(ActionEvent event) {
        String selected = listViewP.getSelectionModel().getSelectedItem();
        if (selected == null || selected.equals("ID -- Nom -- Type -- Prix")) {
            showAlert("Erreur", "Veuillez sélectionner un produit à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        int id = Integer.parseInt(selected.split(" -- ")[0]);

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce produit ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait();
        if (confirm.getResult() == ButtonType.YES) {
            String query = "DELETE FROM produit WHERE idproduit = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
                showAlert("Succès", "Produit supprimé avec succès !", Alert.AlertType.INFORMATION);
                loadProduits();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void VersService(ActionEvent event) {
        switchScene(event, "hello-view.fxml");
    }

    private void switchScene(ActionEvent event, String fxmlFile) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
