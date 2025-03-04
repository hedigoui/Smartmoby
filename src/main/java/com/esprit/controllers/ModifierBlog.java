package com.esprit.controllers;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifierBlog {










    private String nameToSearchWith ="";
    void getNameToSearchWith(Blog blog){
        nameToSearchWith = blog.getTitle();
        System.out.println("the word is :" + nameToSearchWith);
    }


    void setData(Blog blog){

        /*
        TFvoy_description_old.setText(blog.getDescription());
        TFvoy_destination_old.setValue(blog.getDestination());
        TFvoy_image_old.setText(voyage.getImage1());
        TFvoy_nom_old.setText(voyage.getNom());
        TFvoy_prix_old.setText(String.valueOf(voyage.getPrix()));
        VoyAjout_date_debut_old.setValue(voyage.getDate_debut().toLocalDate());
        VoyAjout_date_fin_old.setValue(voyage.getDate_fin().toLocalDate());
        if(voyage.getType().equals("Touristique")) {
            VoyType1_old.setSelected(true);
        }else if(voyage.getType().equals("Humanitaire") ){
            VoyType2_old.setSelected(true);
        }
        System.out.println("WE ARE PUTTING STUFF HERE");


         */
    }
}
