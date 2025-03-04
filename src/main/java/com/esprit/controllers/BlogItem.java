package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import com.esprit.services.MyListener;
import com.esprit.models.Blog;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class BlogItem {

    @FXML
    private ResourceBundle resources;



    @FXML
    private Label titleLabel;

    @FXML
    private Label datelabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label priceLable;

    private Blog blog;
    private MyListener myListener;

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(blog);
    }

    public void setData(Blog blog, MyListener myListener) {
        this.blog = blog;
        this.myListener = myListener;

        // Ensure labels are initialized
        if (titleLabel != null) {
            titleLabel.setText(blog.getTitle());
        }
        if (contentLabel != null) {
            contentLabel.setText(blog.getContent());
        }

        // Handle null date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (blog.getDate() != null && datelabel != null) {
            datelabel.setText(sdf.format(blog.getDate()));
        } else if (datelabel != null) {
            datelabel.setText("No date available");
        }
    }


}
