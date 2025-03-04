package com.esprit.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.esprit.models.Blog;
import com.esprit.services.BlogService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class EditBlog {


    @FXML
    private Button modifier_btn;

    @FXML
    private TextField oldTitle;

    @FXML
    private TextField oldContent;

    @FXML
    private DatePicker oldDate;

    BlogService sv = new BlogService();
    ObservableList<Blog> observableListBlogs = sv.getAll();

    // Convert ObservableList to ArrayList
    ArrayList<Blog> list_blogs = new ArrayList<>(observableListBlogs);


    private String nameToSearchWith ="";
    void getNameToSearchWith(Blog blog){
        nameToSearchWith = blog.getTitle();
        System.out.println("the word is :" + nameToSearchWith);
    }

    void setData(Blog blog){
        oldTitle.setText(blog.getTitle());
        oldContent.setText(blog.getContent());
        //oldDate.setValue(blog.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        System.out.println("WE ARE PUTTING STUFF HERE");
    }


    @FXML
    void EditBlog(ActionEvent event) {
        Blog chosenBlog = null;
        for (Blog unit : list_blogs) {
            System.out.println("the unit is named : " + unit.getTitle());
            if (unit.getTitle().toLowerCase().equals(nameToSearchWith.toLowerCase())) {
                chosenBlog = unit;
                break; // Break out of the loop after finding the blog
            }
        }
        if (chosenBlog == null) {
            System.out.println("it's NULLLLLL");
            return; // Exit the method if no blog is found
        }

        // Get the ID of the chosen blog
        int blogId = chosenBlog.getId();

        // Get the updated title, content, and date from the UI
        String title = oldTitle.getText();
        String content = oldContent.getText();
        LocalDate selectedDate = oldDate.getValue(); // Get selected date from DatePicker
        java.sql.Date date = (selectedDate != null) ? java.sql.Date.valueOf(selectedDate) : null;

        // Validate the input fields
        if (title.isEmpty()) {
            oldTitle.setStyle("-fx-border-color :red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(oldTitle).play();
            return;
        }
        if (content.isEmpty()) {
            oldContent.setStyle("-fx-border-color :red; -fx-border-width: 2px;");
            new animatefx.animation.Shake(oldContent).play();
            return;
        }

        // Create the updated blog object with the correct ID
        Blog update = new Blog(blogId, title, content, date);

        try {
            BlogService blogService = new BlogService();
            blogService.update(update); // Update the blog in the database
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le blog a été modifié avec succés.");
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return to the blog list
        navigateToBlogList(event);
    }

    @FXML
    void navigateToBlogList(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlogList.fxml"));
            Parent root = loader.load();
            Node sourceNode = (Node) event.getSource();
            Stage stage = (Stage) sourceNode.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void show_acceuil(ActionEvent actionEvent) {
    }

    public void show_transport(ActionEvent actionEvent) {
    }

    public void show_service(ActionEvent actionEvent) {
    }

    public void show_event(ActionEvent actionEvent) {
    }

    public void show_blog(ActionEvent actionEvent) {
    }

    public void show_parametres(ActionEvent actionEvent) {
    }

    public void show_se_deconnecter(ActionEvent actionEvent) {
    }

    public void quiiter(ActionEvent actionEvent) {
    }

    public void show_utilisateurs(ActionEvent actionEvent) {
    }


}
