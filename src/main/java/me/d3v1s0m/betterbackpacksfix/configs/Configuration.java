package me.d3v1s0m.betterbackpacksfix.configs;

import me.d3v1s0m.betterbackpacksfix.BetterBackpacksFix;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configuration {
    private static File file;
    private static FileConfiguration customFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(BetterBackpacksFix.getInstance().getDataFolder(), "config.yml");

        if (!file.getParentFile().exists()) {
            Bukkit.getLogger().info("BetterBackpacksFix folder not found! Generating...");
            file.getParentFile().mkdir();
        }
        if (!file.exists()){
            Bukkit.getLogger().info("config.yml not found! Generating...");
            try{
                file.createNewFile();
            }catch (IOException e){
                Bukkit.getLogger().severe("Could not create config.yml!");
            }
        }

        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return customFile;
    }

    public static void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save config.yml!");
        }
    }

    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
