package org.example.controllers;

import com.mailjet.client.errors.MailjetException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.services.EmailAPI;
import org.example.utils.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotDePasseOublie {
    private Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private TextField nom_utilisateur;

    @FXML
    void send_email(ActionEvent event) {
        String username = nom_utilisateur.getText();
        String email = null;
        String password = null;

        String sql = "SELECT email, mot_de_passe FROM utilisateur WHERE nom_utilisateur = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
                password = resultSet.getString("mot_de_passe");
                System.out.println("Email: " + email);
                System.out.println("Mot de passe: " + password);

                try {
                    EmailAPI emailAPI = new EmailAPI();
                    emailAPI.sendEmail(
                            "haythemabdellaoui007@gmail.com",
                            "Me",
                            email,
                            "You",
                            "Récupération Du Mot De Passe",
                            "Salutations de notre équipe de support technique ! Voici votre mot de passe : " + password,
                            "<h3>Voici votre mot de passe : " + password + "</h3>"
                    );
                } catch (MailjetException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce nom d'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
