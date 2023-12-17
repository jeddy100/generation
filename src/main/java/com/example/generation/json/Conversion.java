package com.example.generation.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Conversion {
    public static Map<String, String> GetConversion() {
        Map<String, String> listType = new HashMap<>();
        try {
            String jsonFilePath = "C:/Users/Jeddy/IdeaProjects/GenerationCode/generation/src/main/resources/Conversion.json";
            Gson gson = new Gson();
            java.lang.reflect.Type type = new TypeToken<Map<String, String>>() {
            }.getType();

            listType = gson.fromJson(new FileReader(jsonFilePath), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listType;
    }
}
