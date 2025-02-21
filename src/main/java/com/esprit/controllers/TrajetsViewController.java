package com.esprit.controllers;

import com.esprit.models.Trajet;
import com.esprit.models.CardTrajet;
import com.esprit.services.Services;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TrajetsViewController {
    @FXML
    private FlowPane cardsContainer;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private Pagination pagination;
    @FXML
    private Label dateTimeLabel;
    @FXML
    private Button addTrajetButton;
    @FXML
    private GridPane grid;
    private int pageCount;
    private ArrayList<Trajet> getlist(){

        return services.getAllTrajet();
    }
    private Services services;
    private ObservableList<Trajet> allTrajets;
    private List<Trajet> trajets = new ArrayList<>();
    private static final int ITEMS_PER_PAGE = 8;
    private List<Trajet> getData(){

        List<Trajet> trajets = getlist();
        return trajets;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int column = 0;
        int row = 1;
        trajets.addAll(getData());
        Collections.sort(trajets, Comparator.comparing(Trajet::getDistance));

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


        searchField.setOnKeyReleased(this::filterByProductId);
        sortComboBox.getItems().addAll( "prix","distance");
        sortComboBox.setValue("nom"); // Default selection


        sortComboBox.setOnAction(event -> filterBySelectedCriteria());


        searchField.setOnKeyReleased(event -> filterBySelectedCriteria());
        int totalItems = getData().size();
        pageCount = (totalItems / ITEMS_PER_PAGE) + ((totalItems % ITEMS_PER_PAGE) > 0 ? 1 : 0);
        pagination.setPageCount(pageCount);
        pagination.setPageFactory(this::createPage);
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dateTimeLabel.setText("Date: " + now.format(formatter));
    }

    private void loadTrajets() {
        allTrajets.clear();
        allTrajets.addAll(services.getAllTrajet());
        updatePagination();
    }

    private void setupSearchListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterAndDisplayTrajets();
        });
    }

    private void setupSortListener() {
        sortComboBox.setOnAction(event -> {
            String sortOption = sortComboBox.getValue();
            if (sortOption != null) {
                switch (sortOption) {
                    case "Date (Plus récent)":
                        allTrajets.sort(Comparator.comparing(Trajet::getDateD).reversed());
                        break;
                    case "Date (Plus ancien)":
                        allTrajets.sort(Comparator.comparing(Trajet::getDateD));
                        break;
                    case "Prix (Croissant)":
                        allTrajets.sort(Comparator.comparing(Trajet::getPrix));
                        break;
                    case "Prix (Décroissant)":
                        allTrajets.sort(Comparator.comparing(Trajet::getPrix).reversed());
                        break;
                }
                updatePagination();
            }
        });
    }

    private void setupFilterListener() {
        filterComboBox.setOnAction(event -> filterAndDisplayTrajets());
    }
    private void filterByProductId(javafx.scene.input.KeyEvent event) {
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            allTrajets.clear();
            try {
                int id = Integer.parseInt(searchText);
                for (Trajet trajet : trajets) {
                    if (String.valueOf(trajet.getPrix()).startsWith(searchText)) {
                        allTrajets.add(trajet);
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
            allTrajets.addAll(trajets);
        }
        updateGridPane(pagination.getCurrentPageIndex(), ITEMS_PER_PAGE);
    }

    private void filterAndDisplayTrajets() {
        String searchText = searchField.getText().toLowerCase();
        String filterOption = filterComboBox.getValue();

        ObservableList<Trajet> filteredList = allTrajets.filtered(trajet -> {
            boolean matchesSearch = trajet.getPointD().toLowerCase().contains(searchText) ||
                    trajet.getPointA().toLowerCase().contains(searchText);

            if (filterOption == null || filterOption.equals("Tous")) {
                return matchesSearch;
            }

            LocalDateTime trajetDate = trajet.getDateD().toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();

            switch (filterOption) {
                case "Aujourd'hui":
                    return matchesSearch && trajetDate.toLocalDate().equals(now.toLocalDate());
                case "Cette semaine":
                    return matchesSearch && trajetDate.isAfter(now.minusWeeks(1));
                case "Ce mois":
                    return matchesSearch && trajetDate.isAfter(now.minusMonths(1));
                default:
                    return matchesSearch;
            }
        });

        displayTrajets(filteredList);
    }

    private void setupPagination() {
        pagination.setPageCount((int) Math.ceil((double) allTrajets.size() / ITEMS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, allTrajets.size());

        cardsContainer.getChildren().clear();

        for (int i = fromIndex; i < toIndex; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardTrajet.fxml"));
                Node card = loader.load();
                CardTrajet controller = loader.getController();
                controller.setData(allTrajets.get(i));
                cardsContainer.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cardsContainer;
    }

    private void updatePagination() {
        pagination.setPageCount((int) Math.ceil((double) allTrajets.size() / ITEMS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
    }

    private void displayTrajets(ObservableList<Trajet> trajets) {
        allTrajets = trajets;
        updatePagination();
    }

    private void handleAddTrajet() {
        // Implement navigation to add new trajet form
        // This should be implemented based on your navigation system
    }
    private void filterBySelectedCriteria() {
        String searchText = searchField.getText().trim();
        String selectedCriteria = (String) filterComboBox.getValue();

        if (!searchText.isEmpty()) {
            allTrajets.clear();
            try {
                // Filtering based on selected criteria
                for (Trajet trajet : trajets) {
                    String fieldValue = "";
                    switch (selectedCriteria) {
                        case "Distance":
                            fieldValue = String.valueOf(trajet.getDistance());
                            break;
                        case "Prix":
                            fieldValue = String.valueOf(trajet.getPrix());
                            break;

                    }
                    if (fieldValue.startsWith(searchText)) {
                        allTrajets.add(trajet);
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
            allTrajets.addAll(trajets);
        }
        updateGridPane(pagination.getCurrentPageIndex(), ITEMS_PER_PAGE);
    }
    private void updateGridPane(int pageIndex, int itemsPerPage) {
        grid.getChildren().clear();
        int row = 0;
        int col = 0;
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, allTrajets.size());

        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CardTrajet.fxml"));
                Pane anchorPane = fxmlLoader.load();
                CardTrajet prodtItemController = fxmlLoader.getController();
                prodtItemController.setData(allTrajets.get(i));

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

}