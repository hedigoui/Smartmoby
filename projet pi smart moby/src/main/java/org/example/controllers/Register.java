package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javax.print.DocFlavor;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.stage.Stage;
import org.example.models.*;
import org.example.services.*;
import org.example.utils.DataSource;
import org.mindrot.jbcrypt.BCrypt;


public class Register {

    @FXML
    private TextField email;

    @FXML
    private PasswordField mot_de_passe;


    @FXML
    private TextField nom;

    @FXML
    private TextField nom_utilisateur;

    @FXML
    private TextField prenom;

    @FXML
    private TextField departement;

    @FXML
    private TextField num_badge;

    @FXML
    private TextField num_permis;

    @FXML
    private Button login;



    @FXML
    private ComboBox<Utilisateur.Role> role;

    @FXML
    public void initialize() {
        if (role != null) {
            role.setItems(FXCollections.observableArrayList(Utilisateur.Role.values()));
            departement.setVisible(false);
            num_badge.setVisible(false);
            num_permis.setVisible(false);

            // Ajouter un écouteur pour détecter la sélection
            role.setOnAction(event -> {
                departement.setVisible(false);
                num_badge.setVisible(false);
                num_permis.setVisible(false);
                if (Utilisateur.Role.ADMIN == role.getValue()) {
                    departement.setVisible(true);
                }
                if (Utilisateur.Role.ORGANISATEUR == role.getValue()) {
                    num_badge.setVisible(true);
                }
                if (Utilisateur.Role.CONDUCTEUR == role.getValue()) {
                    num_permis.setVisible(true);
                }

            });
        }
    }

    @FXML
    void ajouter_compte(ActionEvent event) {
        Connection connection = DataSource.getInstance().getConnection();

        // Vérification des champs vides
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || nom_utilisateur.getText().isEmpty() ||
                email.getText().isEmpty() || mot_de_passe.getText().isEmpty() || role.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

        // Vérification du nom : doit commencer par une majuscule et ne pas contenir de chiffres
        if (!nom.getText().matches("^[A-Z][a-zA-Z]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le nom doit commencer par une majuscule et ne contenir aucun chiffre.");
            alert.showAndWait();
            return;
        }

        // Vérification du prénom : doit commencer par une majuscule et ne pas contenir de chiffres
        if (!prenom.getText().matches("^[A-Z][a-zA-Z]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le prénom doit commencer par une majuscule et ne contenir aucun chiffre.");
            alert.showAndWait();
            return;
        }

        // Vérification du nom d'utilisateur : doit contenir au moins un chiffre
        if (!nom_utilisateur.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le nom d'utilisateur doit contenir au moins un chiffre.");
            alert.showAndWait();
            return;
        }

        // Vérification du format de l'email
        if (!email.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("L'email doit être sous forme d'une adresse mail valide.");
            alert.showAndWait();
            return;
        }

        // Vérification que le mot de passe contient des chiffres
        if (!mot_de_passe.getText().matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins un chiffre.");
            alert.showAndWait();
            return;
        }

        // Vérification que le numéro de badge est composé de 4 chiffres (si le rôle est ORGANISATEUR)
        if (Utilisateur.Role.ORGANISATEUR == role.getValue()) {
            if (!num_badge.getText().matches("\\d{4}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de badge doit être composé de 4 chiffres.");
                alert.showAndWait();
                return;
            }
        }

        // Vérification que le numéro de permis est composé de 8 chiffres (si le rôle est CONDUCTEUR)
        if (Utilisateur.Role.CONDUCTEUR == role.getValue()) {
            if (!num_permis.getText().matches("\\d{8}")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText("Le numéro de permis doit être composé de 8 chiffres.");
                alert.showAndWait();
                return;
            }
        }

        try {
            Connection connection2 = DataSource.getInstance().getConnection();
            String checkQuery = "SELECT COUNT(*) FROM utilisateur WHERE nom_utilisateur = ?";
            PreparedStatement checkStmt = connection2.prepareStatement(checkQuery);
            checkStmt.setString(1, nom_utilisateur.getText());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText("Ce nom d'utilisateur est déjà utilisé.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification d'unicité : " + e.getMessage());
        }

        try {
            Connection connection2 = DataSource.getInstance().getConnection();
            String checkQuery = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
            PreparedStatement checkStmt = connection2.prepareStatement(checkQuery);
            checkStmt.setString(1, email.getText()); // Remplacez 'email.getText()' par la variable qui contient l'email à vérifier
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText("Cet email est déjà utilisé.");
                alert.showAndWait();
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification d'unicité de l'email : " + e.getMessage());
        }


        String hashedPassword = hashPassword(mot_de_passe.getText());
        String req = "INSERT INTO utilisateur (nom, prenom, nom_utilisateur, email, mot_de_passe, role) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Préparer la requête avec des paramètres
            PreparedStatement pst = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, nom.getText());
            pst.setString(2, prenom.getText());
            pst.setString(3, nom_utilisateur.getText());
            pst.setString(4, email.getText());
            pst.setString(5, hashedPassword);
            pst.setString(6, role.getValue().toString()); // Convertir le rôle en String

            // Exécuter la requête
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // Récupérer la clé générée (ID de l'utilisateur inséré)
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1); // ID généré pour l'utilisateur

                    // Ajouter l'utilisateur dans la table spécifique en fonction de son rôle
                    if (Utilisateur.Role.CONDUCTEUR == role.getValue()) {
                        int numPermis = Integer.parseInt(num_permis.getText());
                        Conducteur_service c = new Conducteur_service();
                        c.ajouter(new Conducteur(userId, nom.getText(), prenom.getText(), nom_utilisateur.getText(),
                                email.getText(), hashedPassword, role.getValue(), userId, numPermis));
                        System.out.println("Mot de passe haché : " + hashedPassword);

                    }

                    if (Utilisateur.Role.ADMIN == role.getValue()) {
                        Admin_service a = new Admin_service();
                        a.ajouter(new Admin(userId, nom.getText(), prenom.getText(), nom_utilisateur.getText(),
                                email.getText(), hashedPassword, role.getValue(), departement.getText()));
                    }

                    if (Utilisateur.Role.ORGANISATEUR == role.getValue()) {
                        int numBadge = Integer.parseInt(num_badge.getText());
                        Organisateur_service o = new Organisateur_service();
                        o.ajouter(new Organisateur(userId, nom.getText(), prenom.getText(), nom_utilisateur.getText(),
                                email.getText(), hashedPassword, role.getValue(), numBadge));
                    }

                    if (Utilisateur.Role.CLIENT == role.getValue()) {
                        Client_service c = new Client_service();
                        c.ajouter(new Client(userId, nom.getText(), prenom.getText(), nom_utilisateur.getText(),
                                email.getText(), hashedPassword, role.getValue(), userId));
                    }

                    System.out.println("Utilisateur ajouté avec succès.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }



    @FXML
    void login(ActionEvent event) {
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

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }



}
