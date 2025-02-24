package org.example.services;

import org.example.models.Avis;

import java.util.List;

public interface IAvisService<A> {
    void ajouter(Avis avis);

    void modifier(Avis avis);

    void supprimer(Avis avis);

    void ajouter(Object o);

    void modifier(Object o);

    void supprimer(Object o);

    List<Avis> rechercher();
}
