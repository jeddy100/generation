package com.example.generation.generation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.generation.json.Type;
import com.example.generation.main.Main;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


// CodeGenerator.java
public class CodeGenerator {
    private static final String GENERATED_PACKAGE = "model_genere";
    private static final String GENERATED_PACKAGE_REPOSITORY = "repository_genere";
    private static final String GENERATED_PACKAGE_SERVICE= "service_genere";
    private static final String GENERATED_PACKAGE_CONTROLLER= "controller_genere";
    String [] args=Main.getArguments();



    public static void generateJavaClasses(String tableName, List<String> columns, List<String> columnTypes) {


        StringBuilder classCode = new StringBuilder();
        classCode.append("package ").append(GENERATED_PACKAGE).append(";").append("\n");
        classCode.append("public class ").append(tableNameToClassName(tableName)).append(" {\n");

        // Générer les champs de classe
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String columnType = columnTypes.get(i);
            String capitalizedColumn = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);

            classCode.append("\tprivate ").append(mapColumnType(columnType)).append(" ").append(columnName).append(";\n");

            ///////getter
            classCode.append("\tpublic ").append(mapColumnType(columnType)).append(" get").append(capitalizedColumn).append("() {\n");
            classCode.append("\t\treturn ").append(columnName).append(";\n");
            classCode.append("\t}\n");

            /////setter
            classCode.append("\tpublic void set").append(capitalizedColumn).append("(").append(mapColumnType(columnType)).append(" ").append(columnName).append(") {\n");
            classCode.append("\t\tthis.").append(columnName).append(" = ").append(columnName).append(";\n");
            classCode.append("\t}\n");



        }
        /////constructeur
        classCode.append("\tpublic ").append(tableNameToClassName(tableName)).append("(");
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String columnType = columnTypes.get(i);
            classCode.append(mapColumnType(columnType)).append(" ").append(columnName).append(",");

        }
        classCode.deleteCharAt(classCode.length()-1);
        classCode.append(")");
        classCode.append("\t{").append("\n");
        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String columnType = columnTypes.get(i);
            classCode.append("\tthis.").append(columnName).append(" = ").append(columnName).append(";").append("\n");
        }

        classCode.append("}").append("\n");

        classCode.append("}");

        // Écrire le code dans un fichier
        writeToFile(tableNameToClassName(tableName) + ".java", classCode.toString(),GENERATED_PACKAGE);


        System.out.println("Generating Java class for table: " + tableName);
    }
    private static String tableNameToClassName(String tableName) {
        // Transformation du nom de table en un nom de classe Java
        StringBuilder className = new StringBuilder();
        String[] words = tableName.split("_");
        for (String word : words) {
            className.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return className.toString();
    }

    private static String mapColumnType(String columnType) {
        // Mapping des types de colonnes PostgreSQL à des types Java
        switch (columnType.toLowerCase()) {
            case "int":
            case"int4":
            case "serial":
                return "int";
            case "varchar":
                return "String";
            case "double precision":
            case "double":
                return"double";
            case "timestamp":
                return"Timestamp";
            case"date":
                return "Date";
            // Ajoutez d'autres cas selon vos besoins
            default:
                return "Object"; // Ou ajustez selon votre logique
        }
    }
    private static void writeToFile(String fileName, String content,String GENERATED_PACKAGE) {
        try {
            // Créer le répertoire du package s'il n'existe pas
            File packageDirectory = new File(GENERATED_PACKAGE);
            if (!packageDirectory.exists()) {
                packageDirectory.mkdir();
            }

            // Créer le fichier dans le répertoire du package
            File file = new File(GENERATED_PACKAGE + File.separator + fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                System.out.println("Java class written to file: " + file.getPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////genereation code repository
    public static void generateJavaRepository(String tableName) {

        StringBuilder classCode = new StringBuilder();
        classCode.append("package ").append(GENERATED_PACKAGE_REPOSITORY).append(";").append("\n");

        classCode.append("public interface ").append(tableNameToClassName(tableName)).append("Repository").append(" extends JpaRepository<").append(tableNameToClassName(tableName)).append(">").append(" {\n");
        classCode.append("}\n");
        // Écrire le code dans un fichier
        writeToFile("Repository"+ tableNameToClassName(tableName) + ".java", classCode.toString(),GENERATED_PACKAGE_REPOSITORY);


        System.out.println("Generating Java repository for table: " + tableName);

    }

    public static void generateJavaService(String tableName) {

        StringBuilder classCode = new StringBuilder();
        classCode.append("package ").append(GENERATED_PACKAGE_SERVICE).append(";").append("\n");
        classCode.append("public class ").append(tableNameToClassName(tableName)).append("Service").append(" {\n");
        classCode.append("}\n");

        writeToFile("Service"+ tableNameToClassName(tableName) + ".java", classCode.toString(),GENERATED_PACKAGE_SERVICE);


        System.out.println("Generating Java service for table: " + tableName);

    }

    public static void generateJavaController(String tableName) {
        StringBuilder classCode =  new StringBuilder();
        classCode.append("package ").append(GENERATED_PACKAGE_CONTROLLER).append(";").append("\n");
        classCode.append("public class ").append(tableNameToClassName(tableName)).append("Controller").append(" {\n");
        classCode.append("}\n");

        writeToFile("Controller"+ tableNameToClassName(tableName) + ".java", classCode.toString(),GENERATED_PACKAGE_CONTROLLER);


        System.out.println("Generating Java Controller for table: " + tableName);

    }


    ////////////////////////////generation code avec template

    public static void generateJavaClassesTemplate(String tableName, List<String> columns, List<String> columnTypes,String typeFile,String templateUsed) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setClassForTemplateLoading(CodeGenerator.class, "/templates");

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("className", tableNameToClassName(tableName));

        List<ColumnInfo> columnInfoList = createColumnInfoList(columns, columnTypes);
        templateData.put("columns", columnInfoList);

        String packag =GENERATED_PACKAGE;
        templateData.put("package",packag);





        try {
            String [] args=Main.getArguments();

            Template template = cfg.getTemplate(templateUsed);
            String outputPath = args[3]+GENERATED_PACKAGE + File.separator + tableNameToClassName(tableName) + typeFile;
            writeToFiletemplate(outputPath, template, templateData);
            System.out.println(typeFile+" class written to file: " + outputPath);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
    private static List<ColumnInfo> createColumnInfoList(List<String> columns, List<String> columnTypes) {
        List<ColumnInfo> columnInfoList = new ArrayList<>();

        for (int i = 0; i < columns.size(); i++) {
            String columnName = columns.get(i);
            String columnType = columnTypes.get(i);
            String mapedValue=mapColumnType(columnType);
            ColumnInfo columnInfo = new ColumnInfo(columnName, mapedValue, Type.GetTypes().get(mapedValue));
            columnInfoList.add(columnInfo);
        }

        return columnInfoList;
    }
    private static void writeToFiletemplate(String outputPath, Template template, Map<String, Object> templateData)
            throws IOException, TemplateException {
        String [] args=Main.getArguments();
        File packageDirectory = new File(args[3],GENERATED_PACKAGE);
        if (!packageDirectory.exists()) {
            packageDirectory.mkdir();
        }
        try (FileWriter writer = new FileWriter(new File(outputPath))) {
            template.process(templateData, writer);
        }
    }



}
