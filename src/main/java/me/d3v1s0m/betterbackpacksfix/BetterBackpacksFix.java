package me.d3v1s0m.betterbackpacksfix;


import me.d3v1s0m.betterbackpacksfix.commands.MainCommand;
import me.d3v1s0m.betterbackpacksfix.configs.Configuration;
import me.d3v1s0m.betterbackpacksfix.configs.Defaults;
import me.d3v1s0m.betterbackpacksfix.listeners.BackpackOpenList;
import me.d3v1s0m.betterbackpacksfix.listeners.InventoryList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class BetterBackpacksFix extends JavaPlugin {

    public static BetterBackpacksFix plugin;

    public static List<String> defaultAliases = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        if (getServer().getPluginManager().getPlugin("BetterBackpacks") == null) {
            getLogger().severe("BetterBackpacks not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getConsoleSender().sendMessage("Enabling BetterBackpacksFix ...");
        defaultAliases.add("betterbackpackfix");
        defaultAliases.add("bbackpacksfix");
        defaultAliases.add("bbackpackfix");
        defaultAliases.add("bbpf");
        Configuration.setup();
        setDefaults();
        Configuration.save();

        List<String> aliases = Configuration.get().getStringList("aliases").stream().map((s) -> s.replace(" ", "").trim()).filter((s) -> !s.isEmpty()).collect(Collectors.toList());

        new MainCommand(plugin, "betterbackpacksfix", "/", "The main command", "&6You do not have permission to run this command.", aliases);
        getServer().getPluginManager().registerEvents(new BackpackOpenList(), this);
        getServer().getPluginManager().registerEvents(new InventoryList(plugin), this);
        Bukkit.getConsoleSender().sendMessage("BetterBackpacksFix enabled!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Configuration.save();
    }

    public static BetterBackpacksFix getInstance() {
        return plugin;
    }

    private void setDefaults() {
        Configuration.get().options().setHeader(Collections.singletonList("BetterBackpacksFix by d3v1s0m"));
        Defaults.setupValues();
        Defaults.setupComments();
        Configuration.get().options().copyDefaults(true);
    }
}
