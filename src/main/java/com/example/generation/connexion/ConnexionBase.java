package com.example.generation.connexion;

import com.example.generation.main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBase {
    public static Connection connect() throws SQLException {
        String [] args= Main.getArguments();
        String url = "jdbc:postgresql://localhost:5432/"+args[2];
        String user= "postgres";
        String password= "1234";
        return DriverManager.getConnection(url,user,password);


    }
}
