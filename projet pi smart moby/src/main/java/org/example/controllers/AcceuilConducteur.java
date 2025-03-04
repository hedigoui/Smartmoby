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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AcceuilConducteur {
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
    private AnchorPane Modifier;

    @FXML
    private TextField numero_permis;

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
    private ComboBox<Conducteur> conducteurCombo;

    @FXML
    void initialize() {

        refreshListView();
        refreshListView3();
        loadVehicules1();
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
        loadVehicules1();
        loadVehicules();
    }



    @FXML
    void modifier_compte(ActionEvent event) {
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        String Nom_utilisateur = nom_utilisateur.getText();
        String Email = email.getText();
        String Mot_de_passe = mdp.getText();
        int Num = Integer.valueOf(numero_permis.getText());

        // Vérification des champs vides
        if (Nom.isEmpty() || Prenom.isEmpty() || Nom_utilisateur.isEmpty() || Email.isEmpty() || Mot_de_passe.isEmpty() || String.valueOf(Num).isEmpty()) {
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
        if (!String.valueOf(Num).matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de validation");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de permis doit être composé de 8 chiffres.");
            alert.showAndWait();
            return;
        }



        // Si toutes les vérifications sont passées, effectuer la modification
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);
        String hashedPassword = hashPassword(Mot_de_passe);

        // Appel de la méthode de modification
        Conducteur_service conducteurService = new Conducteur_service();
        conducteurService.modifier(new Conducteur(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword, Utilisateur.Role.CONDUCTEUR, userId,Num ),
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
        Conducteur_service conducteurService = new Conducteur_service();
        Utilisateur_service utilisateurService = new Utilisateur_service();

        Conducteur conducteur = conducteurService.getConducteurById(userId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);

        if (conducteur != null && utilisateur != null) {
            // Appeler la méthode de suppression
            conducteurService.supprimer(conducteur, utilisateur);
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
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
    }

    @FXML
    void show_parametres(ActionEvent event) {
        Modifier.setVisible(true);
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(false);
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
    void show_transport(ActionEvent event) {
        ajout_trajet.setVisible(false);
        ajouter_transport.setVisible(true);
        Modifier.setVisible(false);

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

    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }







}
