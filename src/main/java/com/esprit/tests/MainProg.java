package com.esprit.tests;

import com.esprit.models.Blog;

import com.esprit.services.BlogService;
import com.esprit.utils.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import java.text.ParseException;


public class MainProg {
    public static void main(String[] args) throws ParseException {
        // Parse the date string into a java.util.Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date utilDate = sdf.parse("12/12/2025");

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        // Create BlogService and add the blog
        BlogService bs = new BlogService();
        bs.ajouter(new Blog("Hazem", "Ali", sqlDate));



        System.out.println(bs.rechercher());
    }
}
