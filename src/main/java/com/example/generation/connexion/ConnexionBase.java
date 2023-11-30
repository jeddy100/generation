package com.example.generation.connexion;

import com.example.generation.main.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBase {
    public static Connection connect() throws SQLException {
        String [] args= Main.getArguments();
        String url = "jdbc:postgresql://"+args[5]+":"+args[6]+"/"+args[2];
        String user= args[3];
        String password= args[4];
        return DriverManager.getConnection(url,user,password);


    }
}
