package com.example.generation.json;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Type {

    public static Map<String,String> GetTypes(){
        Map<String,String> listType=new HashMap<>();
        listType.put("Date", "java.sql.Date");
        listType.put("Timestamp", "java.sql.Timestamp");
        listType.put("int","");
        listType.put("double","");
        listType.put("String","");

        return listType;

    }
}
