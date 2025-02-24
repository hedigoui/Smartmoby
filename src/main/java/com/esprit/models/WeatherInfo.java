package com.esprit.models;

public class WeatherInfo {
    private String city;
    private double temperature;
    private int humidity;
    private String description;
    private String icon;
    private double windSpeed;
    private String timestamp;


    // Constructor
    public WeatherInfo() {
        this.timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Getters and Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("Weather in %s at %s:%n" +
                        "Temperature: %.1f°C%n" +
                        "Description: %s%n" +
                        "Humidity: %d%%%n" +
                        "Wind Speed: %.1f m/s",
                city, timestamp, temperature,
                description, humidity, windSpeed);
    }
}