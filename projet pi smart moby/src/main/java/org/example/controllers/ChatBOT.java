package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.services.AI_API;
import org.example.services.Gemini_API;

import java.io.IOException;

public class ChatBOT {

    @FXML
    private TextArea answer;

    @FXML
    private TextField question;

    @FXML
    void get_answer(ActionEvent event) {
        Gemini_API geminiAPI = new Gemini_API();
        String Question = question.getText();

        try {
            String Answer = geminiAPI.generateContent(Question);
            System.out.println(Answer);
            answer.setText(Answer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            // Charger le fichier FXML de la nouvelle scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AcceuilClient.fxml"));
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

}
