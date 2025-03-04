package com.esprit.controllers;

import com.esprit.models.Blog;
import com.esprit.services.BlogService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class BlogController {

    @FXML
    private TextField title;

    @FXML
    private TextField content;

    @FXML
    private Button btnadd;

    @FXML
    private Button btnreload;

    @FXML
    private TableView<Blog> table;

    @FXML
    private TableColumn<Blog, String> coltitle;

    @FXML
    private TableColumn<Blog, String> colcontent;

    @FXML
    private TableColumn<Blog, Date> coldate;

    @FXML
    private Button btndelete;

    @FXML
    private Button btndupdate;



    @FXML
    void ajouterBlog(ActionEvent event) {
        BlogService service = new BlogService();
        String titreText = title.getText();
        String descriptionText = content.getText();
        if (titreText.isEmpty() || descriptionText.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.show();
            return;
        }
        if (descriptionText.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La description doit avoir au moins 5 caractères.");
            alert.show();
            return;
        }
        Date currentDate = new Date();
        Blog blog = new Blog(0, title.getText(), content.getText(), currentDate);
        service.ajouter(blog);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Blog ajouté avec succès");
        alert.show();
        showblog(); // Rafraîchir la TableView après l'ajout du blog

        //    refreshTableView();
    }

    @FXML
    void addBlog(ActionEvent event) {
        BlogService service = new BlogService();
        String titreText = title.getText();
        String descriptionText = content.getText();
        if (titreText.isEmpty() || descriptionText.isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
            alert.show();
            return;
        }
        if (descriptionText.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La description doit avoir au moins 5 caractères.");
            alert.show();
            return;
        }
        Date currentDate = new Date();
        Blog blog = new Blog(0, title.getText(), content.getText(), currentDate);
        service.ajouter(blog);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Blog ajouté avec succès");
        alert.show();
        showblog();
        // Rafraîchir la TableView après l'ajout du blog
        //navigateToBlogList();
        //    refreshTableView();
    }

    @FXML
    void navigateToBlogList(MouseEvent event) {
        System.out.println("Je navigue");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/BlogList.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    public void showblog() {
        BlogService blogServiceback = new BlogService();
        ObservableList<Blog> list = blogServiceback.getAll();
        table.setItems(list);
        coltitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colcontent.setCellValueFactory(new PropertyValueFactory<>("content"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void initialize() {
        showblog();
    }

   /* @FXML
    void showDetails(ActionEvent event) {
        Blog selectedBlog = table.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails du Blog");
            alert.setHeaderText(selectedBlog.getTitle());
            alert.setContentText(selectedBlog.getContent());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun blog sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un blog pour afficher les détails.");
            alert.showAndWait();
        }
    }*/

    @FXML
    void showDetails(ActionEvent event) {
        Blog selectedBlog = table.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            try {
                // Load the BlogDetailsView.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlogDetails.fxml"));
                Parent root = loader.load();

                // Get the controller and pass the selected blog
                BlogDetailsController controller = loader.getController();
                controller.setSelectedBlog(selectedBlog);

                // Open in a new window
                Stage stage = new Stage();
                stage.setTitle("Détails du Blog");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun blog sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un blog pour afficher les détails.");
            alert.showAndWait();
        }
    }


    @FXML
    void getData(MouseEvent event) {
        Blog blog = table.getSelectionModel().getSelectedItem();
        if (blog != null) {
            title.setText(blog.getTitle());
            content.setText(blog.getContent());
            // Mettre à jour le bouton "Ajouter" pour le désactiver pendant la mise à jour
            btnadd.setDisable(true);
        } else {
            // Réactiver le bouton "Ajouter" si aucun blog n'est sélectionné
            btnadd.setDisable(false);
        }
    }

    @FXML
    void update(ActionEvent event) {
        Blog blog = table.getSelectionModel().getSelectedItem();
        blog.setTitle(title.getText());
        blog.setContent(content.getText());
        BlogService blogServiceback = new BlogService();
        blogServiceback.modifier(blog);
        showblog(); // Rafraîchir la TableView après la mise à jour du blog

        //refreshTableView();
    }
    @FXML
    void delete(ActionEvent event) {
        Blog blog = table.getSelectionModel().getSelectedItem();
        BlogService service = new BlogService();
        service.delete(blog);
        showblog(); // Rafraîchir la TableView après la suppression du blog
        //refreshTableView();
    }

    public void show_acceuil(ActionEvent actionEvent) {
    }

    public void show_transport(ActionEvent actionEvent) {
    }

    public void show_service(ActionEvent actionEvent) {
    }

    public void show_event(ActionEvent actionEvent) {
    }

    public void show_blog(ActionEvent actionEvent) {
    }

    public void show_parametres(ActionEvent actionEvent) {
    }

    public void show_se_deconnecter(ActionEvent actionEvent) {
    }

    public void quiiter(ActionEvent actionEvent) {
    }

    public void show_utilisateurs(ActionEvent actionEvent) {
    }

    public void onPDF(ActionEvent actionEvent) {
        PDFExporter.exportTableViewToPDF(table);
    }
}
