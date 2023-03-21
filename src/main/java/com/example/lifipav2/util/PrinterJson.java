package com.example.lifipav2.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PrinterJson {
    private static PrinterJson instance;

    private PrinterJson() {
        instance = new PrinterJson();
    }

    public static PrinterJson getInstance() {
        if (instance == null) {
            instance = new PrinterJson();
        }
        return instance;
    }

    public String toString(Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o).trim().replace("\n", "").replace("\t", "");
    }
}
