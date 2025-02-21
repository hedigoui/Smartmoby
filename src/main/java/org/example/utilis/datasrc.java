package org.example.utilis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class datasrc {

    private static datasrc instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/event_pi";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private datasrc() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static datasrc getInstance() {
        if(instance == null)
            instance = new datasrc();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}