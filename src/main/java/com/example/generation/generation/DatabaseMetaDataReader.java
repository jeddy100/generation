package com.example.generation.generation;
import com.example.generation.connexion.ConnexionBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMetaDataReader {
    public static List<String> getTableColumns(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();

        try (Connection connection = ConnexionBase.connect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, "%");

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columns.add(columnName);
            }
        }

        return columns;
    }

    public static List<String> getTableColumnsType(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();

        try (Connection connection = ConnexionBase.connect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, "%");

            while (resultSet.next()) {
                String columnName = resultSet.getString("TYPE_NAME");
                columns.add(columnName);
            }
        }

        return columns;
    }

    public static List<String> getTableName() throws SQLException {
        List<String> tableName= new ArrayList<>();
        try (Connection connection = ConnexionBase.connect()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tablenom = tables.getString("TABLE_NAME");
                tableName.add(tablenom);
            }

        }
        return tableName;

    }



        public static void readDatabaseMetadata() throws SQLException {
        try (Connection connection = ConnexionBase.connect()) {
            List<String> tables=getTableName();
            for (int j = 0; j <tables.size() ; j++) {

                String tableName = tables.get(j);
                List<String> colonnes = getTableColumns(tableName);
                List<String> colonnestype = getTableColumnsType(tableName);



                System.out.println("Table: " + tableName);

                for (int i = 0; i <colonnes.size() ; i++) {
                    System.out.println(colonnes.get(i));
                    System.out.println(colonnestype.get(i));

                }

                // À ce stade, vous pouvez générer dynamiquement des classes Java en utilisant le nom de la table, les colonnes, etc.
            }


        }
    }
}
