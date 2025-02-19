package org.example.services;

import org.example.models.fedback;
import org.example.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class fedback_serv implements Ifedback {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(fedback fedback) {
        String req = "INSERT INTO fedback (commentaire, note) VALUES ('" + fedback.getCommentaire() + "', '" + fedback.getNote() + "' )";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("fedback ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(fedback fedback) {
        String req = "UPDATE fedback SET commentaire='" + fedback.getCommentaire() + "' ,note='" + fedback.getNote() + "'  WHERE id=" + fedback.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("fedback modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(fedback fedback) {
        String req = "DELETE FROM fedback WHERE id=" + fedback.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("fedback supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<fedback> afficher() {
        List<fedback> fedbacks = new ArrayList<>();

        String req = "SELECT * FROM fedback";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                fedbacks.add(new fedback(rs.getInt("id"), rs.getString("commentaire"), rs.getInt("note") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return fedbacks;
    }
}

