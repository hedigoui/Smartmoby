package org.example.services;

import org.example.utils.DataSource;
import org.example.models.Trajet;

import java.sql.*;
import java.util.ArrayList;
import org.example.models.Vehicule;

public class Services {
    private Connection cnx ;
    public Services() {cnx = DataSource.getInstance().getConnection();
    }

    public void add(Vehicule vehicule) {
        String qry = "INSERT INTO `vehicule`(`type`, `capacite`, `statut`, `dispo`) VALUES (?, ?, ?, ?)";

        try {
            // Création de la requête préparée avec récupération de l'ID généré
            PreparedStatement stm = cnx.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, vehicule.getType());
            stm.setInt(2, vehicule.getCapacite());
            stm.setString(3, vehicule.getStatut());
            stm.setBoolean(4, vehicule.isDispo());

            // Exécution de la requête
            int affectedRows = stm.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    vehicule.setId(generatedKeys.getInt(1)); // Récupération de l'ID auto-incrémenté
                    System.out.println("Véhicule ajouté avec ID : " + vehicule.getId());
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du véhicule : " + e.getMessage());
            e.printStackTrace();
        }
    }



    public ArrayList<Vehicule> getAllVehicule() {
        ArrayList<Vehicule> vehicules = new ArrayList<>();
        String qry = "SELECT * FROM `Vehicule`";

        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Vehicule s = new Vehicule();
                s.setId(rs.getInt("id"));  // Correction : utilisation du nom de colonne
                s.setType(rs.getString("type"));
                s.setCapacite(rs.getInt("capacite")); // Correction de l’index
                s.setStatut(rs.getString("statut"));
                s.setDispo(rs.getBoolean("dispo")); // Correction de l’index

                vehicules.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des véhicules : " + e.getMessage());
            e.printStackTrace();
        }

        return vehicules;
    }

    public boolean update(Vehicule vehicule) {
        String qry = "UPDATE vehicule SET type=?, capacite=?, statut=?, dispo=? WHERE id=?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, vehicule.getType());
            stm.setInt(2, vehicule.getCapacite());
            stm.setString(3, vehicule.getStatut());
            stm.setBoolean(4, vehicule.isDispo());
            stm.setInt(5, vehicule.getId());

            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            e.printStackTrace();
            return false;
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

    public Trajet addtrajet(Trajet trajet) {
        String query = "INSERT INTO `Trajet`(`pointD`, `pointA`, `dateD`, `dateA`, `distance`, `prix`, `id_veh`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            stm.setString(1, trajet.getPointD());
            stm.setString(2, trajet.getPointA());
            stm.setTimestamp(3, new java.sql.Timestamp(trajet.getDateD().getTime()));
            stm.setTimestamp(4, new java.sql.Timestamp(trajet.getDateA().getTime()));
            stm.setDouble(5, trajet.getDistance());
            stm.setDouble(6, trajet.getPrix());
            stm.setInt(7, trajet.getId_veh());

            stm.executeUpdate();

            // Récupérer l'ID généré
            ResultSet rs = stm.getGeneratedKeys();
            if (rs.next()) {
                trajet.setId(rs.getInt(1)); // Met à jour l'ID dans l'objet Trajet
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du trajet", e);
        }
        return trajet; // Retourner le trajet avec l'ID mis à jour
    }


    public ArrayList<Trajet> getAllTrajet() {
        ArrayList<Trajet> trajets = new ArrayList<>();
        String qry = "SELECT t.*, v.type as vehicule FROM Trajet t " +
                "LEFT JOIN vehicule v ON t.id_veh = v.id";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                // Création d'un objet Trajet et remplissage avec les données de la base de données
                Trajet t = new Trajet();

                t.setId(rs.getInt(1));  // ID
                t.setPointD(rs.getString("pointD"));  // Ville de départ
                t.setPointA(rs.getString("pointA"));  // Ville d'arrivée
                t.setDateD(rs.getTimestamp("dateD"));  // Date de départ
                t.setDateA(rs.getTimestamp("dateA"));  // Date d'arrivée
                t.setDistance(rs.getDouble("distance"));  // Distance
                t.setPrix(rs.getDouble("prix"));  // Prix
                t.setId_veh(rs.getInt("id_veh"));
                t.setVehicule(rs.getString("vehicule")); // Set the vehicle type

                // Ajouter le trajet à la liste
                trajets.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des trajets", e);
        }
        return trajets;
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
            return false;
        }
    }

    public boolean updateTrajet(Trajet trajet) {
        String qry = "UPDATE Trajet SET pointD = ?, pointA = ?, dateD = ?, dateA = ?, " +
                "distance = ?, prix = ?, id_veh = ? WHERE id = ?";

        try (PreparedStatement pst = cnx.prepareStatement(qry)) {
            pst.setString(1, trajet.getPointD());
            pst.setString(2, trajet.getPointA());
            pst.setTimestamp(3, trajet.getDateD());
            pst.setTimestamp(4, trajet.getDateA());
            pst.setDouble(5, trajet.getDistance());
            pst.setDouble(6, trajet.getPrix());
            pst.setInt(7, trajet.getId_veh());
            pst.setInt(8, trajet.getId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean UpdateVehicule(Vehicule vehicule) {
        String qry = "UPDATE Vehicule SET type = ?, capacite = ?, statut = ?, dispo = ? WHERE id = ?";

        try (PreparedStatement pst = cnx.prepareStatement(qry)) {
            // Assigner les valeurs des propriétés du véhicule aux paramètres de la requête
            pst.setString(1, vehicule.getType());
            pst.setInt(2, vehicule.getCapacite());
            pst.setString(3, vehicule.getStatut());
            pst.setString(4, vehicule.getDisponibilite());
            pst.setInt(5, vehicule.getId());

            // Exécuter la mise à jour et vérifier si des lignes ont été affectées
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0; // Retourner true si la mise à jour a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retourner false si une exception se produit
        }
    }

    public Vehicule getVehiculeById(int id) {
        String query = "SELECT * FROM vehicule WHERE id = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Vehicule v = new Vehicule();
                v.setId(rs.getInt("id"));
                v.setType(rs.getString("type"));
                v.setCapacite(rs.getInt("capacite"));
                v.setStatut(rs.getString("statut"));
                v.setDispo(rs.getBoolean("dispo"));
                return v;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}

