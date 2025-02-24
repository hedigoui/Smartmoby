package org.example.services;

import org.example.models.Avis;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AvisService implements IAvisService<Avis> { // Assuming IService is your generic service interface
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Avis avis) {
        String req = "INSERT INTO avis (name, comment, date) VALUES ('" + avis.getName() + "', '" + avis.getComment() + "', '" + avis.getDate() + "')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Avis ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Avis avis) {
        String req = "UPDATE avis SET name='" + avis.getName() + "', comment='" + avis.getComment() + "' WHERE id=" + avis.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Avis modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Avis avis) {
        String req = "DELETE FROM avis WHERE id=" + avis.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Avis supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void ajouter(Object o) {
        // Implement if needed
    }

    @Override
    public void modifier(Object o) {
        // Implement if needed
    }

    @Override
    public void supprimer(Object o) {
        // Implement if needed
    }

    @Override
    public List<Avis> rechercher() {
        List<Avis> avisList = new ArrayList<>();

        String req = "SELECT * FROM avis";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                avisList.add(new Avis(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("comment"),
                        rs.getDate("date")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return avisList;
    }
}