package org.example.controllers;

import com.mailjet.client.errors.MailjetException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.services.EmailAPI;
import org.example.utils.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class MotDePasseOublie {
    private Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private TextField email;

    @FXML
    void send_email(ActionEvent event) {
        String Email = email.getText();
        String code = String.format("%06d", new Random().nextInt(999999));

        if (Email.isEmpty() || !Email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Email invalide");
            alert.setHeaderText(null);
            alert.setContentText("Votre Email est invalide ! ");
            alert.showAndWait();
        }
        else {
            String updateSQL = "UPDATE utilisateur SET reset_code = ? WHERE email = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                updateStatement.setString(1, code);
                updateStatement.setString(2, Email);
                updateStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                EmailAPI emailAPI = new EmailAPI();
                emailAPI.sendEmail(
                        "haythemabdellaoui007@gmail.com",
                        "Me",
                        Email,
                        "You",
                        "Code de vérification",
                        "Salutations de notre équipe de support technique ! " ,
                        "<h3>Voici votre code de verification : " + code + "</h3>"
                );
            } catch (MailjetException e) {
                e.printStackTrace();
            }

            try {
                // Charger la nouvelle page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/verification_de_code.fxml"));
                Parent root = loader.load();

                // Passer l'e-mail au contrôleur de la nouvelle page
                VerificationDeCode verificationController = loader.getController();
                verificationController.setEmail(Email); // Méthode à implémenter dans VerificationController

                // Afficher la nouvelle page
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

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

}
