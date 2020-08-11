package org.sotap.CustomDeathMessage.Utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.sotap.CustomDeathMessage.CustomDeathMessage;

public final class Files {
    public static String cwd;
    public static FileConfiguration config;
    public static FileConfiguration messages;
    
    public static File getFile(File folder, String name) {
        File file = new File(folder, name);
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static FileConfiguration load(String path, String name) {
        return YamlConfiguration.loadConfiguration(getFile(
                new File(path.replace(path.length() == 1 ? "." : "./", path.length() == 1 ? cwd : cwd + "/")), name));
    }

    
    public static void save(FileConfiguration data, String targetFile) {
        try {
            data.save(targetFile.replace("./", cwd + "/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void init(CustomDeathMessage plugin) {
        plugin.reloadConfig();
        cwd = plugin.getDataFolder().getPath();
        config = plugin.getConfig();
        messages = load(".", "messages.yml");
    }
}