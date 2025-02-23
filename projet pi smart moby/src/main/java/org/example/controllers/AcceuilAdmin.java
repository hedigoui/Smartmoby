package org.example.controllers;

import com.itextpdf.layout.element.Cell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.models.*;
import org.example.services.Admin_service;
import org.example.services.Client_service;
import org.example.services.Conducteur_service;
import org.example.services.Organisateur_service;
import org.example.utils.DataSource;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



import java.io.FileOutputStream;
import java.io.IOException;

import javafx.scene.Node;



public class AcceuilAdmin {
    Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private TableColumn<Admin, String> departement_admin;

    @FXML
    private TextField nom_client_a_rechercher;

    @FXML
    private TextField nom_organisateur_a_rechercher;

    @FXML
    private TextField nom_conducteur_a_rechercher;

    @FXML
    private TableColumn<Admin, String> email_admin;

    @FXML
    private TableColumn<Admin, String> mot_de_passe_admin;

    @FXML
    private TableColumn<Admin, String> nom_admin;

    @FXML
    private TableColumn<Admin, String> nom_utilisateur_admin;

    @FXML
    private AnchorPane page_utilisateurs;

    @FXML
    private TableColumn<Admin, String> prenom_admin;

    @FXML
    private TableColumn<Admin, Utilisateur.Role> role_admin;

    @FXML
    private TableView<Admin> tableView;

    @FXML
    private AnchorPane TableAdmins;

    @FXML
    private AnchorPane PageClients;


    @FXML
    private TableColumn<Client, String> email_client;


    @FXML
    private TableColumn<Client, String> mot_de_passe_client;


    @FXML
    private TableColumn<Client, String> nom_client;

    @FXML
    private TableColumn<Client, String> nom_utilisateur_client;

    @FXML
    private TableColumn<Client, String> prenom_client;

    @FXML
    private TableColumn<Client, String> role_client;

    @FXML
    private TableView<Client> table_client;

    @FXML
    private AnchorPane Afficher_organisateurs;


    @FXML
    private TableColumn<Organisateur, String> departement_organisateur;


    @FXML
    private TableColumn<Organisateur, String> email_organisateur;


    @FXML
    private TableColumn<Organisateur, String> mot_de_passe_organisateur;


    @FXML
    private TableColumn<Organisateur, String> nom_organisateur;

    @FXML
    private TableColumn<Organisateur, String> nom_utilisateur_organisateur;

    @FXML
    private TableColumn<Organisateur, Integer> num_badge;


    @FXML
    private TableColumn<Organisateur, String> prenom_organisateur;


    @FXML
    private TableColumn<Organisateur, Utilisateur.Role> role_organisateur;

    @FXML
    private AnchorPane afficher_conducteurs;


    @FXML
    private TableColumn<Conducteur, String> email_conducteur;

    @FXML
    private TableColumn<Conducteur, String> mot_de_passe_conducteur;

    @FXML
    private TableColumn<Conducteur, String> nom_conducteur;

    @FXML
    private TableColumn<Conducteur, String> nom_utilisateur_conducteur;

    @FXML
    private TableColumn<Conducteur, Integer> num_permis;


    @FXML
    private TableColumn<Conducteur, String> prenom_conducteur;


    @FXML
    private TableColumn<Conducteur, Utilisateur.Role> role_conducteur;


    @FXML
    private TableView<Conducteur> table_conducteurs;

    @FXML
    private TableView<Organisateur> table_organisateurs;


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
    private TextField prenom;
    private Admin admin;

    @FXML
    private TextField departement;

    @FXML
    private TextField nom_admin_a_rechercher;


    @FXML
    void initialize() {

        Admin_service a = new Admin_service();
        List<Admin> admins = a.afficher(); // Utiliser la méthode correcte pour récupérer les admins

        // Créer un ObservableList à partir des admins
        ObservableList<Admin> adminsList = FXCollections.observableArrayList(admins); // Liste Observable contenant les admins

        // Lier la TableView avec l'ObservableList
        tableView.setItems(adminsList); // tableView doit être la TableView dans ton fichier FXML

        // Configurer les colonnes de la TableView
        nom_admin.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_admin.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom_utilisateur_admin.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        email_admin.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passe_admin.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        role_admin.setCellValueFactory(new PropertyValueFactory<>("role"));
        departement_admin.setCellValueFactory(new PropertyValueFactory<>("departement"));

        //client
        Client_service c = new Client_service();
        List<Client> clients = c.afficher();

        ObservableList<Client> clientsList = FXCollections.observableArrayList(clients); // Liste Observable contenant les admins

        // Lier la TableView avec l'ObservableList
        table_client.setItems(clientsList); // tableView doit être la TableView dans ton fichier FXML

        // Configurer les colonnes de la TableView
        nom_client.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom_utilisateur_client.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        email_client.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passe_client.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        role_client.setCellValueFactory(new PropertyValueFactory<>("role"));

        //organisateur
        Organisateur_service o = new Organisateur_service();
        List<Organisateur> organisateurs = o.afficher();

        ObservableList<Organisateur> organisateursList = FXCollections.observableArrayList(organisateurs); // Liste Observable contenant les admins

        // Lier la TableView avec l'ObservableList
        table_organisateurs.setItems(organisateursList); // tableView doit être la TableView dans ton fichier FXML

        // Configurer les colonnes de la TableView
        nom_organisateur.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_organisateur.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom_utilisateur_organisateur.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        email_organisateur.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passe_organisateur.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        role_organisateur.setCellValueFactory(new PropertyValueFactory<>("role"));
        num_badge.setCellValueFactory(new PropertyValueFactory<>("num_badge"));

        //conducteur

        Conducteur_service cd = new Conducteur_service();
        List<Conducteur> conducteurs = cd.afficher();

        ObservableList<Conducteur> conducteursList = FXCollections.observableArrayList(conducteurs); // Liste Observable contenant les admins

        // Lier la TableView avec l'ObservableList
        table_conducteurs.setItems(conducteursList); // tableView doit être la TableView dans ton fichier FXML

        // Configurer les colonnes de la TableView
        nom_conducteur.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_conducteur.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom_utilisateur_conducteur.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        email_conducteur.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passe_conducteur.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        role_conducteur.setCellValueFactory(new PropertyValueFactory<>("role"));
        num_permis.setCellValueFactory(new PropertyValueFactory<>("numero_permis"));


    }


    @FXML
    void quiiter(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void show_acceuil(ActionEvent event) {
        page_utilisateurs.setVisible(false);
        Modifier.setVisible(false);

    }

    @FXML
    void show_blog(ActionEvent event) {

    }

    @FXML
    void show_event(ActionEvent event) {

    }

    @FXML
    void show_parametres(ActionEvent event) {
        page_utilisateurs.setVisible(false);
        Modifier.setVisible(true);

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
    void show_service(ActionEvent event) {

    }

    @FXML
    void show_transport(ActionEvent event) {

    }

    @FXML
    void show_utilisateurs(ActionEvent event) {
        page_utilisateurs.setVisible(true);
        Modifier.setVisible(false);
    }

    @FXML
    void afficher_admins(ActionEvent event) {
        TableAdmins.setVisible(true);
        PageClients.setVisible(false);
        Afficher_organisateurs.setVisible(false);
        afficher_conducteurs.setVisible(false);

    }

    @FXML
    void afficher_clients(ActionEvent event) {
        TableAdmins.setVisible(false);
        PageClients.setVisible(true);
        Afficher_organisateurs.setVisible(false);
        afficher_conducteurs.setVisible(false);

    }

    @FXML
    void afficher_conducteurs(ActionEvent event) {
        TableAdmins.setVisible(false);
        PageClients.setVisible(false);
        Afficher_organisateurs.setVisible(false);
        afficher_conducteurs.setVisible(true);

    }

    @FXML
    void afficher_organisateurs(ActionEvent event) {
        TableAdmins.setVisible(false);
        Afficher_organisateurs.setVisible(true);
        PageClients.setVisible(false);
        afficher_conducteurs.setVisible(false);

    }

    @FXML
    void supprimer(ActionEvent event) {
        Admin_service a = new Admin_service();
        Client_service c = new Client_service();
        Conducteur_service cd = new Conducteur_service();
        Organisateur_service o = new Organisateur_service();

        Admin selectedAdmin = tableView.getSelectionModel().getSelectedItem();
        Client selectedClient = table_client.getSelectionModel().getSelectedItem();
        Conducteur selectedConducteur = table_conducteurs.getSelectionModel().getSelectedItem();
        Organisateur selectedOrg = table_organisateurs.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (selectedAdmin != null) {
            System.out.println("Admin sélectionné - ID: " + selectedAdmin.getId());
            System.out.println("Nom: " + selectedAdmin.getNom());
            System.out.println("Prénom: " + selectedAdmin.getPrenom());
        } else {
            System.out.println("Aucun admin sélectionné.");
        }

        if (selectedAdmin != null) {
            // Récupérer l'ID de l'admin sélectionné
            int id = selectedAdmin.getId();
            System.out.println(id);

            // Créer un objet Utilisateur pour la suppression
            Utilisateur utilisateur = new Utilisateur(
                    selectedAdmin.getId(),
                    selectedAdmin.getNom(),
                    selectedAdmin.getPrenom(),
                    selectedAdmin.getNom_utilisateur(),
                    selectedAdmin.getEmail(),
                    selectedAdmin.getMot_de_passe(),
                    selectedAdmin.getRole()
            );

            // Supprimer l'admin et l'utilisateur
            Admin_service adminService = new Admin_service();
            adminService.supprimer(selectedAdmin, utilisateur);

            // Mettre à jour la TableView
            tableView.getItems().remove(selectedAdmin);

            alert.setContentText("Admin et utilisateur supprimés avec succès.");
            alert.show();


        } else if (selectedClient != null) {
            System.out.println("Client sélectionné: " + selectedClient.getId()); // Debug
            Utilisateur utilisateurClient = new Utilisateur(
                    selectedClient.getId(),
                    selectedClient.getNom(),
                    selectedClient.getPrenom(),
                    selectedClient.getNom_utilisateur(),
                    selectedClient.getEmail(),
                    selectedClient.getMot_de_passe(),
                    selectedClient.getRole()
            );
            c.supprimer(selectedClient, utilisateurClient);
            table_client.getItems().remove(selectedClient);
            alert.setContentText("Client supprimé avec succès.");
            alert.show();
        } else if (selectedConducteur != null) {
            System.out.println("Conducteur sélectionné: " + selectedConducteur.getId()); // Debug
            Utilisateur utilisateurConducteur = new Utilisateur(
                    selectedConducteur.getId(),
                    selectedConducteur.getNom(),
                    selectedConducteur.getPrenom(),
                    selectedConducteur.getNom_utilisateur(),
                    selectedConducteur.getEmail(),
                    selectedConducteur.getMot_de_passe(),
                    selectedConducteur.getRole()
            );
            cd.supprimer(selectedConducteur, utilisateurConducteur);
            table_conducteurs.getItems().remove(selectedConducteur);
            alert.setContentText("Conducteur supprimé avec succès.");
            alert.show();
        } else if (selectedOrg != null) {
            System.out.println("Organisateur sélectionné: " + selectedOrg.getId()); // Debug
            Utilisateur utilisateurOrg = new Utilisateur(
                    selectedOrg.getId(),
                    selectedOrg.getNom(),
                    selectedOrg.getPrenom(),
                    selectedOrg.getNom_utilisateur(),
                    selectedOrg.getEmail(),
                    selectedOrg.getMot_de_passe(),
                    selectedOrg.getRole()
            );
            o.supprimer(selectedOrg, utilisateurOrg);
            table_organisateurs.getItems().remove(selectedOrg);
            alert.setContentText("Organisateur supprimé avec succès.");
            alert.show();
        } else {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.show();
        }
    }

    @FXML
    void modifier_compte(ActionEvent event) {
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        String Nom_utilisateur = nom_utilisateur.getText();
        String Email = email.getText();
        String Mot_de_passe = mdp.getText();
        String Departement = departement.getText();

        // Vérification des champs vides
        if (Nom.isEmpty() || Prenom.isEmpty() || Nom_utilisateur.isEmpty() || Email.isEmpty() || Mot_de_passe.isEmpty() || Departement.isEmpty()) {
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

        // Vérification que le département ne contient pas de majuscules
        if (!Departement.matches("[a-z]*")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le département ne doit pas contenir de majuscules.");
            alert.showAndWait();
            return;
        }

        // Si toutes les vérifications sont passées, effectuer la modification
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);

        // Appel de la méthode de modification
        Admin_service adminService = new Admin_service();
        adminService.modifier(new Admin(userId, Nom, Prenom, Nom_utilisateur, Email, Mot_de_passe, Utilisateur.Role.ADMIN, userId, Departement),
                new Utilisateur(userId, Nom, Prenom, Nom_utilisateur, Email, Mot_de_passe));

        // Alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Les informations ont été modifiées avec succès.");
        alert.show();

        List<Admin> admins = adminService.afficher(); // Utiliser la méthode correcte pour récupérer les admins

        // Créer un ObservableList à partir des admins
        ObservableList<Admin> adminsList = FXCollections.observableArrayList(admins); // Liste Observable contenant les admins

        // Lier la TableView avec l'ObservableList
        tableView.setItems(adminsList); // tableView doit être la TableView dans ton fichier FXML

        // Configurer les colonnes de la TableView
        nom_admin.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_admin.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom_utilisateur_admin.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        email_admin.setCellValueFactory(new PropertyValueFactory<>("email"));
        mot_de_passe_admin.setCellValueFactory(new PropertyValueFactory<>("mot_de_passe"));
        role_admin.setCellValueFactory(new PropertyValueFactory<>("role"));
        departement_admin.setCellValueFactory(new PropertyValueFactory<>("departement"));
    }

    @FXML
    void rechercher_admin(ActionEvent event) {
        Admin_service adminService = new Admin_service();
        List<Admin> admins = adminService.afficher();
        ObservableList<Admin> adminsList = FXCollections.observableArrayList(admins);
        adminsList.clear();

        String searchQuery = "SELECT u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, a.departement FROM UTILISATEUR u " +
                "JOIN ADMIN a ON u.id = a.id WHERE u.nom LIKE ?";

        try (
             PreparedStatement stmt = connection.prepareStatement(searchQuery)) {

            stmt.setString(1, "%" + nom_admin_a_rechercher.getText().trim() + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                adminsList.add(new Admin(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"), // Optionnel selon besoin
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getString("departement")
                ));
            }
            tableView.setItems(adminsList);
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    @FXML
    void reset_admin(ActionEvent event) {
        initialize();
        System.out.println("clicked");
    }

    @FXML
    void rechercher_client(ActionEvent event) {
        System.out.println("clicked");
        String text = nom_client_a_rechercher.getText().trim();
        System.out.println("Texte recherché : " + text);

        ObservableList<Client> clientsList = FXCollections.observableArrayList();

        String searchQuery = "SELECT u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role FROM UTILISATEUR u " +
                "JOIN client c ON u.id = c.id WHERE u.nom LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(searchQuery)) {
            stmt.setString(1, "%" + text + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientsList.add(new Client(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"), // Optionnel selon besoin
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase())
                ));
            }

            table_client.setItems(clientsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void reset_client(ActionEvent event) {
        initialize();
        System.out.println("clicked");
    }

    @FXML
    void rechercher_organisateur(ActionEvent event) {
        System.out.println("clicked");
        String text = nom_organisateur_a_rechercher.getText().trim();
        System.out.println("Texte recherché : " + text);

        ObservableList<Organisateur> organisateursList = FXCollections.observableArrayList();

        String searchQuery = "SELECT u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role ,o.num_badge FROM UTILISATEUR u " +
                "JOIN ORGANISATEUR o ON u.id = o.id WHERE u.nom LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(searchQuery)) {
            stmt.setString(1, "%" + text + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                organisateursList.add(new Organisateur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"), // Optionnel selon besoin
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("num_badge")
                ));
            }

            table_organisateurs.setItems(organisateursList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void reset_organisateur(ActionEvent event) {
        initialize();
        System.out.println("clicked");
    }

    @FXML
    void rechercher_conducteur(ActionEvent event) {
        System.out.println("clicked");
        String text = nom_conducteur_a_rechercher.getText().trim();
        System.out.println("Texte recherché : " + text);

        ObservableList<Conducteur> conducteursList = FXCollections.observableArrayList();

        String searchQuery = "SELECT u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role ,c.numero_permis FROM UTILISATEUR u " +
                "JOIN CONDUCTEUR c ON u.id = c.id WHERE u.nom LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(searchQuery)) {
            stmt.setString(1, "%" + text + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                conducteursList.add(new Conducteur(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"), // Optionnel selon besoin
                        Utilisateur.Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getInt("numero_permis")
                ));
            }

            table_conducteurs.setItems(conducteursList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void reset_conducteur(ActionEvent event) {
        initialize();
        System.out.println("clicked");
    }

    @FXML
    void OpenStatWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stat.fxml"));
            Parent root = loader.load();

            Stat controller = loader.getController();
            controller.refreshChart();

            Stage stage = new Stage();
            stage.setTitle("Statistiques des Utilisateurs");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture des statistiques : " + e.getMessage());
        }
    }

    @FXML
    void exporter(ActionEvent event) {
        String filePath = "Utilisateurs.pdf";

        try {
            PdfWriter writer = new PdfWriter(new File(filePath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Liste des Utilisateurs").setBold().setFontSize(14));

            // Création du tableau avec des colonnes ajustées
            Table table = new Table(UnitValue.createPercentArray(new float[]{1.5f, 1.5f, 2, 2.5f, 2.5f, 1.5f, 2, 2, 2}))
                    .useAllAvailableWidth();

            // Ajouter les en-têtes avec une taille de police plus petite
            String[] headers = {"Nom", "Prénom", "Nom d'utilisateur", "Email", "Mot de passe", "Rôle", "Département", "Num Badge", "Num Permis"};
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setBold().setFontSize(10)));
            }

            // Requête SQL pour récupérer tous les utilisateurs avec leurs infos spécifiques
            String query = "SELECT u.nom, u.prenom, u.nom_utilisateur, u.email, u.mot_de_passe, u.role, " +
                    "a.departement, o.num_badge, c.numero_permis " +
                    "FROM utilisateur u " +
                    "LEFT JOIN admin a ON u.id = a.id " +
                    "LEFT JOIN organisateur o ON u.id = o.id " +
                    "LEFT JOIN conducteur c ON u.id = c.id";

            try (
                 Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    table.addCell(new Cell().add(new Paragraph(rs.getString("nom")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("prenom")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("nom_utilisateur")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("email")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("mot_de_passe")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("role")).setFontSize(9)));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("departement") != null ? rs.getString("departement") : "N/A")).setFontSize(9));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("num_badge") != null ? rs.getString("num_badge") : "N/A")).setFontSize(9));
                    table.addCell(new Cell().add(new Paragraph(rs.getString("numero_permis") != null ? rs.getString("numero_permis") : "N/A")).setFontSize(9));
                }

            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }

            document.add(table);
            document.close();
            System.out.println("PDF généré avec succès : " + filePath);

        } catch (FileNotFoundException e) {
            System.out.println("Erreur de fichier : " + e.getMessage());
        }
    }




}





