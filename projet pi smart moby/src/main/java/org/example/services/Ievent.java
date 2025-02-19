package org.example.services;

import org.example.models.event;

import java.util.List;

public interface Ievent {
    void ajouter(event event);

    void modifier(event event);

    void supprimer(event event);

    List<event> afficher();
}
