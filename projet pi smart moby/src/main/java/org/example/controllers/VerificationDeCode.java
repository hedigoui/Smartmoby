package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.example.utils.DataSource;

public class VerificationDeCode {
    private String Email;

    public void setEmail(String Email) {
        this.Email = Email;
    }

    @FXML
    private TextField code;

    Connection connection = DataSource.getInstance().getConnection();

    @FXML
    void register(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
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

    @FXML
    void se_connecter(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
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

    @FXML
    void x(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fermer la fenêtre
        stage.close();

    }

    @FXML
    void verifier_code(ActionEvent event) {
        String codeSaisi = code.getText();

        String sql = "SELECT reset_code FROM utilisateur WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String codeStocke = resultSet.getString("reset_code");

                if (codeStocke != null && codeStocke.equals(codeSaisi)) {
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reset_password.fxml"));
                        Parent root = loader.load();
                        ResetPassword verificationController = loader.getController();
                        verificationController.setEmail(Email);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Code incorrect !");
                    alert.setHeaderText(null);
                    alert.setContentText("Votre code est incorrect , veuillez vérifiez ! ");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
