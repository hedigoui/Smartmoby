package org.example.services;

import org.example.models.Trajet;
import org.example.models.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public interface IUtilisateur_service {
    void ajouter(Utilisateur utilisateur);

    void modifier(Utilisateur utilisateur);

    void supprimer(Utilisateur utilisateur);

    List<Utilisateur> afficher();


    Utilisateur getUtilisateurById(int id);
}
