package com.esprit.controllers;

import com.esprit.models.Blog;
import com.esprit.models.Avis;
import com.esprit.services.AvisService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class BlogDetailsController {
    @FXML
    private TextArea titleTextArea; // TextArea for blog title
    @FXML
    private TextArea contentTextArea; // TextArea for blog content
    @FXML
    private ListView<Avis> commentsListView; // ListView for comments
    @FXML
    private TextField commentTextField; // TextField for new comment

    private Blog selectedBlog; // The blog post being displayed

    private AvisService avisService = new AvisService();

    // Method to set the selected blog post
    public void setSelectedBlog(Blog blog) {
        this.selectedBlog = blog;
        updateUI(); // Update the UI with blog details and comments
    }

    // Update the UI with blog details and comments
    private void updateUI() {
        if (selectedBlog != null) {
            titleTextArea.setText(selectedBlog.getTitle()); // Set title
            contentTextArea.setText(selectedBlog.getContent()); // Set content

            // Fetch comments for the selected blog (replace with your data fetching logic)
            ObservableList<Avis> comments = fetchCommentsForBlog(selectedBlog.getId());
            commentsListView.setItems(comments); // Set comments in the ListView
        }
    }

    // Fetch comments for a specific blog (replace with your actual data source)
    private ObservableList<Avis> fetchCommentsForBlog(int blogId) {
        ObservableList<Avis> comments = FXCollections.observableArrayList();

        // Example: Fetch comments from a database or service
        // Here, we're just adding some dummy data
        //comments.add(new Avis(1, "John Doe", "Great post!", new Date()));
        //comments.add(new Avis(2, "Jane Smith", "Very informative.", new Date()));

        return comments;
    }

    // Handle adding a new comment
    @FXML
    private void handleAddComment() {
        String commentText = commentTextField.getText().trim();
        if (!commentText.isEmpty()) {
            // Create a new Avis object (replace "Anonymous" with actual user name if available)
            Avis newComment = new Avis("Anonymous", commentText, new Date());
            newComment.setBlogId(selectedBlog.getId()); // Set the blog ID

            // Add the comment to the database
            avisService.ajouter(newComment);


            // Refresh the comments list
            commentsListView.getItems().add(newComment);
            commentTextField.clear(); // Clear the TextField
        }
    }

    // Add a comment to the data source (replace with your actual logic)
    private void addCommentToDataSource(Avis comment) {
        // Example: Save the comment to a database or service
        System.out.println("New comment added: " + comment.getComment());
    }

    @FXML
    private void DeleteComment(ActionEvent event) {
        // Get the selected comment from the ListView
        Avis selectedComment = commentsListView.getSelectionModel().getSelectedItem();

        if (selectedComment != null) {
            // Delete the comment from the database
            avisService.supprimer(selectedComment);

            // Remove the comment from the ListView
            commentsListView.getItems().remove(selectedComment);

            System.out.println("Comment deleted: " + selectedComment.getComment());
        } else {
            // Show a warning if no comment is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Comment Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a comment to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void UpdateComment(ActionEvent event) {
    }
}