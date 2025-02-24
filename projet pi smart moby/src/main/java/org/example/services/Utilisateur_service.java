package org.example.services;

import org.example.models.*;
import org.example.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.example.controllers.Register;

public class Utilisateur_service implements IUtilisateur_service {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Utilisateur utilisateur) {
        Register register = new Register();
        String req = "INSERT INTO utilisateur (nom, prenom, nom_utilisateur, email, mot_de_passe, role) VALUES ('"
                + utilisateur.getNom() + "', '" + utilisateur.getPrenom() + "', '" + utilisateur.getNom_utilisateur()
                + "', '" + utilisateur.getEmail() + "',  '" + utilisateur.getMot_de_passe() + "', '" + utilisateur.getRole() + "')";

        try {
            PreparedStatement pst = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int userId = rs.getInt(1);




                if (utilisateur.getRole() == Utilisateur.Role.CONDUCTEUR) {
                    Conducteur_service c = new Conducteur_service();
                    c.ajouter(new Conducteur(userId, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getNom_utilisateur(),
                            utilisateur.getEmail(), utilisateur.getMot_de_passe(), utilisateur.getRole(), userId, 1234));
                }


                if(utilisateur.getRole() == Utilisateur.Role.ORGANISATEUR){
                    Organisateur_service o = new Organisateur_service();
                    o.ajouter(new Organisateur(userId, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getNom_utilisateur(),
                            utilisateur.getEmail(), utilisateur.getMot_de_passe(), utilisateur.getRole(),400));
                }

                if(utilisateur.getRole() == Utilisateur.Role.CLIENT){
                    Client_service c = new Client_service();
                    c.ajouter(new Client(userId, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getNom_utilisateur(),
                            utilisateur.getEmail(), utilisateur.getMot_de_passe(), utilisateur.getRole(),userId));
                }

            }

            System.out.println("Utilisateur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }


    @Override
    public void modifier(Utilisateur utilisateur) {
        // Préparer la requête de mise à jour de l'utilisateur sans toucher au rôle
        String req = "UPDATE utilisateur SET nom='" + utilisateur.getNom() + "' ,prenom='" + utilisateur.getPrenom() + "' ,nom_utilisateur='" + utilisateur.getNom_utilisateur() + "' ,email='" + utilisateur.getEmail() + "'  ,mot_de_passe='" + utilisateur.getMot_de_passe() + "' WHERE id=" + utilisateur.getId();

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);  // Exécution de la requête pour l'utilisateur
            System.out.println("Utilisateur modifié");



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(Utilisateur utilisateur) {
        String req = "DELETE FROM utilisateur WHERE id=" + utilisateur.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficher() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        String req = "SELECT * FROM utilisateur";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("nom_utilisateur"), rs.getString("email"), rs.getString("mot_de_passe"), Utilisateur.Role.valueOf(rs.getString("role").toUpperCase())));


            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return utilisateurs;
    }


}



