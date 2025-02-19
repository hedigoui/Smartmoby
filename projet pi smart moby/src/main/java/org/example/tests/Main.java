package org.example.tests;

import org.example.models.*;
import org.example.services.*;
import org.example.utils.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BlogService blogService = new BlogService();
    private static final AvisService avisService = new AvisService();
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();

        Connection connection = dataSource.getConnection();

        String imagePath = "C:\\Users\\hayth\\OneDrive\\Bureau\\pi java\\image.png";

        // Charger l'image sous forme de tableau de bytes
        byte[] photoBytes = loadImageAsBytes(imagePath);

        //haythem

        //user
        Utilisateur_service u = new Utilisateur_service();
        u.ajouter(new Utilisateur("ahmed", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.ADMIN));
        /*u.modifier(new Utilisateur(97, "chams", "ayari", "user", "aa@gmail.com", "aaaa"));
        u.supprimer(new Utilisateur(53, "", "", "", "", "",null));*/
        u.ajouter(new Utilisateur("haythem", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.CONDUCTEUR));
        System.out.println(u.afficher());


        //admin
        Admin_service a = new Admin_service();
        a.modifier(new Admin(97, "hamda", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.ADMIN,"marketing"),new Utilisateur(97, "chams", "ayari", "user", "aa@gmail.com", "aaaa"));
        a.supprimer(new Admin(199, "", "", "", "", "",null,""), new Utilisateur(99, "", "", "", "", "",null));
        System.out.println(a.afficher());
        //conducteur
        Conducteur_service c = new Conducteur_service();
        c.modifier(new Conducteur(130, "hitler", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.CONDUCTEUR,2222), new Utilisateur(130, "hitler", "ayari", "user", "aa@gmail.com", "aaaa"));
        c.supprimer(new Conducteur(136,"", "", "", "", "",null,0),new Utilisateur(136,"", "", "", "", "",null));

        //organisateur
        u.ajouter(new Utilisateur("ahmed", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.ORGANISATEUR));
        Organisateur_service o = new Organisateur_service();
        o.modifier(new Organisateur(164,"hamda", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.ORGANISATEUR,500), new Utilisateur(164,"hamda", "ayari", "user", "aa@gmail.com", "aaaa"));
        o.supprimer(new Organisateur(161,"","","","","",null,0), new Utilisateur(161,"","","","","",null));
        System.out.println(o.afficher());
        //client
        u.ajouter(new Utilisateur("ahmed", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.CLIENT));
        Client_service cl = new Client_service();
        cl.modifier(new Client(177,"hamda", "ayari", "user", "aa@gmail.com", "aaaa", Utilisateur.Role.CLIENT,177),new Utilisateur(177,"hamda", "ayari", "user", "aa@gmail.com", "aaaa"));
        cl.supprimer(new Client(177,"", "", "", "", "", null,0),new Utilisateur(177,"","","","","",null));
        System.out.println(cl.afficher());
        //Trajets (hedi)


        Vehicule s1 = new Vehicule(6, "trotinette", 1, "hors service", false);
        Vehicule vehiculeToUpdate = new Vehicule(5, "bus", 4, "Available", false);
        Services sap = new Services();
        sap.add(s1);
        sap.update(vehiculeToUpdate);
        // Print the result of sap.getAll()
        System.out.println(sap.getAll_vehicule());
        Vehicule stockToDelete = new Vehicule();
        stockToDelete.setId(4);


        boolean isDeleted = sap.delete(stockToDelete);

        if (isDeleted) {
            System.out.println("Vehicule deleted successfully");
        } else {
            System.out.println("Failed to deleted Vehicule");
        }

        System.out.println("Vehicule after deleting" + sap.getAll());


        //TRAJET
        Services tap = new Services();
        Trajet t1 = new Trajet(2, "ariana", "petite ariana",
                Timestamp.valueOf(LocalDateTime.of(2026, 8, 10, 17, 30)),  // Date et heure de départ
                Timestamp.valueOf(LocalDateTime.of(2026, 8, 10, 19, 45)), // Date et heure d'arrivée
                6, 5.5);

        tap.addtrajet(t1);
        Trajet targetToUpdate = new Trajet(5, "aouina", "zahrouni",
                Timestamp.valueOf(LocalDateTime.of(2025, 5, 20, 14, 30)),
                Timestamp.valueOf(LocalDateTime.of(2025, 5, 20, 16, 45)),
                6, 5.5);
        tap.update(targetToUpdate);
        ArrayList<Trajet> trajets = tap.getAll();
        for (Trajet trajet : trajets) {
            System.out.println(trajet);  // Assurez-vous que `Trajet` a une méthode `toString()` correcte
        }
        System.out.println(tap.getAll());


        Trajet stockToDelete2 = new Trajet();
        stockToDelete2.setId(5);


        boolean isDeleted2 = tap.delete(stockToDelete);

        if (isDeleted2) {
            System.out.println("Trajet deleted successfully");
        } else {
            System.out.println("Failed to deleted Trajet");
        }

        System.out.println("Trajet after deleting" + tap.getAll());


        //Oussema

        event_serv e1 = new event_serv();
        fedback_serv feedbackService = new fedback_serv();
        fedback newFeedback = new fedback("Super événement, très bien organisé!", 5);
        feedbackService.ajouter(newFeedback);
        fedback feedbackToModify = new fedback(1, "Événement génial, mais quelques améliorations.", 4);
        feedbackService.modifier(feedbackToModify);
        fedback feedbackToDelete = new fedback(1, null, 0);
        feedbackService.supprimer(feedbackToDelete);
        List<fedback> fedbacks = feedbackService.afficher();


        if (fedbacks.isEmpty()) {
            System.out.println("Aucun feedback trouvé.");
        } else {
            System.out.println("Liste des feedbacks :");

            for (fedback feedback : fedbacks) {
                System.out.println("ID : " + feedback.getId() + ", Commentaire : " + feedback.getCommentaire() + ", Note : " + feedback.getNote());
            }
        }


        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse("18/10/2025");

            event newEvent = new event("Conférence Tech", date, "Tunis");
            e1.ajouter(newEvent);
            event eventToModify = new event(2, "Conférence Tech - Nouvelle Edition", date, "Tunis");  // ID de l'événement à modifier.

            // Appeler la méthode modifier pour changer les informations de l'événement dans la base de données
            e1.modifier(eventToModify);
            event eventasupprimer = new event(15, "Conférence Tech - Nouvelle Edition", date, "Tunis");
            eventasupprimer.setId(15);  // Supposons que l'utilisateur avec ID 1 existe dans la base de données

            // Appeler la méthode supprimer pour supprimer l'utilisateur
            e1.supprimer(eventasupprimer);
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        List<event> events = e1.afficher();

        // Afficher les événements récupérés
        if (events.isEmpty()) {
            System.out.println("Aucun événement trouvé.");
        } else {
            System.out.println("Événements trouvés :");
            for (event ev : events) {
                // Afficher les informations de chaque événement
                System.out.println(ev);
            }
        }
        //hazem
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






    private static byte[] loadImageAsBytes(String imagePath) {
        File file = new File(imagePath);
        byte[] imageBytes = null;

        try (FileInputStream fis = new FileInputStream(file)) {
            imageBytes = new byte[(int) file.length()];
            fis.read(imageBytes);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture de l'image : " + e.getMessage());
        }

        return imageBytes;
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
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

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
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

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
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

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
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

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




