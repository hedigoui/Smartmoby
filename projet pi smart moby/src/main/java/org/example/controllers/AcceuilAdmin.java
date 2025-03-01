package org.example.controllers;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
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
import org.example.services.*;
import org.example.utils.DataSource;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.UnitValue;


import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import java.util.Map;

import javafx.scene.Node;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;


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

    public TableColumn idveh;
    @FXML
    private TableColumn<Vehicule, Integer> ca;

    @FXML
    private TextField capacite;

    @FXML
    private TableColumn<Vehicule, Integer> disp;


    @FXML
    private RadioButton non;

    @FXML
    private RadioButton oui;

    @FXML
    private TableColumn<Vehicule, String> st;

    @FXML
    private TextField stat;

    @FXML
    private TableView<Vehicule> tabv;

    @FXML
    private TableColumn<Vehicule, String> type;

    @FXML
    private ComboBox<String> vtc;

    private Services ps = new Services();

    private ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();


    @FXML
    private TextField numero_permis;



    @FXML
    private TableView<Trajet> tabt; // TableView pour afficher les trajets

    @FXML
    private TableColumn<Trajet, Integer> idtar;

    @FXML
    private TableColumn<Trajet, String> pd;

    @FXML
    private TableColumn<Trajet, String> pa;

    @FXML
    private TableColumn<Trajet, Timestamp> dd;

    @FXML
    private TableColumn<Trajet, Timestamp> da;

    @FXML
    private TableColumn<Trajet, Double> dis;

    @FXML
    private TableColumn<Trajet, Double> px;
    @FXML
    private ComboBox<Vehicule> id_veh;

    @FXML
    private TextField idt, pointd, pointa, distance, prix;

    @FXML
    private DatePicker dated, datea;


    private ObservableList<Trajet> trajetList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane ajout_trajet;

    @FXML
    private AnchorPane ajouter_transport;

    @FXML
    private DatePicker date;

    @FXML
    private TextField lieu;

    @FXML
    private TextField nom2;

    @FXML
    private AnchorPane afficher_event;

    private event_serv ps2 = new event_serv();



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


        vtc.getItems().addAll("Bus", "Métro", "Car", "Vélo", "Trottinette");

        // Associer les colonnes du TableView aux attributs de l'objet Vehicule
        idveh.setCellValueFactory(new PropertyValueFactory<>("id")); // Ajout pour afficher l'ID
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        ca.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        st.setCellValueFactory(new PropertyValueFactory<>("statut"));
        disp.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Lier les RadioButtons à un ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        oui.setToggleGroup(toggleGroup);
        non.setToggleGroup(toggleGroup);
        oui.setSelected(true); // Sélection par défaut

        // Charger les véhicules dans la TableView
        loadVehicules();



        // Associer les colonnes aux attributs de Trajet
        idtar.setCellValueFactory(new PropertyValueFactory<>("id"));
        pd.setCellValueFactory(new PropertyValueFactory<>("pointD"));
        pa.setCellValueFactory(new PropertyValueFactory<>("pointA"));
        dd.setCellValueFactory(new PropertyValueFactory<>("dateD"));
        da.setCellValueFactory(new PropertyValueFactory<>("dateA"));
        dis.setCellValueFactory(new PropertyValueFactory<>("distance"));
        px.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Charger les trajets existants
        loadTrajets();


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
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        afficher_event.setVisible(false);

    }

    @FXML
    void show_blog(ActionEvent event) {

    }

    @FXML
    void show_event(ActionEvent event) {
        page_utilisateurs.setVisible(false);
        Modifier.setVisible(false);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        afficher_event.setVisible(true);

    }

    @FXML
    void show_parametres(ActionEvent event) {
        page_utilisateurs.setVisible(false);
        Modifier.setVisible(true);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
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
    void show_service(ActionEvent event) {

    }

    @FXML
    void show_transport(ActionEvent event) {
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(true);
        Modifier.setVisible(false);
        page_utilisateurs.setVisible(false);
        afficher_event.setVisible(false);

    }

    @FXML
    void show_utilisateurs(ActionEvent event) {
        page_utilisateurs.setVisible(true);
        Modifier.setVisible(false);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        afficher_event.setVisible(false);
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
        afficher_event.setVisible(false);

    }

    @FXML
    void afficher_organisateurs(ActionEvent event) {
        TableAdmins.setVisible(false);
        Afficher_organisateurs.setVisible(true);
        PageClients.setVisible(false);
        afficher_conducteurs.setVisible(false);
        afficher_event.setVisible(false);

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
        String hashedPassword = hashPassword(Mot_de_passe);

        // Appel de la méthode de modification
        Admin_service adminService = new Admin_service();
        adminService.modifier(new Admin(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword, Utilisateur.Role.ADMIN, userId, Departement),
                new Utilisateur(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword));

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
    void supprimer2(ActionEvent event){
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);
        Admin_service adminService = new Admin_service();
        Utilisateur_service utilisateurService = new Utilisateur_service();

        Admin admin = adminService.getAdminById(userId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);

        if (admin != null && utilisateur != null) {
            // Appeler la méthode de suppression
            adminService.supprimer(admin, utilisateur);
            System.out.println("Admin et Utilisateur supprimés avec succès !");

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
            System.out.println("Admin ou Utilisateur introuvable !");
        }
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
    public void exporter(ActionEvent event) {
        String filePath = "Statistiques_Utilisateurs.pdf";

        try {
            // Création du document PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Ajouter le titre
            Paragraph title = new Paragraph("Statistiques des Utilisateurs\n\n")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Récupérer les statistiques de la base de données
            String query = "SELECT role, COUNT(*) as count FROM utilisateur GROUP BY role";
            Map<String, Integer> roleCounts = new HashMap<>();

            try (

                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    String role = rs.getString("role");
                    int count = rs.getInt("count");
                    roleCounts.put(role, count);
                }

            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
            }

            // Générer le graphique en camembert (Pie Chart)
            DefaultPieDataset dataset = new DefaultPieDataset();

            for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

            JFreeChart chart = ChartFactory.createPieChart(
                    "Répartition des utilisateurs par rôle", // Titre du graphique
                    dataset,                               // Données
                    true,                                  // Légende visible
                    true,                                  // URLs activées
                    false                                  // Ne pas inclure des info tooltips
            );

            // Convertir le graphique en image
            BufferedImage chartImage = chart.createBufferedImage(500, 300); // Taille du graphique

            // Ajouter l'image du graphique au PDF
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(chartImage, "PNG", os);
            Image img = new Image(ImageDataFactory.create(os.toByteArray()));

            // Ajouter l'image du graphique dans le PDF
            document.add(img);

            // Fermer le document
            document.close();
            System.out.println("PDF généré avec succès : " + filePath);

        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }





    @FXML
    void add(ActionEvent event) {
        // Get input values
//        int id = Integer.parseInt(idv.getText()); // Assuming ID is an integer
        String vehicleType = vtc.getValue();
        int capacity = Integer.parseInt(capacite.getText());
        String status = stat.getText();
        boolean available = oui.isSelected();

        // Create a new Vehicule object
        Vehicule vehicule = new Vehicule( vehicleType, capacity, status, available);

        // Add the vehicle using the Services class
        ps.add(vehicule);

        // Refresh the TableView
        loadVehicules();

        // Clear input fields
        clearFields();
    }
    @FXML
    public void delete(ActionEvent event) {

        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            tabv.getItems().remove(selectedItem);

            Services t = new Services();
            t.delete(selectedItem);
        }

    }

    public void update(ActionEvent event) {
        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            openUpdateWindow(selectedItem);
        } else {
            System.out.println("Aucun vehicule sélectionné !");
        }
    }

    private void openUpdateWindow(Vehicule selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateVehicule.fxml")); // Vérifie le bon fichier FXML
            Parent root = loader.load();

            // Vérifie le bon contrôleur
            UpdateVehicule controller = loader.getController();
            controller.initData(selectedItem);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Rafraîchir la liste après fermeture de la fenêtre
            stage.setOnHidden(e -> refreshListView2());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshListView2() {
        tabv.getItems().clear();

        Services t = new Services();
        ArrayList<Vehicule> items = t.getAllVehicule();

        tabv.getItems().addAll(items);
    }




    private void loadVehicules() {
        // Fetch all vehicles from the database or data source
        vehiculeList.clear();
        vehiculeList.addAll(ps.getAllVehicule());
        tabv.setItems(vehiculeList);
        Services service = new Services();
        List<Vehicule> vehicules = service.getAllVehicule(); // You need to implement this method in Services
        id_veh.setItems(FXCollections.observableArrayList(vehicules));

        // Set a cell factory to display vehicle information in ComboBox
        id_veh.setCellFactory(param -> new ListCell<Vehicule>() {
            @Override
            protected void updateItem(Vehicule item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getType() + " - " + item.getCapacite()); // Adjust based on your Vehicule class
                }
            }
        });
    }

    private void clearFields() {
//        idv.clear();
        vtc.getSelectionModel().clearSelection();
        capacite.clear();
        stat.clear();
        oui.setSelected(true);
    }
    @FXML
    void gotrajet(ActionEvent event) {
        ajout_trajet.setVisible(true);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        page_utilisateurs.setVisible(false);
    }

    @FXML
    void addtrajet(ActionEvent event) {
        try {
            // Vérifier que tous les champs sont remplis
            if (pointd.getText().isEmpty() || pointa.getText().isEmpty() ||
                    dated.getValue() == null || datea.getValue() == null ||
                    distance.getText().isEmpty() || prix.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            String pointDepart = pointd.getText();
            String pointArrivee = pointa.getText();
            double distanceValue = Double.parseDouble(distance.getText());
            double prixValue = Double.parseDouble(prix.getText());
            Vehicule selectedveh = id_veh.getValue();
            // Convertir LocalDate en Timestamp
            Timestamp dateDepart = Timestamp.valueOf(dated.getValue().atStartOfDay());
            Timestamp dateArrivee = Timestamp.valueOf(datea.getValue().atStartOfDay());

            // Vérifier que la date de départ est antérieure à la date d’arrivée
            if (dateDepart.after(dateArrivee)) {
                showAlert("Erreur", "La date de départ doit être antérieure à la date d'arrivée.");
                return;
            }
            // Créer le trajet avec le type de véhicule
            Trajet trajet = new Trajet(pointDepart, pointArrivee, dateDepart, dateArrivee,
                    distanceValue, prixValue, selectedveh.getId());
            trajet.setVehicule(selectedveh.getType()); // Set the vehicle type
            int id_veh = selectedveh.getId();

            // Créer un objet Trajet et l'ajouter
//            Trajet trajet = new Trajet(pointDepart, pointArrivee, dateDepart, dateArrivee, distanceValue, prixValue, id_veh);
            ps.addtrajet(trajet);

            // Ajouter le trajet à la TableView
            tabt.getItems().add(trajet);

            // Réinitialiser les champs
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides.");
        }
    }

    @FXML
    public void deletetrajet(ActionEvent event) {

        Trajet selectedItem = tabt.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {

            tabt.getItems().remove(selectedItem);

            Services t = new Services();
            t.delete(selectedItem);
        }

    }

    @FXML
    public void updatetrajet(ActionEvent event) {
        Trajet selectedItem = tabt.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            openUpdateWindowTrajet(selectedItem);
        }
    }
    private void openUpdateWindowTrajet(Trajet selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateTrajet.fxml"));
            Parent root = loader.load();
            UpdateTrajet controller = loader.getController();
            controller.initData(selectedItem); // Passer le trajet sélectionné

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshListView());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void refreshListView() {
        tabt.getItems().clear();

        Services t = new Services();
        ArrayList<Trajet> items = t.getAllTrajet();

        tabt.getItems().addAll(items);
    }
    private void loadTrajets() {
        trajetList.clear();
        trajetList.addAll(ps.getAllTrajet());
        tabt.setItems(trajetList);
    }





    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void gotransport(ActionEvent event) {
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(true);
        Modifier.setVisible(false);
        page_utilisateurs.setVisible(false);

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
        ps2.ajouter(newEvent);

        System.out.println("✅ Événement ajouté avec succès : " + newEvent);
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





