package com.esprit.tests;

import com.esprit.models.WeatherInfo;
import com.esprit.services.WeatherService;

public class WeatherTest {
    public static void main(String[] args) {
        // Test the weather service
        WeatherService service = new WeatherService();

        // Test for Tunis
        System.out.println("Testing weather service for Tunis:");
        WeatherInfo tunisInfo = service.getWeatherForCity("Gabes");
        System.out.println(tunisInfo != null ? tunisInfo : "Failed to get weather for Tunis");

        // Test for another city
        System.out.println("\nTesting weather service for Ariana:");
        WeatherInfo parisInfo = service.getWeatherForCity("Ariana");
        System.out.println(parisInfo != null ? parisInfo : "Failed to get weather for Paris");
    }
}