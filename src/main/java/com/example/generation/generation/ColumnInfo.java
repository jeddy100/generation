package com.example.generation.generation;

public class ColumnInfo {
    private String name;
    private String type;

    public ColumnInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCapitalizedName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
