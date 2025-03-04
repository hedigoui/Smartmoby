package com.esprit.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;

public class PDFExporter {

    public static <T> void exportTableViewToPDF(TableView<T> tableView) {
        try {
            // Open file chooser for saving PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            fileChooser.setTitle("Save PDF");
            File file = fileChooser.showSaveDialog(null);

            if (file == null) return;

            // Create PDF document
            PdfWriter writer = new PdfWriter(file.getAbsolutePath());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Create table with the same number of columns as TableView
            Table table = new Table(tableView.getColumns().size());

            // Add column headers
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                table.addHeaderCell(new Cell().add(new Paragraph(column.getText())));
            }

            // Add table rows
            ObservableList<T> items = tableView.getItems();
            for (T item : items) {
                for (TableColumn<T, ?> column : tableView.getColumns()) {
                    // Check if the cell has an observable value
                    Object cellValue = null;
                    if (column.getCellObservableValue(item) != null) {
                        cellValue = column.getCellObservableValue(item).getValue();
                    }

                    // Add the cell value to the PDF, handle nulls gracefully
                    table.addCell(new Cell().add(new Paragraph(cellValue != null ? cellValue.toString() : "")));
                }
            }

            // Add table to document and close
            document.add(table);
            document.close();
            System.out.println("PDF Exported Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
