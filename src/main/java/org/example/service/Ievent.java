package org.example.service;

import org.example.modeles.event;

import java.util.List;

public interface Ievent {
    void ajouter(event event);

    void modifier(event event);

    void supprimer(event event);

    List<event> afficher();
}
