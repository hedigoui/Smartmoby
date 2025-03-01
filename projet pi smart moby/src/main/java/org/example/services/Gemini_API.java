package org.example.services;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Gemini_API {
    private static final String API_KEY = "AIzaSyBX4k06BiLtI6fbPAlPifxlehxUfjUkhw4";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + API_KEY;
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public Gemini_API() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String generateContent(String question) throws IOException {
        String contexteSmartMoby = "Smart MOBY est une solution innovante visant à transformer la mobilité classique en une expérience intelligente et optimisée. " +
                "Développé en Java avec une base de données MySQL, ce projet propose une interface moderne et intuitive pour une meilleure accessibilité. " +
                "Fonctionnalités principales : " +
                "- Gestion des transports : Accédez à des services de transport détaillés et disponibles à tout moment. " +
                "- Événements : Découvrez et participez à divers événements liés à la mobilité. " +
                "- Offres et services : Profitez d’offres exclusives intégrant divers services et produits. " +
                "- Blog : Restez informé grâce à un espace dédié aux actualités et conseils sur la mobilité. " +
                "- Gestion des utilisateurs : Inscrivez-vous et créez un compte pour accéder à toutes les fonctionnalités. " +
                "Smart MOBY redéfinit l’expérience de déplacement en alliant innovation et praticité.";

        if (!question.toLowerCase().contains("smart moby")) {
            return "Je ne peux discuter que sur le projet Smart MOBY !";
        }

        String jsonBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + contexteSmartMoby + "\\n\\n" + question + "\" }] }] }";
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Erreur: " + response.code() + " " + response.message());
            }

            JsonNode jsonResponse = objectMapper.readTree(response.body().string());
            return jsonResponse.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        }
    }
}
