package com.esprit.tests;

import com.esprit.models.Blog;
import com.esprit.models.Avis;
import com.esprit.services.BlogService;
import com.esprit.services.AvisService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BlogService blogService = new BlogService();
    private static final AvisService avisService = new AvisService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Manage Blogs");
            System.out.println("2. Manage Avis");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageBlogs();
                    break;
                case 2:
                    manageAvis();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageBlogs() {
        while (true) {
            System.out.println("\n--- Blog Management ---");
            System.out.println("1. Add Blog");
            System.out.println("2. Update Blog");
            System.out.println("3. Delete Blog");
            System.out.println("4. View All Blogs");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBlog();
                    break;
                case 2:
                    updateBlog();
                    break;
                case 3:
                    deleteBlog();
                    break;
                case 4:
                    viewAllBlogs();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageAvis() {
        while (true) {
            System.out.println("\n--- Avis Management ---");
            System.out.println("1. Add Avis");
            System.out.println("2. Update Avis");
            System.out.println("3. Delete Avis");
            System.out.println("4. View All Avis");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addAvis();
                    break;
                case 2:
                    updateAvis();
                    break;
                case 3:
                    deleteAvis();
                    break;
                case 4:
                    viewAllAvis();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addBlog() {
        System.out.print("Enter blog title: ");
        String title = scanner.nextLine();
        System.out.print("Enter blog content: ");
        String content = scanner.nextLine();
        System.out.print("Enter blog date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        try {
            // Parse the date string into java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dateStr);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create and add the blog
            Blog blog = new Blog(title, content, sqlDate);
            blogService.ajouter(blog);
            System.out.println("Blog added successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    private static void updateBlog() {
        System.out.print("Enter blog ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new content: ");
        String content = scanner.nextLine();
        System.out.print("Enter new date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        try {
            // Parse the date string into java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dateStr);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create and update the blog
            Blog blog = new Blog(id, title, content, sqlDate);
            blogService.modifier(blog);
            System.out.println("Blog updated successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    private static void deleteBlog() {
        System.out.print("Enter blog ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Blog blog = new Blog();
        blog.setId(id);
        blogService.supprimer(blog);
        System.out.println("Blog deleted successfully!");
    }

    private static void viewAllBlogs() {
        List<Blog> blogs = blogService.rechercher();
        if (blogs.isEmpty()) {
            System.out.println("No blogs found.");
        } else {
            for (Blog blog : blogs) {
                System.out.println(blog);
            }
        }
    }

    private static void addAvis() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        try {
            // Parse the date string into java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dateStr);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create and add the avis
            Avis avis = new Avis(name, comment, sqlDate);
            avisService.ajouter(avis);
            System.out.println("Avis added successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    private static void updateAvis() {
        System.out.print("Enter avis ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter new date (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();

        try {
            // Parse the date string into java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date utilDate = sdf.parse(dateStr);

            // Convert java.util.Date to java.sql.Date
            Date sqlDate = new Date(utilDate.getTime());

            // Create and update the avis
            Avis avis = new Avis(id, name, comment, sqlDate);
            avisService.modifier(avis);
            System.out.println("Avis updated successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        }
    }

    private static void deleteAvis() {
        System.out.print("Enter avis ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Avis avis = new Avis();
        avis.setId(id);
        avisService.supprimer(avis);
        System.out.println("Avis deleted successfully!");
    }

    private static void viewAllAvis() {
        List<Avis> avisList = avisService.rechercher();
        if (avisList.isEmpty()) {
            System.out.println("No avis found.");
        } else {
            for (Avis avis : avisList) {
                System.out.println(avis);
            }
        }
    }
}