package org.example.controllers;

import org.example.models.CardTrajet;
import org.example.models.Trajet;
import org.example.services.Services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;


public class Affichuser implements Initializable{
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
}
