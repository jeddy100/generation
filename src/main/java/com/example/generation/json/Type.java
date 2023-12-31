package com.example.generation.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Type {

    public static Map<String,String> GetTypes(){
        Map<String,String> listType=new HashMap<>();
        try {
            String jsonFilePath = "C:/Users/Jeddy/IdeaProjects/GenerationCode/generation/src/main/resources/type.json";
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
