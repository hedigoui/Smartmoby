package org.example.controllers;

import com.mailjet.client.errors.MailjetException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.services.EmailAPI;
import org.example.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MotDePasseOublie {
    private Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private TextField nom_utilisateur;

    @FXML
    void send_email(ActionEvent event) {
        String username = nom_utilisateur.getText();
        String email = null;
        String password = null;

        String sql = "SELECT email, mot_de_passe FROM utilisateur WHERE nom_utilisateur = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                email = resultSet.getString("email");
                password = resultSet.getString("mot_de_passe");
                System.out.println("Email: " + email);
                System.out.println("Mot de passe: " + password);

                try {
                    EmailAPI emailAPI = new EmailAPI();
                    emailAPI.sendEmail(
                            "haythemabdellaoui007@gmail.com",
                            "Me",
                            email,
                            "You",
                            "Récupération Du Mot De Passe",
                            "Salutations de notre équipe de support technique ! Voici votre mot de passe : " + password,
                            "<h3>Voici votre mot de passe : " + password + "</h3>"
                    );
                } catch (MailjetException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce nom d'utilisateur.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
