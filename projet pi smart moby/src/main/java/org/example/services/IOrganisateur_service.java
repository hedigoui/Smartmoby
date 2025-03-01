package org.example.services;

import org.example.models.Organisateur;
import org.example.models.Utilisateur;

import java.util.List;

public interface IOrganisateur_service {
    public void ajouter (Organisateur organisateur);
    public void modifier (Organisateur organisateur , Utilisateur utilisateur);
    public void supprimer (Organisateur organisateur, Utilisateur utilisateur);
    public List<Organisateur> afficher();

    Organisateur getOrganisateurById(int id);
}
