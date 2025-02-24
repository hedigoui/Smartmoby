package org.example.services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class AI_API {

    private static final String BASE_URL = "https://api.aimlapi.com/v1";
    private static final String API_KEY = "9bfb3c34f50e4afe8d0af8d20f134df2"; // Remplacez par votre clé API
    private static final String MODEL = "mistralai/Mistral-7B-Instruct-v0.2";

    public static String chatWithAI(String systemPrompt, String userPrompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Créer le corps de la requête
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("model", MODEL);
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", systemPrompt));
        messages.put(new JSONObject().put("role", "user").put("content", userPrompt));
        jsonBody.put("messages", messages);
        jsonBody.put("temperature", 0.7);
        jsonBody.put("max_tokens", 256);

        RequestBody body = RequestBody.create(
                jsonBody.toString(), MediaType.get("application/json; charset=utf-8"));

        // Construire la requête
        Request request = new Request.Builder()
                .url(BASE_URL + "/chat/completions")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        // Exécuter la requête
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            } else {
                throw new IOException("Server returned HTTP response code: " + response.code());
            }
        }

    }
}
