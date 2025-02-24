package org.example.services;

import org.example.models.Client;
import org.example.models.Utilisateur;

import java.util.List;

public interface IClient_service {
    public void ajouter (Client client);
    public void modifier (Client client, Utilisateur utilisateur);
    public void supprimer (Client client, Utilisateur utilisateur);
    public List<Client> afficher();
}
