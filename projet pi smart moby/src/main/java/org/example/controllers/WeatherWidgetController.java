package org.example.controllers;

import org.example.models.WeatherInfo;
import org.example.services.WeatherService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class WeatherWidgetController implements Initializable {
    // Existing FXML injections
    @FXML private Label cityLabel;
    @FXML private Label timestampLabel;
    @FXML private Label temperatureLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label humidityLabel;
    @FXML private Label windLabel;
    @FXML private Label dateTimeLabel;
    @FXML private Label userLabel;
    @FXML private Label pressureLabel;
    @FXML private Label feelsLikeLabel;
    @FXML private ImageView weatherIcon;

    // New FXML injections for search functionality
    @FXML private TextField citySearchField;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML
    private Button back;
    private Stage stage;
    private Scene scene;

    private WeatherService weatherService;
    private Timeline updateTimeline;
    private String currentCity = "Tunis"; // Default city

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Existing initialization code...
        weatherService = new WeatherService();
        setupDateTime();
        setupUserInfo();
        updateWeather(currentCity);
        setupAutomaticUpdates();

        // Add listeners for search field
        citySearchField.setOnAction(e -> searchCity()); // Enable Enter key
    }

    @FXML
    private void searchCity() {
        String searchTerm = citySearchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            currentCity = searchTerm;
            updateWeather(searchTerm);
            citySearchField.clear(); // Clear the search field after search
        }
    }

    @FXML
    private void refreshWeather() {
        updateWeather(currentCity);
        // Update the timestamp
        timestampLabel.setText("Last Updated: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private void updateWeather(String city) {
        try {
            // Disable buttons during update
            if (searchButton != null) searchButton.setDisable(true);
            if (refreshButton != null) refreshButton.setDisable(true);

            WeatherInfo info = weatherService.getWeatherForCity(city);
            if (info != null) {
                updateUI(info);
                currentCity = city; // Update current city only if successful
            } else {
                showError("Unable to fetch weather data for " + city);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error: " + e.getMessage());
        } finally {
            // Re-enable buttons
            if (searchButton != null) searchButton.setDisable(false);
            if (refreshButton != null) refreshButton.setDisable(false);
        }
    }

    private void updateUI(WeatherInfo info) {
        cityLabel.setText(info.getCity());
        temperatureLabel.setText(String.format("%.1f°C", info.getTemperature()));
        descriptionLabel.setText(info.getDescription());
        humidityLabel.setText(String.format("%d%%", info.getHumidity()));
        windLabel.setText(String.format("%.1f m/s", info.getWindSpeed()));
        timestampLabel.setText("Last Updated: " + info.getTimestamp());

        try {
            String iconUrl = String.format("http://openweathermap.org/img/w/%s.png", info.getIcon());
            weatherIcon.setImage(new Image(iconUrl));
        } catch (Exception e) {
            e.printStackTrace();
            weatherIcon.setImage(null);
        }
    }

    private void showError(String errorMessage) {
        cityLabel.setText("Error");
        temperatureLabel.setText("--°C");
        descriptionLabel.setText(errorMessage);
        humidityLabel.setText("--");
        windLabel.setText("--");
        weatherIcon.setImage(null);
        timestampLabel.setText("Last Update Failed");

    }

    // Existing helper methods...
    private void setupDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        updateDateTime(formatter);

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1),
                e -> updateDateTime(formatter)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void updateDateTime(DateTimeFormatter formatter) {
        dateTimeLabel.setText("Current Date and Time (UTC):\n" +
                LocalDateTime.now().format(formatter));
    }

    private void setupUserInfo() {
        userLabel.setText("Current User's Login: " + System.getProperty("user.name"));
    }

    private void setupAutomaticUpdates() {
        updateTimeline = new Timeline(new KeyFrame(Duration.minutes(5),
                e -> updateWeather(currentCity)));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
        updateTimeline.play();
    }

    public void cleanup() {
        if (updateTimeline != null) {
            updateTimeline.stop();
        }
    }

    @FXML
    void back(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/AcceuilClient.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}