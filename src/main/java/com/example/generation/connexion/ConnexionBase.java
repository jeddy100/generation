package com.example.generation.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBase {
    public static Connection connect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/generation";
        String user= "postgres";
        String password= "1234";
        return DriverManager.getConnection(url,user,password);

    }
}
