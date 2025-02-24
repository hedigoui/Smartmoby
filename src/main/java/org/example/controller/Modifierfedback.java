package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import org.example.modeles.fedback;
import org.example.service.fedback_serv;

public class Modifierfedback {

    @FXML
    private TextArea feedbackTextArea;  // Champ pour modifier le commentaire du feedback

    @FXML
    private Slider noteSlider;  // Slider pour modifier la note

    @FXML
    private Label noteLabel;  // Label pour afficher la valeur de la note

    @FXML
    private Button modifyButton;  // Bouton pour enregistrer les modifications

    private fedback selectedFeedback;  // Le feedback sélectionné à modifier
    private final fedback_serv fedbackService = new fedback_serv();  // Service pour manipuler les feedbacks

    // Méthode d'initialisation
    public void initData(fedback feedback) {
        this.selectedFeedback = feedback;

        // Initialiser les champs avec les données du feedback
        feedbackTextArea.setText(feedback.getCommentaire());
        noteSlider.setValue(feedback.getNote());
        noteLabel.setText(String.valueOf((int) noteSlider.getValue()));

        // Vérifier si l'id_event est bien récupéré
        System.out.println("aaaaa");
        System.out.println("Feedback ID : " + feedback.getId());
        System.out.println("ID de l'événement associé : " + feedback.getIdEvent());

        // Mettre à jour la valeur de la note lorsque l'utilisateur déplace le slider
        noteSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            noteLabel.setText(String.valueOf(newVal.intValue()));
        });
    }

    // Méthode pour enregistrer les modifications du feedback
    @FXML
    private void modifierFeedback() {
        System.out.println("aaaaa");
        System.out.println("Feedback ID : " + selectedFeedback.getIdEvent());
        // Récupérer les nouvelles valeurs du feedback
        String newCommentaire = feedbackTextArea.getText().trim();
        int newNote = (int) noteSlider.getValue();


        if (newCommentaire.isEmpty()) {
            showAlert("Erreur", "Le commentaire ne peut pas être vide.");
            return;
        }

        // Vérification si la note est valide (par exemple, entre 0 et 10)
        if (newNote < 0 || newNote > 10) {
            showAlert("Erreur", "La note doit être comprise entre 0 et 10.");
            return;
        }

        try {
            // Mettre à jour les valeurs du feedback
            selectedFeedback.setCommentaire(newCommentaire);
            selectedFeedback.setNote(newNote);

            // Enregistrer les modifications dans la base de données
            fedbackService.modifier(selectedFeedback);

            showAlert("Succès", "Le feedback a été modifié avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la modification du feedback.");
            e.printStackTrace();
        }
    }

    // Méthode pour afficher des alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void supprimer(ActionEvent event) {
        System.out.println("🔴 Suppression du feedback en cours...");

        // Vérifier si un feedback est sélectionné
        if (selectedFeedback == null) {
            showAlert("Erreur", "Veuillez sélectionner un feedback à supprimer.");
            return;
        }

        System.out.println("🆔 Feedback ID : " + selectedFeedback.getId()); // Vérification de l'ID du feedback

        // Vérifier si l'ID du feedback est valide
        if (selectedFeedback.getId() == 0) {
            showAlert("Erreur", "Le feedback sélectionné n'a pas d'ID valide.");
            return;
        }

        try {
            // Supprimer le feedback via le service
            fedbackService.supprimer(selectedFeedback);


            System.out.println("✅ Feedback supprimé avec succès.");
            showAlert("Succès", "Le feedback a été supprimé avec succès.");
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la suppression du feedback : " + e.getMessage());
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors de la suppression du feedback.");
        }
    }

}
