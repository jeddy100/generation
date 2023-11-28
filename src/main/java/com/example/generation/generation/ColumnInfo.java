package com.example.generation.generation;

public class ColumnInfo {
    private String name;
    private String type;

    private String imp;

    public ColumnInfo(String name, String type, String imp) {
        this.name = name;
        this.type = type;
        this.imp = imp;
    }

    public String getImp() {
        return imp;
    }

    public void setImp(String imp) {
        this.imp = imp;
    }

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
