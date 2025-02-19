package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

}
