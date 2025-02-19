package org.example.models;

public class SessionManager {
    private static Utilisateur utilisateurConnecte; // Utilisateur connecté

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public static void setUtilisateurConnecte(Utilisateur utilisateur) {
        utilisateurConnecte = utilisateur;
    }

    // Récupérer l'ID de l'utilisateur connecté
    public static int getUtilisateurConnecteId() {
        if (utilisateurConnecte != null) {
            return utilisateurConnecte.getId();
        }
        return -1; // ou autre valeur par défaut si aucun utilisateur n'est connecté
    }
}

