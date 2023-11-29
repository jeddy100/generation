package com.example.generation.main;

import com.example.generation.generation.CodeGenerator;
import com.example.generation.generation.DatabaseMetaDataReader;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static String[] arguments;

    public static void main(String[] args) {
        arguments = args;



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
                System.out.println(args[3]);
                CodeGenerator.generateJavaClassesTemplate(tableName.get(i), colonnes, colonnesType,args[0],args[1]);
                ///generation template .net.
//                CodeGenerator.generateJavaClassesTemplate(tableName.get(i), colonnes, colonnesType,".cs","Net-template.ftl");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String[] getArguments() {
        return arguments;
    }
}
