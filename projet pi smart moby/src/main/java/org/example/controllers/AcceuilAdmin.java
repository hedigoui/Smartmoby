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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
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
import org.example.models.Produit;
import org.example.services.PDFGenerator;
import org.example.services.SMSService;
import org.example.utils.DataSource;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import static org.example.services.ServiceDAO.conn;

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
import org.example.models.Service;
import org.example.services.ServiceDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.models.event;
import org.example.models.fedback;
import org.example.services.event_serv;
import org.example.services.fedback_serv;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



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
    private TableColumn<Vehicule, Integer> idveh;

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

    @FXML
    private ComboBox<Conducteur> conducteurCombo;

    private event_serv ps2 = new event_serv();

    @FXML private TextField txtNomP, txtTypeP, txtPrixP;
    @FXML private ListView<String> listViewP;
    @FXML private TextField searchProduit;
    @FXML private Button btnAjouterP, btnModifierP, btnSuppP, btnVersService;

    @FXML private Pane displayPane;
    @FXML private Pane managePane;

    @FXML private Pane displayPane1;
    @FXML private Pane managePane1;

    public SMSService smsService = new SMSService();

    private ObservableList<String> produitList = FXCollections.observableArrayList();

    @FXML private Button btnAdd, btnModifier, btnSupprimer;
    @FXML private TableColumn<Service, String> desc_c;
    @FXML private TableColumn<Service, Integer> id_c;
    @FXML private TableColumn<Service, String> nom_c;
    @FXML private TableView<Service> table;
    @FXML private TableColumn<Service, Double> tarif_c;
    @FXML private TextField txtDescription, txtNom, txtTarif;
    @FXML private TextField searchService;

    private ObservableList<Service> serviceList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane afficher_produit;

    @FXML
    private AnchorPane afficher_service;

    @FXML
    private ListView<event> liste;

    @FXML
    private TextField searchField;

    @FXML
    private Button supp;

    @FXML
    private Button modif;

    @FXML
    private Button feedback;

    @FXML
    private ComboBox<String> triComboBox;


    private final event_serv eventService = new event_serv();
    private final ObservableList<event> eventList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane afficher_evenement;

    @FXML
    private ListView<event> liste1; // ListView pour afficher les événements



    @FXML
    private TextArea feedbackInput; // TextArea pour le feedback

    @FXML
    private Slider noteSlider; // Slider pour la note

    @FXML
    private Label noteLabel; // Label pour afficher la note

    @FXML
    private Button ajouterFedbackButton; // Bouton pour ajouter un feedback




    private final fedback_serv fedbackService = new fedback_serv();
    private event selectedEvent; // L'événement sélectionné
    private fedback selectedFeedback;









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


        vtc.getItems().addAll("Car", "Taxi", "Métro", "Bus", "Moto");

        // Associer les colonnes du TableView aux attributs de l'objet Vehicule
        idveh.setCellValueFactory(new PropertyValueFactory<>("id"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        ca.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        st.setCellValueFactory(new PropertyValueFactory<>("statut"));
        disp.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        // Lier les RadioButtons à un ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();
        oui.setToggleGroup(toggleGroup);
        non.setToggleGroup(toggleGroup);
        oui.setSelected(true);

        // Charger les véhicules dans la TableView
        loadVehicules();

        // Charger les conducteurs dans le ComboBox
        loadConducteurs();


        loadVehicules1();

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

        id_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nom_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNom()));
        desc_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        tarif_c.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTarif()).asObject());

        loadServices();

        table.setOnMouseClicked(event -> {
            if (!table.getSelectionModel().isEmpty()) {
                Service selectedService = table.getSelectionModel().getSelectedItem();
                txtNom.setText(selectedService.getNom());
                txtDescription.setText(selectedService.getDescription());
                txtTarif.setText(String.valueOf(selectedService.getTarif()));
            }
        });

        InitComboBox();
        chargerListe();

        // Ajout du filtrage dynamique
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filtrerEvenements(newValue));

        // Personnalisation de l'affichage des éléments dans la ListView
        liste.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(event ev, boolean empty) {
                super.updateItem(ev, empty);
                if (empty || ev == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Création d'une boîte HBox pour afficher l'événement
                    HBox box = new HBox(10);
                    javafx.scene.text.Text nomEvent = new javafx.scene.text.Text(ev.getNom());
                    javafx.scene.text.Text dateEvent = new javafx.scene.text.Text("📅 " + ev.getDate());
                    javafx.scene.text.Text lieuEvent = new Text("📍 " + ev.getLieu());

                    // Ajout des éléments à la boîte
                    box.getChildren().addAll(nomEvent, dateEvent, lieuEvent);
                    setGraphic(box);
                }
            }
        });

        chargerListe();



        System.out.println("📌 Initialisation du contrôleur...");

        if (liste == null) {
            System.out.println("⚠️ Erreur : ListView n'est pas initialisée !");
            return;
        }

        // Charger les événements dans la liste
        loadEvents();

        // Listener pour la sélection d'événement
        liste.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                selectedEvent = newValue;
                System.out.println("✅ Événement sélectionné : " + newValue.getNom());
            }
        });




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
        afficher_evenement.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);

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
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        afficher_evenement.setVisible(false);
        afficher_evenement.setVisible(false);

    }

    @FXML
    void show_parametres(ActionEvent event) {
        page_utilisateurs.setVisible(false);
        Modifier.setVisible(true);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        afficher_evenement.setVisible(false);

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
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        page_utilisateurs.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(true);
        afficher_service.setVisible(false);
        afficher_evenement.setVisible(false);

    }

    @FXML
    void show_transport(ActionEvent event) {
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(true);
        Modifier.setVisible(false);
        page_utilisateurs.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        afficher_evenement.setVisible(false);

    }

    @FXML
    void show_utilisateurs(ActionEvent event) {
        page_utilisateurs.setVisible(true);
        Modifier.setVisible(false);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
        Modifier.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        afficher_evenement.setVisible(false);
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
        String vehicleType = vtc.getValue();
        int capacity = Integer.parseInt(capacite.getText());
        String status = stat.getText();
        boolean available = oui.isSelected();
        Conducteur selectedConducteur = conducteurCombo.getValue();
        Integer conducteurId = selectedConducteur != null ? selectedConducteur.getId() : null;

        // Create a new Vehicule object
        Vehicule vehicule = new Vehicule(vehicleType, capacity, status, available, conducteurId);

        // Add the vehicle using the Services class
        ps.add(vehicule);

        // Refresh the TableView
        loadVehicules();
        initialize();

        // Clear input fields
        clearFields();
    }

    @FXML
    public void delete(ActionEvent event) {
        Vehicule selectedItem = tabv.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            tabv.getItems().remove(selectedItem);
            ps.delete(selectedItem);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateVehicule.fxml"));
            Parent root = loader.load();
            UpdateVehicule controller = loader.getController();
            controller.initData(selectedItem);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setOnHidden(e -> refreshListView());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshListView() {
        tabv.getItems().clear();
        ArrayList<Vehicule> items = ps.getAllVehicule();
        tabv.getItems().addAll(items);
    }

    private void loadVehicules1() {
        vehiculeList.clear();
        vehiculeList.addAll(ps.getAllVehicule());
        tabv.setItems(vehiculeList);
    }

    private void loadConducteurs() {
        List<Conducteur> conducteurs = ps.getAllConducteurs();
        conducteurCombo.setItems(FXCollections.observableArrayList(conducteurs));
        conducteurCombo.setCellFactory(param -> new ListCell<Conducteur>() {
            @Override
            protected void updateItem(Conducteur item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom() + " " + item.getPrenom() + " - " + item.getNumero_permis());
                }
            }
        });
    }

    private void clearFields() {
        vtc.getSelectionModel().clearSelection();
        capacite.clear();
        stat.clear();
        oui.setSelected(true);
        conducteurCombo.getSelectionModel().clearSelection();
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
            stage.setOnHidden(e -> refreshListView3());
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void refreshListView3() {
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

    private void loadVehicules() {
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
        afficher_evenement.setVisible(true);
        afficher_event.setVisible(false);
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
        initialize();
    }


    // Méthode de validation (exemple de validation basique)
    private boolean isValidEvent(event newEvent) {
        // Ajouter ici d'autres règles de validation selon le modèle d'événement
        return newEvent.getNom() != null && !newEvent.getNom().isEmpty() &&
                newEvent.getLieu() != null && !newEvent.getLieu().isEmpty() &&
                newEvent.getDate() != null;
    }

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
    public void showDisplaySection2() {
        displayPane1.setVisible(true);
        managePane1.setVisible(false);
    }

    @FXML
    public void showManageSection2() {
        displayPane1.setVisible(false);
        managePane1.setVisible(true);
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

        try (
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
    public void generatePDF() {
        List<String> products = listViewP.getItems();

        if (!products.isEmpty()) {
            try {
                // Spécifiez le chemin de votre fichier PDF
                String filePath = "product_list.pdf";


                PDFGenerator.generatePDFFromStringList(products, filePath);

                // Afficher une alerte de succès
                showAlert(Alert.AlertType.INFORMATION, "PDF généré avec succès", "Le PDF a été créé avec succès à l'emplacement : " + filePath);
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur lors de la génération du PDF", "Une erreur est survenue : " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Liste vide", "Il n'y a pas de produits dans la liste.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
        try (
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nom);
            stmt.setString(2, type);
            stmt.setDouble(3, prix);
            stmt.executeUpdate();
            showAlert("Succès", "Produit ajouté avec succès !", Alert.AlertType.INFORMATION);

            //   smsService.sendSMS("+21623039225","produit crée");
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
        try (
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
            try (
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
        afficher_produit.setVisible(false);
        afficher_service.setVisible(true);
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

    private void loadServices() {
        serviceList.clear();
        try {
            List<Service> services = ServiceDAO.getAllServices();
            serviceList.addAll(services);
            table.setItems(serviceList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les services.", Alert.AlertType.ERROR);
        }
    }

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
    public void searchService() {
        String filter = searchService.getText().toLowerCase();
        ObservableList<Service> filteredList = FXCollections.observableArrayList();

        for (Service service : serviceList) {
            if (service.getNom().toLowerCase().contains(filter) ||
                    service.getDescription().toLowerCase().contains(filter) ||
                    Double.valueOf(service.getTarif()).toString().toLowerCase().contains(filter)) {
                filteredList.add(service);
            }
        }

        table.setItems(filteredList);
    }
    @FXML
    void Add(ActionEvent event) {
        if (!validerChamps()) return; // Validation avant ajout

        try {
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            double tarif = Double.parseDouble(txtTarif.getText());

            ServiceDAO.ajouterService(new Service(0, nom, description, tarif));
            showAlert("Succès", "Service ajouté avec succès.", Alert.AlertType.INFORMATION);
            loadServices();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible d'ajouter le service.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        Service selectedService = table.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert("Erreur", "Veuillez sélectionner un service à modifier.", Alert.AlertType.ERROR);
            return;
        }

        if (!validerChamps()) return; // Validation avant modification

        try {
            selectedService = new Service(selectedService.getId(), txtNom.getText(), txtDescription.getText(), Double.parseDouble(txtTarif.getText()));
            ServiceDAO.modifierService(selectedService);
            showAlert("Succès", "Service modifié avec succès.", Alert.AlertType.INFORMATION);
            loadServices();
            clearFields();
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de modifier le service.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        Service selectedService = table.getSelectionModel().getSelectedItem();
        if (selectedService == null) {
            showAlert("Erreur", "Veuillez sélectionner un service à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce service ?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            try {
                ServiceDAO.supprimerService(selectedService.getId());
                showAlert("Succès", "Service supprimé avec succès.", Alert.AlertType.INFORMATION);
                loadServices();
                clearFields();
            } catch (SQLException e) {
                showAlert("Erreur", "Impossible de supprimer le service.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void VersProduit(ActionEvent event) {
        afficher_produit.setVisible(true);
        afficher_service.setVisible(false);
    }


    /**
     * Vérifie si les champs sont bien remplis avant d'ajouter ou modifier un service.
     */
    private boolean validerChamps() {
        String nom = txtNom.getText().trim();
        String description = txtDescription.getText().trim();
        String tarifStr = txtTarif.getText().trim();

        if (nom.isEmpty() || description.isEmpty() || tarifStr.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.", Alert.AlertType.ERROR);
            return false;
        }

        try {
            double tarif = Double.parseDouble(tarifStr);
            if (tarif <= 0) {
                showAlert("Erreur", "Le tarif doit être un nombre positif.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le tarif doit être un nombre valide.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    //oussema

    public void chargerListe() {
        List<event> events = eventService.afficher();
        if (events.isEmpty()) {
            afficherAlerte("Aucun événement", "⚠️ Aucun événement trouvé en base.", Alert.AlertType.WARNING);
        }
        eventList.setAll(events);
        liste.setItems(eventList);
    }

    @FXML
    private void supprimer_event(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Suppression de l'événement");
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            boolean deleted = eventService.supprimer(selectedEvent);
            if (deleted) {
                eventList.remove(selectedEvent);
                afficherAlerte("Succès", "L'événement '" + selectedEvent.getNom() + "' a été supprimé.", Alert.AlertType.INFORMATION);
            } else {
                afficherAlerte("Erreur", "Échec de la suppression. Vérifiez que l'événement existe toujours.", Alert.AlertType.ERROR);
            }
        }
        initialize();
    }

    @FXML
    private void modifier(ActionEvent event) {
        System.out.println("clicked");
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        System.out.println("selectedEvent: " + selectedEvent);
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à modifier.", Alert.AlertType.ERROR);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            modifierEvent controller = loader.getController();
            controller.initData(selectedEvent, this); // Passer l'événement sélectionné

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier un événement");
            stage.showAndWait();

            chargerListe();  // Rafraîchir la liste après modification

        } catch (IOException e) {
            afficherAlerte("Erreur", "❌ Impossible d'ouvrir la fenêtre de modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Méthode pour ouvrir la page des feedbacks
    @FXML
    private void voirFeedback(ActionEvent event) {
        event selectedEvent = liste.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement pour voir les feedbacks.", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Charger la scène de feedback
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajouter_fedback.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Passer l'événement sélectionné au contrôleur de feedback
            ajouterfedback controller = loader.getController();
            controller.setEvent(selectedEvent);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Feedbacks de l'événement");
            stage.showAndWait();

        } catch (IOException e) {
            afficherAlerte("Erreur", "❌ Impossible d'ouvrir la fenêtre de feedbacks : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    // Méthode pour filtrer les événements en fonction du champ de recherche
    private void filtrerEvenements(String keyword) {
        ObservableList<event> filteredList = FXCollections.observableArrayList();
        for (event ev : eventList) {
            if (ev.getNom().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(ev);
            }
        }
        liste.setItems(filteredList);
    }

    // Méthode pour afficher des alertes avec différents types
    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void InitComboBox() {
        // Initialiser la ComboBox avec les options de tri par date
        triComboBox.setItems(FXCollections.observableArrayList(
                "Faire tri par date",
                "Tri par date ascendante",
                "Tri par date descendante"
        ));

        // Définir la valeur par défaut
        triComboBox.setValue("Faire tri par date");

        // Ajouter un listener pour trier les événements en fonction du choix
        triComboBox.valueProperty().addListener((observable, oldValue, newValue) -> trierListeParDate(newValue));
    }

    private void trierListeParDate(String critere) {
        switch (critere) {
            case "Tri par date ascendante":
                FXCollections.sort(eventList, (e1, e2) -> e1.getDate().compareTo(e2.getDate()));
                break;
            case "Tri par date descendante":
                FXCollections.sort(eventList, (e1, e2) -> e2.getDate().compareTo(e1.getDate()));
                break;
        }
        liste.setItems(eventList);
    }

    private void loadEvents() {
        try {
            ObservableList<event> events = FXCollections.observableArrayList(eventService.getAllEvents());
            liste.setItems(events);
            liste.setCellFactory(param -> new ListCell<event>() {
                @Override
                protected void updateItem(event item, boolean empty) {
                    super.updateItem(item, empty);
                    setText((empty || item == null) ? null : item.getNom());
                }
            });
        } catch (Exception e) {
            System.out.println("❌ Erreur lors du chargement des événements : " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void setEvent(event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }




    public void initData(fedback feedback) {
        this.selectedFeedback = feedback;

    }

    @FXML
    public void retour_ajouter_event(ActionEvent event){
        afficher_event.setVisible(true);
        afficher_evenement.setVisible(false);
    }





}





