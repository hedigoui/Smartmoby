package org.example.models;

public class Session {
    // Variable statique pour stocker l'ID de l'utilisateur connecté
    private static int userId;

    // Méthode pour définir l'ID de l'utilisateur
    public static void setUserId(int id) {
        userId = id;
    }

    // Méthode pour obtenir l'ID de l'utilisateur
    public static int getUserId() {
        return userId;
    }

    // Méthode pour réinitialiser l'ID (si besoin de déconnecter)
    public static void resetUserId() {
        userId = 0; // ou une autre valeur qui vous indique que l'utilisateur n'est pas connecté
    }
}

