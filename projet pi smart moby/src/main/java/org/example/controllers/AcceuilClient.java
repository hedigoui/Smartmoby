package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.models.*;
import org.example.services.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.*;


import org.example.services.PDFGenerator;
import org.example.services.SMSService;


import java.sql.*;
import java.util.List;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.example.services.ServiceDAO.conn;

public class AcceuilClient implements Initializable {

    @FXML
    private AnchorPane Modifier;

    @FXML
    private AnchorPane Afficher_Transport;

    @FXML
    private TextField departement;

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
    public ComboBox byCbox;
    @FXML
    public TextField chercherTF;
    Services t = new Services();
    @FXML private Button weatherButton;
    @FXML
    private Button statisticsButton;
    private Stage weatherStage;

    private ArrayList<Trajet> getlist(){

        return t.getAllTrajet();
    }
    @FXML
    private ImageView exit;
    private Parent parent;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button back;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scrol;
    @FXML
    private Pagination pagination;

    private final int itemsPerPage = 3; // Number of items per page
    private int pageCount;
    private List<Trajet> trajets = new ArrayList<>();
    private ObservableList<Trajet> filteredStock = FXCollections.observableArrayList();
    private List<Trajet> getData(){

        List<Trajet> trajets = getlist();
        return trajets;
    }

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
    private AnchorPane afficher_event;

    @FXML private TextField txtNomP, txtTypeP, txtPrixP;
    @FXML private ListView<String> listViewP;
    @FXML private TextField searchProduit;
    @FXML private Button btnAjouterP, btnModifierP, btnSuppP, btnVersService;

    @FXML private Pane displayPane;
    @FXML private Pane managePane;

    @FXML private Button btnAdd, btnModifier, btnSupprimer;
    @FXML private TableColumn<Service, String> desc_c;
    @FXML private TableColumn<Service, Integer> id_c;
    @FXML private TableColumn<Service, String> nom_c;
    @FXML private TableView<Service> table;
    @FXML private TableColumn<Service, Double> tarif_c;
    @FXML private TextField txtDescription, txtNom, txtTarif;
    @FXML private TextField searchService;

    @FXML
    private AnchorPane afficher_produit;

    @FXML
    private AnchorPane afficher_service;

    @FXML
    private AnchorPane acceuil1;


    private ObservableList<Service> serviceList = FXCollections.observableArrayList();


    public SMSService smsService = new SMSService();

    private ObservableList<String> produitList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        int column = 0;
        int row = 1;
        trajets.addAll(getData());
        Collections.sort(trajets, Comparator.comparing(Trajet::getPrix));


        try {
            for (int i = 0; i < trajets.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardTrajet.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardTrajet cardTrajet = fxmlLoader.getController();
                cardTrajet.setData(trajets.get(i));

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        exit.setOnMouseClicked(event -> {
                    System.exit(0);
                }
        );
        chercherTF.setOnKeyReleased(this::filterByProductId);
        byCbox.getItems().addAll( "type vehicule","point destination","point depart" );
        byCbox.setValue("type vehicule"); // Default selection


        byCbox.setOnAction(event -> filterBySelectedCriteria());


        chercherTF.setOnKeyReleased(event -> filterBySelectedCriteria());
        int totalItems = getData().size();
        pageCount = (totalItems / itemsPerPage) + ((totalItems % itemsPerPage) > 0 ? 1 : 0);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);

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
                    Text nomEvent = new Text(ev.getNom());
                    Text dateEvent = new Text("📅 " + ev.getDate());
                    Text lieuEvent = new Text("📍 " + ev.getLieu());

                    // Ajout des éléments à la boîte
                    box.getChildren().addAll(nomEvent, dateEvent, lieuEvent);
                    setGraphic(box);
                }
            }
        });

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
    }

    @FXML
    void back(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/affiche.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Node createPage(int pageIndex) {
        grid.getChildren().clear();
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, getData().size());

        List<Trajet> currentPageItems = getData().subList(startIndex, endIndex);

        int row = 0;
        int col = 0;
        for (Trajet s : currentPageItems) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardTrajet.fxml"));
                Pane anchorPane = fxmlLoader.load();
                CardTrajet prodtItemController = fxmlLoader.getController();
                prodtItemController.setData(s);
                grid.add(anchorPane, col++, row);
                GridPane.setMargin(anchorPane, new Insets(10));

                if (col == 3) { // Change to the number of columns in your grid
                    col = 0;
                    row++;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return grid;
    }

    private void filterByProductId(javafx.scene.input.KeyEvent event) {
        String searchText = chercherTF.getText().trim();
        if (!searchText.isEmpty()) {
            filteredStock.clear();
            try {
                int productId = Integer.parseInt(searchText);
                for (Trajet trajet : trajets) {
                    if (String.valueOf(trajet.getPrix()).startsWith(searchText)) {
                        filteredStock.add(trajet);
                    }
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid product ID.");
                alert.showAndWait();
                ex.printStackTrace();
            }
        } else {
            filteredStock.addAll(trajets);
        }
        updateGridPane(pagination.getCurrentPageIndex(), itemsPerPage);
    }
    private void updateGridPane(int pageIndex, int itemsPerPage) {
        grid.getChildren().clear();
        int row = 0;
        int col = 0;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, filteredStock.size());

        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardTrajet.fxml"));
                Pane anchorPane = fxmlLoader.load();
                CardTrajet prodtItemController = fxmlLoader.getController();
                prodtItemController.setData(filteredStock.get(i));

                if (col == 3) { // Adjusted column count condition
                    col = 0;
                    row++;
                }
                grid.add(anchorPane, col++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void filterBySelectedCriteria() {
        String searchText = chercherTF.getText().trim();
        String selectedCriteria = (String) byCbox.getValue();

        if (!searchText.isEmpty()) {
            filteredStock.clear();
            try {
                // Filtering based on selected criteria
                for (Trajet trajet : trajets) {
                    String fieldValue = "";
                    switch (selectedCriteria) {
                        case "type vehicule":
                            fieldValue = String.valueOf(trajet.getVehicule());
                            break;
                        case "point destination":
                            fieldValue = String.valueOf(trajet.getPointA());
                            break;
                        case "point depart":
                            fieldValue = String.valueOf(trajet.getPointD());
                            break;


                    }
                    if (fieldValue.startsWith(searchText)) {
                        filteredStock.add(trajet);
                    }
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid field value.");
                alert.showAndWait();
                ex.printStackTrace();
            }
        } else {
            filteredStock.addAll(trajets);
        }
        updateGridPane(pagination.getCurrentPageIndex(), itemsPerPage);
    }

    @FXML
    void showWeatherWidget(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/weather-widget.fxml"));
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
    private void showStatistics(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/statistics-view.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifier_compte(ActionEvent event) {
        String Nom = nom.getText();
        String Prenom = prenom.getText();
        String Nom_utilisateur = nom_utilisateur.getText();
        String Email = email.getText();
        String Mot_de_passe = mdp.getText();

        // Vérification des champs vides
        if (Nom.isEmpty() || Prenom.isEmpty() || Nom_utilisateur.isEmpty() || Email.isEmpty() || Mot_de_passe.isEmpty() ) {
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





        // Si toutes les vérifications sont passées, effectuer la modification
        int userId = Session.getUserId();
        System.out.println("L'ID de l'utilisateur connecté est : " + userId);
        String hashedPassword = hashPassword(Mot_de_passe);

        // Appel de la méthode de modification
        Client_service clientService = new Client_service();
        clientService.modifier(new Client(userId, Nom, Prenom, Nom_utilisateur, Email, hashedPassword, Utilisateur.Role.CLIENT, userId),
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
        Client_service clientService = new Client_service();
        Utilisateur_service utilisateurService = new Utilisateur_service();

        Client client = clientService.getClientById(userId);
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(userId);

        if (client != null && utilisateur != null) {
            // Appeler la méthode de suppression
            clientService.supprimer(client, utilisateur);
            System.out.println("Client et Utilisateur supprimés avec succès !");

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
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        acceuil1.setVisible(true);


    }

    @FXML
    void show_blog(ActionEvent event) {

    }

    @FXML
    void show_event(ActionEvent event) {
        Modifier.setVisible(false);
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(true);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        acceuil1.setVisible(false);

    }

    @FXML
    void show_parametres(ActionEvent event) {
        Modifier.setVisible(true);
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        acceuil1.setVisible(false);
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
        Modifier.setVisible(false);
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(true);
        afficher_service.setVisible(false);
        acceuil1.setVisible(false);

    }

    @FXML
    void show_transport(ActionEvent event) {
        Modifier.setVisible(false);
        Afficher_Transport.setVisible(true);
        afficher_event.setVisible(false);
        afficher_produit.setVisible(false);
        afficher_service.setVisible(false);
        acceuil1.setVisible(false);

    }

    @FXML
    void AI(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatBOT.fxml"));
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

    public void chargerListe() {
        List<event> events = eventService.afficher();
        if (events.isEmpty()) {
            afficherAlerte("Aucun événement", "⚠️ Aucun événement trouvé en base.", Alert.AlertType.WARNING);
        }
        eventList.setAll(events);
        liste.setItems(eventList);
    }

    @FXML
    private void supprimer(ActionEvent event) {
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
        Modifier.setVisible(false);
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(false);
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
        Modifier.setVisible(false);
        Afficher_Transport.setVisible(false);
        afficher_event.setVisible(false);
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

    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
        txtTarif.clear();
    }


}
