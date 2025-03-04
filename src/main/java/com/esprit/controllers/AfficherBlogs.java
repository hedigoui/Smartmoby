package com.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.esprit.services.MyListener;
import com.esprit.models.Blog;
import com.esprit.services.BlogService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherBlogs implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label blogTitleLabel;

    @FXML
    private Label blogContentLabel;


    @FXML
    private Button reload_btn;
    @FXML
    private Button backArrow;

    @FXML
    private TextField searchBlog_label;
    @FXML
    private Button VoyEdit_btn;
    @FXML
    private ImageView delete_button;
    @FXML
    private ComboBox<String> triList;


    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<Blog> list_blogs = new ArrayList<>();
    private ObservableList<Blog> listeObservable_blogs = FXCollections.observableArrayList(getList_blogs());
    private MyListener myListener;

    private ArrayList<Blog> getList_blogs(){
        BlogService bs = new BlogService();
        return bs.getAllBlogs();
    }
    private int setChosenBlog(Blog blog){
        blogTitleLabel.setText(blog.getTitle());
        blogContentLabel.setText(blog.getContent());
        int ID = blog.getId();

        // Check if the file exists

        return ID;
    }
    @FXML
    private void onSearchBlog(ActionEvent event) throws BlogService.ItemNotFoundException {
        String keyWord = searchBlog_label.getText();
        BlogService bs = new BlogService();
        ArrayList<Blog> foundItems = bs.findByNom(keyWord);
        listeObservable_blogs = FXCollections.observableArrayList(foundItems);
        buildGrid();
    }
    private void onSearchBlogLabel() throws BlogService.ItemNotFoundException {
        String keyWord = searchBlog_label.getText();
        BlogService bs = new BlogService();
        ArrayList<Blog> foundItems = bs.findByNom(keyWord);
        listeObservable_blogs = FXCollections.observableArrayList(foundItems);
        buildGrid();
    }
    @FXML
    private void onTrierBlog(ActionEvent event){
        BlogService bs = new BlogService();
        ArrayList<Blog> sortedItems = new ArrayList<>();
        String sortingMethod = triList.getSelectionModel().getSelectedItem();
        /*
        switch(sortingMethod){
            case "Par Type" :
                sortedItems = bs.trierBlogParDate();
                break;
            case "Par Nom" :
                sortedItems = bs.trierBlogParTitle();
                break;

        }

         */
        listeObservable_blogs = FXCollections.observableArrayList(sortedItems);
        buildGrid();
    }
    @FXML
    public void OnBlogEdit(ActionEvent event) throws IOException {
        // Retrieve the chosen voyage name
        String title = blogTitleLabel.getText();
        Blog chosenvoyage = null; // Declare without initialization
        // Loop through the list of voyages to find the chosen voyage
        for (Blog unit : listeObservable_blogs) {
            if (unit.getTitle().equals(title)) {
                chosenvoyage = unit; // Assign the found voyage
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the voyage
            }
        }
        // Check if the chosen voyage was found
        if (chosenvoyage != null) {
            // Pass the chosen voyage to the next page
            System.out.println("IM NAVIGATING");
            navigateToModifierBlog(chosenvoyage,event);
        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen voyage not found: " + title);
        }
    }
    @FXML
    public void OnBlogMore(ActionEvent event) throws IOException {
        // Retrieve the chosen voyage name
        String title = blogTitleLabel.getText();
        Blog chosenvoyage = null; // Declare without initialization
        // Loop through the list of voyages to find the chosen voyage
        for (Blog unit : listeObservable_blogs) {
            if (unit.getTitle().equals(title)) {
                chosenvoyage = unit; // Assign the found voyage
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the voyage
            }
        }
        // Check if the chosen voyage was found
        if (chosenvoyage != null) {
            // Pass the chosen voyage to the next page
            System.out.println("IM NAVIGATING");
            navigateToMore(chosenvoyage,event);
        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen voyage not found: " + title);
        }
    }






    @FXML
    void navigateToAjouterblog(MouseEvent event) {
        System.out.println("Je navigue");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/AddBlog.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }


    private void navigateToMore(Blog chosenBlog, ActionEvent event) throws IOException {
        // Load the FXML file of the next page
        System.out.println("I NAVIGATED");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlogDetails.fxml"));
        root = loader.load();

        // Récupérer le controller de la scene suivante
        EditBlog Controller = loader.getController();
        // Passer chosen blog au controller suivant
        Controller.getNameToSearchWith(chosenBlog);
        Controller.setData(chosenBlog);

        //montrer la page suivante
        Node sourceNode = (Node) event.getSource();
        stage = (Stage) sourceNode.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void navigateToModifierBlog(Blog chosenBlog, ActionEvent event) throws IOException {
        // Load the FXML file of the next page
        System.out.println("I NAVIGATED");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditBlog.fxml"));
        root = loader.load();

        // Récupérer le controller de la scene suivante
        EditBlog Controller = loader.getController();
        // Passer chosen blog au controller suivant
        Controller.getNameToSearchWith(chosenBlog);
        Controller.setData(chosenBlog);

        //montrer la page suivante
        Node sourceNode = (Node) event.getSource();
        stage = (Stage) sourceNode.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void navigateToHome(ActionEvent event) {
        // Load the FXML file of the next page
        System.out.println("I NAVIGATED");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Node sourceNode = (Node) event.getSource();
        stage = (Stage) sourceNode.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    void deleteBlog(MouseEvent event) {
        // Retrieve the chosen blog name
        String name = blogTitleLabel.getText();

        Blog chosenBlog = null; // Declare without initialization
        // Loop through the list of blogs to find the chosen blog
        for (Blog unit : listeObservable_blogs) {
            if (unit.getTitle().equals(name)) {
                chosenBlog = unit; // Assign the found blog
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the blog
            }
        }
        // Check if the chosen blog was found
        if (chosenBlog != null) {
            // Pass the chosen blog to the next page
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de la suppression");
            alert.setContentText("Etes-vous sûre de vouloir supprimer cet item ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.out.println("IM deleting");
                BlogService bs = new BlogService();
                bs.deleteBlog(chosenBlog.getId());

                // Update the observable list by removing the deleted blog
                listeObservable_blogs.remove(chosenBlog);
                // Update the UI by rebuilding the grid
                buildGrid();

            }
            else{
                alert.close();
            }
        } else {
            // Handle case where the chosen blog is not found
            System.out.println("Chosen blog not found: " + name);
        }

    }
    private boolean isGridEmpty(){
        ObservableList<Node> children = grid.getChildren();
        return children.isEmpty();
    }
    private void buildGrid() {
        if(!isGridEmpty()){
            grid.getChildren().clear(); // Clear the grid
        }
        int column = 0;
        int row = 1;
        try {
            for (Blog blog : listeObservable_blogs) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/BlogItem1.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                BlogItem controller = fxmlLoader.getController();
                controller.setData(blog, myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Construire la grille
        if(!listeObservable_blogs.isEmpty()){
            setChosenBlog(listeObservable_blogs.get(0));
            myListener = new MyListener(){
                @Override
                public void onClickListener(Blog blog) {
                    setChosenBlog(blog);
                }
            };
        }
        buildGrid();
        //Initialiser le combo box de Tri
        triList.setPromptText("Trier les blogs selon...");
        triList.getItems().addAll("Par Date", "Par Title");

        //Initialiser les boutons


        //dynamic search
        searchBlog_label.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    onSearchBlogLabel();
                } catch (BlogService.ItemNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });




    }

    @FXML
    void deleteblog(MouseEvent event) {
        // Retrieve the chosen voyage name
        String title = blogTitleLabel.getText();

        Blog chosenblog = null;
        for (Blog unit : list_blogs) {
            if (unit.getTitle().equals(title)) {
                chosenblog = unit;
                System.out.println("the thing worked");
                break;
            }
        }

        if (chosenblog != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de la suppression");
            alert.setContentText("Etes-vous sûre de vouloir supprimer cet item ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.out.println(" deleting");
                BlogService sv = new BlogService();
                sv.delete(chosenblog);


                list_blogs.remove(chosenblog);
                // Update the UI by rebuilding the grid
                buildGrid();

            }
            else{
                alert.close();
            }
        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen blog not found: " + title);
        }

    }




    public void onMore(ActionEvent actionEvent) {
    }

    public void onblogedit(ActionEvent actionEvent) {
    }





}