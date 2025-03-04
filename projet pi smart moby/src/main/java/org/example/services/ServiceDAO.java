package org.example.services;

import org.example.models.Service;
import org.example.utils.DataSource;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ServiceDAO {
    public static Connection conn = DataSource.getInstance().getConnection();


    public static void ajouterService(Service service) throws SQLException {
        String query = "INSERT INTO service (nom, description, tarif) VALUES (?, ?, ?)";
        try (
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, service.getNom());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getTarif());
            stmt.executeUpdate();
        }
    }

    public static List<Service> getAllServices() throws SQLException {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM service";
        try (
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                services.add(new Service(rs.getInt("id_service"), rs.getString("nom"),
                        rs.getString("description"), rs.getDouble("tarif")));
            }
        }
        return services;
    }

    public static void supprimerService(int id) throws SQLException {
        String query = "DELETE FROM service WHERE id_service = ?";
        try (
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static void modifierService(Service service) throws SQLException {
        String query = "UPDATE service SET nom = ?, description = ?, tarif = ? WHERE id_service = ?";
        try (
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, service.getNom());
            stmt.setString(2, service.getDescription());
            stmt.setDouble(3, service.getTarif());
            stmt.setInt(4, service.getId());
            stmt.executeUpdate();
        }
    }
}