package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.utils.DataSource;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPassword {
    private String Email;

    public void setEmail(String Email) {
        this.Email = Email;
    }

    Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private PasswordField mot_de_passe;

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
    void confirmer(ActionEvent event) {
        String newPassword = mot_de_passe.getText();
        String hashedPassword = hashPassword(newPassword);
        if (!mot_de_passe.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins un chiffre.");
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE utilisateur SET mot_de_passe = ?, reset_code = NULL WHERE email = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, hashedPassword);
            preparedStatement.setString(2, Email);
            preparedStatement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mot de passe réinitialisé");
            alert.setHeaderText(null);
            alert.setContentText("Votre mot de passe est réinitialisé ! ");
            alert.showAndWait();
            se_connecter(event);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


}
