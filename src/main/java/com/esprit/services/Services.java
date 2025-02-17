package com.esprit.services;

import com.esprit.models.*;
import com.esprit.JDBC.MyDataBase;
import com.esprit.models.Trajet;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.*;
import java.util.ArrayList;

public class Services {
     private Connection cnx ;
     public Services() {cnx = MyDataBase.getInstance().getCnx();
     }

    public void add(Vehicule vehicule) {
        String qry = "INSERT INTO `vehicule`(`type`, `capacite`, `statut`, `dispo`) VALUES (?, ?, ?, ?)";

        // Message de débogage avant l'exécution de la requête
        System.out.println("Début de l'ajout du véhicule : " + vehicule.getType() + ", "
                + vehicule.getCapacite() + ", " + vehicule.getStatut() + ", "
                + vehicule.isDispo());

        try {
            // Création de la requête préparée
            PreparedStatement stm = cnx.prepareStatement(qry);
            System.out.println("Requête préparée : " + qry);

            // Débogage des paramètres avant de les lier
            stm.setString(1, vehicule.getType());
            System.out.println("Type du véhicule : " + vehicule.getType());

            stm.setInt(2, vehicule.getCapacite());
            System.out.println("Capacité du véhicule : " + vehicule.getCapacite());

            stm.setString(3, vehicule.getStatut());
            System.out.println("Statut du véhicule : " + vehicule.getStatut());

            stm.setInt(4, vehicule.isDispo() ? 1 : 0);
            System.out.println("Disponibilité du véhicule : " + (vehicule.isDispo() ? "Disponible" : "Non disponible"));

            // Exécution de la requête
            stm.executeUpdate();
            System.out.println("Véhicule ajouté avec succès.");

        } catch (SQLException e) {
            // Message d'erreur avec détails sur l'exception
            System.out.println("Erreur lors de l'ajout du véhicule : " + e.getMessage());
            e.printStackTrace();  // Affichage de la trace complète de l'exception pour débogage
        }
    }


    public ArrayList<Vehicule> getAll() {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        String qry="SELECT * FROM `Vehicule`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);
            while (rs.next()) { Vehicule s = new Vehicule();
                s.setId(rs.getInt(1));
                s.setType(rs.getString("type"));
                s.setCapacite(rs.getInt(3));
                s.setStatut(rs.getString("statut"));
                s.setDispo(rs.getBoolean(5));

                vehicules.add(s);
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicules;
    }
    public void update(Vehicule vehicule) {
        String qry = "UPDATE `vehicule` SET `id`=?,`type`=?,`capacite`=?,`statut`=?,`dispo`=? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, vehicule.getId());
            stm.setString(2, vehicule.getType());
            stm.setInt(3, vehicule.getCapacite());
            stm.setString(4, vehicule.getStatut());
            stm.setInt(5, vehicule.isDispo() ? 1 : 0);
            stm.setInt(6, vehicule.getId());
            int resultat = stm.executeUpdate();
            if (resultat > 0) {
                System.out.println("Post modif avec succs !");
            } else {
                System.out.println("Erreur lors de la modification du post.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean delete(Vehicule vehicule) {
        String qry ="DELETE FROM `vehicule` WHERE `id`=?";
        try{
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,vehicule.getId());

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0 ;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void addtrajet(Trajet trajet) {
        String query = "INSERT INTO `Trajet`(`id`, `pointD`, `pointA`, `dateD`, `dateA`, `distance`, `prix`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(query);

            stm.setInt(1, trajet.getId());
            stm.setString(2, trajet.getPointD());
            stm.setString(3, trajet.getPointA());
            stm.setTimestamp(4, new java.sql.Timestamp(trajet.getDateD().getTime()));  // Conversion pour DATETIME
            stm.setTimestamp(5, new java.sql.Timestamp(trajet.getDateA().getTime()));  // Conversion pour DATETIME
            stm.setDouble(6, trajet.getDistance());
            stm.setDouble(7, trajet.getPrix());

            stm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public ArrayList<Trajet> getAll() {
//        ArrayList<Trajet> trajets = new ArrayList<>();
//        String qry = "SELECT * FROM `Trajet`"; // Requête SQL pour récupérer tous les trajets
//
//        try {
//            Statement stm = cnx.createStatement();
//            ResultSet rs = stm.executeQuery(qry);
//
//            while (rs.next()) {
//                // Création d'un objet Trajet et remplissage avec les données de la base de données
//                Trajet t = new Trajet();
//
//                t.setId(rs.getInt(1));  // ID
//                t.setPointD(rs.getString("pointD"));  // Ville de départ
//                t.setPointA(rs.getString("pointA"));  // Ville d'arrivée
//                t.setDateD(rs.getTimestamp("dateD"));  // Date de départ
//                t.setDateA(rs.getTimestamp("dateA"));  // Date d'arrivée
//                t.setDistance(rs.getDouble("distance"));  // Distance
//                t.setPrix(rs.getDouble("prix"));  // Prix
//
//                // Ajouter le trajet à la liste
//                trajets.add(t);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Erreur lors de la récupération des trajets", e);
//        }
//        return trajets;
//    }

    public void update(Trajet trajet) {
        String qry = "UPDATE `Trajet` SET `pointD`=?, `pointA`=?, `dateD`=?, `dateA`=?, `distance`=?, `prix`=? WHERE `id`=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, trajet.getPointD());
            stm.setString(2, trajet.getPointA());
            stm.setTimestamp(3, trajet.getDateD());  // Utilisation de Timestamp pour DATETIME
            stm.setTimestamp(4, trajet.getDateA());
            stm.setDouble(5, trajet.getDistance());
            stm.setDouble(6, trajet.getPrix());
            stm.setInt(7, trajet.getId());

            int resultat = stm.executeUpdate();
            if (resultat > 0) {
                System.out.println("Trajet modifié avec succès !");
            } else {
                System.out.println("Erreur lors de la modification du trajet.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public boolean delete(Trajet trajet) {
        String qry = "DELETE FROM `Trajet` WHERE `id`=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1, trajet.getId());  // Set the ID of the trajet to be deleted

            int affectedRows = stm.executeUpdate();
            return affectedRows > 0;  // Return true if the trajet was successfully deleted
        } catch (SQLException e) {
            System.out.println(e.getMessage());  // Print the error if any
            return false;  // Return false if there's an issue
        }
    }

}
