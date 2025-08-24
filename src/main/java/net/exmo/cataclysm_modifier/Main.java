package net.exmo.cataclysm_modifier;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // 新增JSON文件处理方法调用
        processJsonFiles("G:\\exmo\\CataclysmModifier\\run\\config\\exmo\\modifier");
    }

    // 新增JSON文件处理方法
    public static void processJsonFiles(String dirPath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File directory = new File(dirPath);
        
        // 新增集合文件路径
        File combinedFile = new File(directory, "combined_loc.json");
        JsonObject combinedRoot = new JsonObject();
        
        // 遍历目录下所有json文件
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null) return;

        for (File file : files) {
            try (Reader reader = new FileReader(file)) {
                JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
                JsonObject newRoot = new JsonObject();

                // 遍历原始JSON条目
                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                    if (entry.getValue().isJsonObject()) {
                        JsonObject valueObj = entry.getValue().getAsJsonObject().deepCopy();
                        if (valueObj.has("localDescription")) {
                            String newKey = entry.getKey()+"_loc";
                            String description = valueObj.get("localDescription").getAsString();
                            
                            valueObj.addProperty("localDescription", newKey);
                            
                            // 添加到集合文件内容
                            combinedRoot.addProperty(newKey, description);
                        }
                        newRoot.add(entry.getKey(), valueObj);
                    }
                }

                // 写入修改后的原文件内容
                try (Writer writer = new FileWriter(file)) {
                    gson.toJson(newRoot, writer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 写入集合文件
        try (Writer combinedWriter = new FileWriter(combinedFile)) {
            gson.toJson(combinedRoot, combinedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}