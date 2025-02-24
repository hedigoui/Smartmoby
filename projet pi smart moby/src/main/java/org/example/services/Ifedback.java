package org.example.services;

import org.example.models.event;
import org.example.models.fedback;

import java.util.List;

public interface Ifedback {
    void ajouter(fedback fedback);

    void modifier(fedback fedback);

    void supprimer(fedback fedback);

    List<fedback> afficher();


}
