package org.example.services;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Gemini_API {
    private static final String API_KEY = "AIzaSyBX4k06BiLtI6fbPAlPifxlehxUfjUkhw4";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    public Gemini_API() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String generateContent(String question) throws IOException {
        String jsonBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + question + "\" }] }] }";
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
