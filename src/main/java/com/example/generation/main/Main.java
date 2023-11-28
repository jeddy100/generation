package com.example.generation.main;

import com.example.generation.generation.CodeGenerator;
import com.example.generation.generation.DatabaseMetaDataReader;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            DatabaseMetaDataReader.readDatabaseMetadata();
            List<String> tableName=DatabaseMetaDataReader.getTableName();
            for (int i = 0; i < tableName.size(); i++) {
                List<String>colonnes=DatabaseMetaDataReader.getTableColumns(tableName.get(i));
                List<String>colonnesType=DatabaseMetaDataReader.getTableColumnsType(tableName.get(i));
//                CodeGenerator.generateJavaClasses(tableName.get(i),colonnes,colonnesType);
//                CodeGenerator.generateJavaRepository(tableName.get(i));
//                CodeGenerator.generateJavaService(tableName.get(i));
//                CodeGenerator.generateJavaController(tableName.get(i));


                //generation template java
                CodeGenerator.generateJavaClassesTemplate(tableName.get(i), colonnes, colonnesType,".java","class-template.ftl");
                ///generation template .net
//                CodeGenerator.generateJavaClassesTemplate(tableName.get(i), colonnes, colonnesType,".cs","Net-template.ftl");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
