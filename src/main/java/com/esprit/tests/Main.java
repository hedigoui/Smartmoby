package com.esprit.tests;

import com.esprit.JDBC.*;
import com.esprit.models.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.esprit.services.*;



public class Main {
    public static void main(String[] args) {

//    Vehicule s1 = new Vehicule( "aa",2, "hors service", false);
//        Vehicule vehiculeToUpdate = new Vehicule( 11,"car", 4, "Available", false);
        Services sap = new Services();
//       sap.add(s1);
//       sap.update(vehiculeToUpdate);
//         Print the result of sap.getAllVehicule();
//        System.out.println(sap.getAllVehicule());


//        Vehicule stockToDelete = new Vehicule();
//        stockToDelete.setId(10);
//
//
//        boolean isDeleted = sap.delete(stockToDelete);
//
//        if(isDeleted){
//            System.out.println("Vehicule deleted successfully");
//        }
//        else{
//            System.out.println("Failed to deleted Vehicule" );
//        }
//
//        System.out.println("Vehicule after deleting" +sap.getAll());



//                 TRAJET
        Services tap = new Services();
//        Trajet t1 = new Trajet(2, "ariana", "zahra",
//                Timestamp.valueOf(LocalDateTime.of(2026, 8, 10, 17, 30)),  // Date et heure de départ
//                Timestamp.valueOf(LocalDateTime.of(2026, 8, 10, 19, 45)), // Date et heure d'arrivée
//                6, 5.5);
//
//        tap.addtrajet(t1);
//        Trajet targetToUpdate = new Trajet(2, "aouina", "centre ville",
//                Timestamp.valueOf(LocalDateTime.of(2025, 5, 20, 14, 30)),
//                Timestamp.valueOf(LocalDateTime.of(2025, 5, 20, 16, 45)),
//                6, 5.5);
//       tap.update(targetToUpdate);
//        ArrayList<Trajet> trajets = tap.getAllTrajet();
//        for (Trajet trajet : trajets) {
//            System.out.println(trajet);  // Assurez-vous que `Trajet` a une méthode `toString()` correcte
//        }
//        System.out.println(tap.getAllTrajet());


//        Trajet stockToDelete = new Trajet();
//        stockToDelete.setId(7);
//
//
//        boolean isDeleted = tap.delete(stockToDelete);
//
//        if(isDeleted){
//            System.out.println("Trajet deleted successfully");
//        }
//        else{
//            System.out.println("Failed to deleted Trajet" );
//        }
//
//        System.out.println("Trajet after deleting" +tap.getAll());
//
    }
}
