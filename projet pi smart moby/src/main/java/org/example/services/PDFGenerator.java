package org.example.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    // Générer un PDF avec une liste de chaînes de caractères (ex: noms de produits)
    public static void generatePDFFromStringList(List<String> items, String filePath) throws IOException {
        // Créer un PdfWriter
        PdfWriter writer = new PdfWriter(filePath);

        // Créer un PdfDocument
        PdfDocument pdf = new PdfDocument(writer);

        // Créer un document à partir du PdfDocument
        Document document = new Document(pdf);

        // Titre du document
        document.add(new Paragraph("Liste des Produits"));

        // Créer une table avec une seule colonne pour les produits
        Table table = new Table(1);  // 1 colonne pour le nom du produit
        table.addCell(new Cell().add(new Paragraph("Produit")));

        // Ajouter chaque produit de la liste des chaînes de caractères à la table
        for (String item : items) {
            table.addCell(new Cell().add(new Paragraph(item)));
        }

        // Ajouter la table au document
        document.add(table);

        // Fermer le document
        document.close();
    }
}


