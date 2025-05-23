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
import org.example.models.Session;
import org.example.models.Utilisateur;
import org.example.utils.DataSource;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    @FXML
    private PasswordField mot_de_passe;

    @FXML
    private TextField nom_utilisateur;

    private String hashPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        String hashedPassword = passwordEncoder.encode(password);

        // Remplacer le préfixe "$2a$" par "$2y$"
        if (hashedPassword.startsWith("$2a$")) {
            hashedPassword = "$2y$" + hashedPassword.substring(4);
        }

        return hashedPassword;

    }

    @FXML
    void se_connecter(ActionEvent event) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String username = nom_utilisateur.getText();
        String password = mot_de_passe.getText();
        System.out.println(password);

        // Connexion à la base de données
        Connection connection = DataSource.getInstance().getConnection();
        String query = "SELECT * FROM utilisateur WHERE nom_utilisateur = ? ";

        try {
            // Préparer la requête SQL pour vérifier l'utilisateur
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, username);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                String hashedPassword = rs.getString("mot_de_passe");
                System.out.println(hashedPassword);

                String enteredHashedPassword = hashPassword(password);
                System.out.println(enteredHashedPassword);

                if (passwordEncoder.matches(password, hashedPassword)) {

                    System.out.println("Connexion réussie !");
                    int userId = rs.getInt("id");


                    Session.setUserId(userId);

                    String roleString = rs.getString("role");
                    Utilisateur.Role role = Utilisateur.Role.valueOf(roleString);

                    if(role.equals(Utilisateur.Role.ADMIN)) {
                        try {
                            // Charger le fichier FXML de la nouvelle scène
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcceuilAdmin.fxml"));
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
                    if(role.equals(Utilisateur.Role.CONDUCTEUR)) {
                        try {
                            // Charger le fichier FXML de la nouvelle scène
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcceuilConducteur.fxml"));
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
                    if(role.equals(Utilisateur.Role.ORGANISATEUR)) {
                        try {
                            // Charger le fichier FXML de la nouvelle scène
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcceuilOrganisateur.fxml"));
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
                    if(role.equals(Utilisateur.Role.CLIENT)) {
                        try {
                            // Charger le fichier FXML de la nouvelle scène
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcceuilClient.fxml"));
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
            } else {
                // Si aucun utilisateur n'est trouvé, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de connexion");
                alert.setHeaderText(null);
                alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }


    @FXML
    void inscription(ActionEvent event) {
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
    void x(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Fermer la fenêtre
        stage.close();

    }

    @FXML
    void mot_de_passe_oublie(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mot_de_passe_oublie.fxml"));
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
