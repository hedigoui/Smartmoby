package com.esprit.components;

import com.esprit.models.WeatherInfo;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WeatherWidget extends VBox {
    private Label cityLabel;
    private Label temperatureLabel;
    private Label descriptionLabel;
    private Label humidityLabel;
    private Label windLabel;
    private ImageView weatherIcon;

    public WeatherWidget() {
        setupUI();
    }

    private void setupUI() {
        setSpacing(5);
        setPadding(new Insets(10));
        setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 10;");

        cityLabel = new Label();
        cityLabel.setFont(Font.font("System", 16));

        temperatureLabel = new Label();
        temperatureLabel.setFont(Font.font("System", 24));

        weatherIcon = new ImageView();
        weatherIcon.setFitHeight(50);
        weatherIcon.setFitWidth(50);

        descriptionLabel = new Label();
        humidityLabel = new Label();
        windLabel = new Label();

        getChildren().addAll(cityLabel, weatherIcon, temperatureLabel,
                descriptionLabel, humidityLabel, windLabel);
    }

    public void updateWeather(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            cityLabel.setText(weatherInfo.getCity());
            temperatureLabel.setText(String.format("%.1f°C", weatherInfo.getTemperature()));
            descriptionLabel.setText(weatherInfo.getDescription());
            humidityLabel.setText(String.format("Humidity: %d%%", weatherInfo.getHumidity()));
            windLabel.setText(String.format("Wind: %.1f m/s", weatherInfo.getWindSpeed()));

            // Load weather icon
            String iconUrl = String.format("http://openweathermap.org/img/w/%s.png",
                    weatherInfo.getIcon());
            weatherIcon.setImage(new Image(iconUrl));
        }
    }
}