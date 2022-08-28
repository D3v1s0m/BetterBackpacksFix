package me.d3v1s0m.betterbackpacksfix.listeners;

import com.alonsoaliaga.betterbackpacks.api.events.BackpackOpenEvent;
import me.d3v1s0m.betterbackpacksfix.configs.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class BackpackOpenList implements Listener {

    private final String bypassDisabledPerm = Configuration.get().getString("permissions.bypassdisabled");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBackpackOpenEvent(BackpackOpenEvent event) {
        if (Configuration.get().getBoolean("disable-backpacks")) {
            if (!event.getPlayer().hasPermission(bypassDisabledPerm != null ? bypassDisabledPerm : "betterbackpacksfix.bypassdisabled")) {
                event.getPlayer().sendMessage(ChatColor.RED + "Backpacks are disabled.");
                event.setCancelled(true);
            }
        }
    }
}
