package org.example.services;

import org.example.models.Conducteur;
import org.example.models.Utilisateur;

import java.util.List;

public interface IConducteur_service {
    void ajouter(Conducteur conducteur);

    void modifier(Conducteur conducteur , Utilisateur utilisateur);

    void supprimer(Conducteur conducteur , Utilisateur utilisateur);
    public List<Conducteur> afficher();
}
