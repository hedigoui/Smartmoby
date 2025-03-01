package org.example.controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.models.*;
import org.example.services.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AcceuilOrganisateur {

    @FXML
    private AnchorPane Modifier;

    @FXML
    private TextField email;

    @FXML
    private PasswordField mdp;

    @FXML
    private TextField nom;

    @FXML
    private TextField nom_utilisateur;

    @FXML
    private TextField num_badge;

    @FXML
    private TextField prenom;

    @FXML
    private DatePicker date;

    @FXML
    private TextField lieu;

    @FXML
    private TextField nom2;

    @FXML
    private AnchorPane afficher_event;

    private event_serv ps = new event_serv();

    @FXML
    void modifier_compte(ActionEvent event) {
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        String Nom_utilisateur = nom_utilisateur.getText();
        String Email = email.getText();
        String Mot_de_passe = mdp.getText();
        String numBadgeText = num_badge.getText();

        // Vérification des champs vides
        if (Nom.isEmpty() || Prenom.isEmpty() || Nom_utilisateur.isEmpty() || Email.isEmpty() || Mot_de_passe.isEmpty() || numBadgeText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Tous les champs doivent être remplis.");
            alert.showAndWait();
            return;
        }

        // Vérification de la première lettre du Nom et Prénom en majuscule et absence de chiffres
        if (!Nom.matches("[A-Z][a-zA-Z]*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le nom doit commencer par une majuscule et ne contenir que des lettres.");
            alert.showAndWait();
            return;
        }

        if (!Prenom.matches("[A-Z][a-zA-Z]*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le prénom doit commencer par une majuscule et ne contenir que des lettres.");
            alert.showAndWait();
            return;
        }

        // Vérification que le Nom Utilisateur contient des chiffres
        if (!Nom_utilisateur.matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le nom d'utilisateur doit contenir des chiffres.");
            alert.showAndWait();
            return;
        }

        // Vérification du format de l'email
        if (!Email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("L'email doit être sous forme d'une adresse mail valide.");
            alert.showAndWait();
            return;
        }

        // Vérification que le mot de passe contient des chiffres
        if (!Mot_de_passe.matches(".*\\d.*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le mot de passe doit contenir au moins un chiffre.");
            alert.showAndWait();
            return;
        }

        // Vérification que le numéro de badge est composé de 4 chiffres
        if (!numBadgeText.matches("\\d{4}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de badge doit être composé de 4 chiffres.");
            alert.showAndWait();
            return;
        }



        int Num = Integer.valueOf(numBadgeText);

        // Si toutes les vérifications sont passées, effectuer la modification
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);
        String hashedPassword = hashPassword(Mot_de_passe);

        // Appel de la méthode de modification
        Organisateur_service organisateurService = new Organisateur_service();
        organisateurService.modifier(new Organisateur(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword, Utilisateur.Role.ORGANISATEUR, userId, Num),
                new Utilisateur(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword));

        // Alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Les informations ont été modifiées avec succès.");
        alert.show();
    }

    @FXML
    void supprimer2(ActionEvent event){
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);
        Organisateur_service organisateurService = new Organisateur_service();
        Utilisateur_service utilisateurService = new Utilisateur_service();

        Organisateur organisateur = organisateurService.getOrganisateurById(userId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);

        if (organisateur != null && utilisateur != null) {
            // Appeler la méthode de suppression
            organisateurService.supprimer(organisateur, utilisateur);
            System.out.println("Conducteur et Utilisateur supprimés avec succès !");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText(null);
            alert.setContentText("Votre compte a été supprimé !");
            alert.showAndWait();
            Session.setUserId(-1);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Client ou Utilisateur introuvable !");
        }
    }

    @FXML
    void quiiter(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void show_acceuil(ActionEvent event) {
        Modifier.setVisible(false);
        afficher_event.setVisible(false);

    }

    @FXML
    void show_event(ActionEvent event) {
        Modifier.setVisible(false);
        afficher_event.setVisible(true);

    }

    @FXML
    void show_parametres(ActionEvent event) {
        Modifier.setVisible(true);
        afficher_event.setVisible(false);
    }

    @FXML
    void show_se_deconnecter(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Vous êtes déconnecté.");
        alert.showAndWait();
        Session.setUserId(-1);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void afficherEvent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficher_event.fxml"));
            if (loader.getLocation() == null) {
                throw new IOException("Le fichier FXML n'a pas pu être trouvé.");
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Afficher les événements");
            stage.show();



        } catch (IOException e) {
            showAlert("Erreur", "❌ Erreur lors de l'ouverture de la page d'affichage des événements : " + e.getMessage());
        }
    }

    @FXML
    void ajouterEvent(ActionEvent event) {
        System.out.println("Ajouter un événement");

        // Récupération des valeurs des champs
        String nomText = nom2.getText().trim();
        String lieuText = lieu.getText().trim();
        LocalDate localDate = date.getValue();

        // Contrôle de saisie
        if (nomText.isEmpty()) {
            showAlert("Erreur", "Le champ 'Nom' ne peut pas être vide.");
            return;
        }
        if (lieuText.isEmpty()) {
            showAlert("Erreur", "Le champ 'Lieu' ne peut pas être vide.");
            return;
        }
        if (localDate == null) {
            showAlert("Erreur", "Veuillez sélectionner une date.");
            return;
        }

        // Vérification que la date est supérieure à aujourd'hui
        LocalDate today = LocalDate.now();
        if (!localDate.isAfter(today)) {
            showAlert("Erreur", "La date de l'événement doit être après aujourd'hui.");
            return;
        }

        // Conversion de la date
        Date dateConverted = Date.valueOf(localDate);

        // Création de l'événement
        org.example.models.event newEvent = new event(nomText, dateConverted, lieuText);

        // Validation de l'événement
        if (!isValidEvent(newEvent)) {
            showAlert("Erreur", "L'événement n'est pas valide. Vérifiez les informations.");
            return;
        }

        // Envoi à la couche service
        ps.ajouter(newEvent);

        System.out.println("✅ Événement ajouté avec succès : " + newEvent);
    }

    // Méthode pour afficher une alerte avec un titre et un message
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode de validation (exemple de validation basique)
    private boolean isValidEvent(event newEvent) {
        // Ajouter ici d'autres règles de validation selon le modèle d'événement
        return newEvent.getNom() != null && !newEvent.getNom().isEmpty() &&
                newEvent.getLieu() != null && !newEvent.getLieu().isEmpty() &&
                newEvent.getDate() != null;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
