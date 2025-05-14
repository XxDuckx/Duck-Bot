package com.duckbot.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InstancePersistenceManager {
    private static final File FILE = new File("config/instances.json");

    public static List<String> loadInstanceIds() {
        try {
            if (!FILE.exists()) return new ArrayList<>();
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> result = new Gson().fromJson(new FileReader(FILE), listType);
            return result != null ? result : new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveInstanceIds(List<String> ids) {
        try {
            FILE.getParentFile().mkdirs();
            new Gson().toJson(ids, new FileWriter(FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
