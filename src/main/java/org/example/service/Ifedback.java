package org.example.service;

import org.example.modeles.event;
import org.example.modeles.fedback;

import java.util.List;

public interface Ifedback {
    void ajouter(fedback fedback);

    void modifier(fedback fedback);

    void supprimer(fedback fedback);

    List<fedback> afficher();


}
