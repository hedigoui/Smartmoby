package org.example.services;

import org.example.models.WeatherInfo;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WeatherService {
    private static final String API_KEY = "503915f728d3ff0b58ad4083d6a4cec3";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final Map<String, CachedWeather> cache = new HashMap<>();

    private static class CachedWeather {
        WeatherInfo info;
        long timestamp;

        CachedWeather(WeatherInfo info) {
            this.info = info;
            this.timestamp = System.currentTimeMillis();
        }

        boolean isValid() {
            return System.currentTimeMillis() - timestamp < 600000; // 10 minutes cache
        }
    }

    public WeatherInfo getWeatherForCity(String city) {
        try {
            // Check cache first
            CachedWeather cached = cache.get(city);
            if (cached != null && cached.isValid()) {
                return cached.info;
            }

            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String urlString = String.format("%s?q=%s&appid=%s&units=metric",
                    BASE_URL, encodedCity, API_KEY);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                StringBuilder response = new StringBuilder();
                try (java.io.BufferedReader reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                WeatherInfo info = parseWeatherJson(response.toString());
                cache.put(city, new CachedWeather(info));
                return info;
            } else {
                System.err.println("Error: " + conn.getResponseCode() + " " + conn.getResponseMessage());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WeatherInfo parseWeatherJson(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            WeatherInfo info = new WeatherInfo();

            info.setCity(json.getString("name"));
            info.setTemperature(json.getJSONObject("main").getDouble("temp"));
            info.setHumidity(json.getJSONObject("main").getInt("humidity"));
            info.setDescription(json.getJSONArray("weather")
                    .getJSONObject(0).getString("description"));
            info.setIcon(json.getJSONArray("weather")
                    .getJSONObject(0).getString("icon"));
            info.setWindSpeed(json.getJSONObject("wind").getDouble("speed"));


            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}