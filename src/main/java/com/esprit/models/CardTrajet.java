package com.esprit.models;

import com.esprit.JDBC.MyDataBase;
import com.esprit.services.Services;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;



import java.sql.Connection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static com.sun.javafx.event.EventUtil.fireEvent;

public class CardTrajet {
    @FXML
    public Button update;
    @FXML
    public Button delete;
    @FXML
    public Label pointA;
    @FXML
    private Label dateD;

    @FXML
    private Label dateA;

    @FXML
    private Label pointD;
    @FXML
    private Label distance;
    @FXML
    private Label prix;
    private Trajet trajet;
    private Connection cnx;

    public void ServiceStock() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public void initialize() {

    }

    public void setData(Trajet trajet) {
        Services t = new Services();
        ArrayList<Trajet> stocks = t.getAllTrajet();

        if (!stocks.isEmpty()) {
            this.trajet = trajet;
            pointD.setText(String.valueOf(trajet.getPointD()));
            pointA.setText(String.valueOf(trajet.getPointA()));
            dateD.setText(String.valueOf(trajet.getDateD()));
            dateA.setText(String.valueOf(trajet.getDateA()));
            distance.setText(String.valueOf(trajet.getDistance()));
            prix.setText(String.valueOf(trajet.getPrix()));
        }
    }
}

