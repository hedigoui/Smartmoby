package org.example.services;

import org.example.models.Admin;
import org.example.models.Trajet;
import org.example.models.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public interface IAdmin_service {

    void ajouter(Admin admin );

    void modifier(Admin admin , Utilisateur utilisateur);

    void supprimer(Admin admin, Utilisateur utilisateur);

    public List<Admin> afficher();


    Admin getAdminById(int id);
}
