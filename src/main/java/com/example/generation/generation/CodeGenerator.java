package com.example.generation.generation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.generation.json.Conversion;
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


    private static String tableNameToClassName(String tableName) {
        // Transformation du nom de table en un nom de classe Java
        StringBuilder className = new StringBuilder();
        String[] words = tableName.split("_");
        for (String word : words) {
            className.append(word.substring(0, 1).toUpperCase()).append(word.substring(1));
        }
        return className.toString();
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
            String outputPath = args[8]+GENERATED_PACKAGE + File.separator + tableNameToClassName(tableName) + typeFile;
            writeToFiletemplate(outputPath, template, templateData);
            System.out.println(typeFile+" class written to file: " + outputPath);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
    public static List<ColumnInfo> createColumnInfoList(List<String> columns, List<String> columnTypes) {
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
        File packageDirectory = new File(args[8],GENERATED_PACKAGE);
        if (!packageDirectory.exists()) {
            packageDirectory.mkdir();
        }
        try (FileWriter writer = new FileWriter(new File(outputPath))) {
            template.process(templateData, writer);
        }
    }


    //////////////Mapping type de colonne conversion

    private static String mapColumnType(String columnType) {
        Map<String,String> typeMapping= Conversion.GetConversion();

        String javaType = typeMapping.get(columnType.toLowerCase());
        return (javaType != null) ? javaType : "Object";
    }



    ////////////////////generation template .java/////////

    public static void generateJavaClassesTemplate2(String tableName, List<ColumnInfo> columnInfoList,String template,String typefile) {
        String [] argos=Main.getArguments();
        String templateContent = readTemplateFromFile(template);
        String className = tableNameToClassName(tableName);
        System.out.println(typefile);
        if (typefile.equals(".java")){
            String packageDeclaration = "package " + GENERATED_PACKAGE + ";";
            String importDeclaration = generateImportDeclarations(columnInfoList);
            String columnTypeDeclarations = generateColumnTypeDeclarations(columnInfoList);
            String getterSetters = generateGetterSetters(columnInfoList);
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("package", packageDeclaration);
            templateData.put("import", importDeclaration);
            templateData.put("class", className);
            templateData.put("columntype", columnTypeDeclarations);
            templateData.put("columnname", ""); // Placeholder for column names, you can modify this
            templateData.put("getter", getterSetters);
            templateData.put("setter", ""); // Placeholder for setters, you can modify this

            // Write the code to a file
            String outputPath = argos[8] + GENERATED_PACKAGE + File.separator + tableNameToClassName(tableName) + typefile;
            try {
                writeToFile(outputPath, templateContent, templateData);
                System.out.println("Java class written to file: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (typefile.equals(".cs"))
        {
            String packageDeclaration = "namespace " + GENERATED_PACKAGE + ";";
            String importDeclaration = generateImportDeclarationsDotnet(columnInfoList);
            String columnTypeDeclarations = generateColumnTypeDeclarationsDotnet(columnInfoList);
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("package", packageDeclaration);
            templateData.put("import", importDeclaration);
            templateData.put("class", className);
            templateData.put("columntype", columnTypeDeclarations);
            templateData.put("columnname", ""); // Placeholder for column names, you can modify this
            templateData.put("getter", "");
            templateData.put("setter", ""); // Placeholder for setters, you can modify this

            // Write the code to a file
            String outputPath = argos[8] + GENERATED_PACKAGE + File.separator + tableNameToClassName(tableName) + typefile;
            try {
                writeToFile(outputPath, templateContent, templateData);
                System.out.println("Dotnet class written to file: " + outputPath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        // Replace placeholders in the template

    }

    private static String generateImportDeclarations(List<ColumnInfo> columnInfoList) {
        StringBuilder importDeclarations = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfoList) {
            if (columnInfo.getImp() != null && !columnInfo.getImp().isEmpty()) {
                importDeclarations.append("import ").append(columnInfo.getImp()).append(";\n");
            }
        }
        return importDeclarations.toString();
    }
    private static String generateImportDeclarationsDotnet(List<ColumnInfo> columnInfoList) {
        StringBuilder importDeclarations = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfoList) {
            if (columnInfo.getImp() != null && !columnInfo.getImp().isEmpty()) {
                importDeclarations.append("using ").append(columnInfo.getImp()).append(";\n");
            }
        }
        return importDeclarations.toString();
    }

    private static String generateColumnTypeDeclarations(List<ColumnInfo> columnInfoList) {
        StringBuilder declarations = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfoList) {
            declarations.append("\t").append(columnInfo.getType()).append(" ").append(columnInfo.getName()).append(";\n");
        }
        return declarations.toString();
    }
    private static String generateColumnTypeDeclarationsDotnet(List<ColumnInfo> columnInfoList) {
        StringBuilder declarations = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfoList) {
            declarations.append("\t").append(columnInfo.getType()).append(" ").append(columnInfo.getName()).append(" ").append("{get ;set }").append(";\n");
        }
        return declarations.toString();
    }

    private static String generateGetterSetters(List<ColumnInfo> columnInfoList) {
        StringBuilder getterSetters = new StringBuilder();
        for (ColumnInfo columnInfo : columnInfoList) {
            String capitalizedColumn = columnInfo.getCapitalizedName();

            // Getter
            getterSetters.append("\tpublic ").append(columnInfo.getType()).append(" get").append(capitalizedColumn).append("() {\n");
            getterSetters.append("\t\treturn ").append(columnInfo.getName()).append(";\n");
            getterSetters.append("\t}\n");

            // Setter
            getterSetters.append("\tpublic void set").append(capitalizedColumn).append("(")
                    .append(columnInfo.getType()).append(" ").append(columnInfo.getName()).append(") {\n");
            getterSetters.append("\t\tthis.").append(columnInfo.getName()).append(" = ").append(columnInfo.getName()).append(";\n");
            getterSetters.append("\t}\n");
        }
        return getterSetters.toString();
    }

    private static void writeToFile(String outputPath, String templateContent, Map<String, Object> templateData)
            throws IOException {
        String [] args = Main.getArguments();
        File packageDirectory = new File(args[8], GENERATED_PACKAGE);
        if (!packageDirectory.exists()) {
            packageDirectory.mkdir();
        }

        try (FileWriter writer = new FileWriter(new File(outputPath))) {
            // Replace placeholders in the template content
            for (Map.Entry<String, Object> entry : templateData.entrySet()) {
                String placeholder = "#" + entry.getKey() + "#";
                String replacement = entry.getValue().toString();
                templateContent = templateContent.replace(placeholder, replacement);
            }

            writer.write(templateContent);
        }
    }
    private static String readTemplateFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (InputStream is = CodeGenerator.class.getResourceAsStream("/templates/" + fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }





}
