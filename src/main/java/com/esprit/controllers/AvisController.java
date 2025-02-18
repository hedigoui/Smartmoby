package com.esprit.controllers;

import com.esprit.models.Avis;
import com.esprit.models.Blog;
import com.esprit.services.AvisService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.Date;

public class AvisController {

    @FXML
    private TextArea commentField;

    @FXML
    private Button btnAddComment;

    @FXML
    private Button btnUpdateComment;

    @FXML
    private Button btnDeleteComment;

    @FXML
    private TableView<Avis> commentTable;

    @FXML
    private TableColumn<Avis, String> colComment;

    private Blog selectedBlog;
    private AvisService avisService = new AvisService();

    public void setSelectedBlog(Blog blog) {
        this.selectedBlog = blog;
        loadComments();
    }

    @FXML
    void addComment(ActionEvent event) {
        if (selectedBlog == null) {
            showAlert("Erreur", "Veuillez sélectionner un blog avant d'ajouter un commentaire.", Alert.AlertType.ERROR);
            return;
        }

        String commentText = commentField.getText();
        if (commentText.isEmpty()) {
            showAlert("Erreur", "Le commentaire ne peut pas être vide.", Alert.AlertType.ERROR);
            return;
        }

        // Convert the blog ID to String for the 'name' property
        String blogIdString = String.valueOf(selectedBlog.getId());
        Avis avis = new Avis(blogIdString, commentText, new Date()); // Assuming name is blog ID converted to String
        avisService.ajouter(avis);

        showAlert("Succès", "Commentaire ajouté avec succès!", Alert.AlertType.INFORMATION);
        loadComments();
    }


    @FXML
    void updateComment(ActionEvent event) {
        Avis selectedAvis = commentTable.getSelectionModel().getSelectedItem();
        if (selectedAvis == null) {
            showAlert("Erreur", "Veuillez sélectionner un commentaire à modifier.", Alert.AlertType.ERROR);
            return;
        }
        String updatedText = commentField.getText();
        if (updatedText.isEmpty()) {
            showAlert("Erreur", "Le commentaire ne peut pas être vide.", Alert.AlertType.ERROR);
            return;
        }
        selectedAvis.setComment(updatedText);
        avisService.modifier(selectedAvis);
        showAlert("Succès", "Commentaire mis à jour avec succès!", Alert.AlertType.INFORMATION);
        loadComments();
    }

    @FXML
    void deleteComment(ActionEvent event) {
        Avis selectedAvis = commentTable.getSelectionModel().getSelectedItem();
        if (selectedAvis == null) {
            showAlert("Erreur", "Veuillez sélectionner un commentaire à supprimer.", Alert.AlertType.ERROR);
            return;
        }
        avisService.supprimer(selectedAvis.getAvisId());
        showAlert("Succès", "Commentaire supprimé avec succès!", Alert.AlertType.INFORMATION);
        loadComments();
    }

    @FXML
    void selectComment(MouseEvent event) {
        Avis selectedAvis = commentTable.getSelectionModel().getSelectedItem();
        if (selectedAvis != null) {
            commentField.setText(selectedAvis.getComment());
        }
    }

    private void loadComments() {
        if (selectedBlog != null) {
            ObservableList<Avis> comments = avisService.getCommentsByBlogId(selectedBlog.getId());
            commentTable.setItems(comments);
            colComment.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
