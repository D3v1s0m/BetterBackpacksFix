package me.d3v1s0m.betterbackpacksfix.commands;

import com.alonsoaliaga.betterbackpacks.BetterBackpacks;
import com.alonsoaliaga.betterbackpacks.commands.AbstractCommand;
import com.alonsoaliaga.betterbackpacks.utils.LocalUtils;
import me.d3v1s0m.betterbackpacksfix.BetterBackpacksFix;
import me.d3v1s0m.betterbackpacksfix.configs.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends AbstractCommand {

    private final BetterBackpacksFix plugin;

    private final String reloadPerm = Configuration.get().getString("permissions.reload");
    private final String disablePerm = Configuration.get().getString("permissions.disable");
    private final String enablePerm = Configuration.get().getString("permissions.enable");

    public MainCommand(BetterBackpacksFix plugin, String command, String usage, String description, String permissionMessage, List<String> aliases) {
        super(command, usage, description, permissionMessage, aliases);
        this.plugin = plugin;
        this.register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.hasPermission(reloadPerm != null ? reloadPerm : "betterbackpacksfix.reload")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                        return true;
                    }
                }
                Configuration.reload();
                sender.sendMessage(ChatColor.GOLD + "Reloaded config.");

            }
            if (args[0].equalsIgnoreCase("disable")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.hasPermission(disablePerm != null ? disablePerm : "betterbackpacksfix.disable")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                        return true;
                    }
                }
                if (Configuration.get().getBoolean("disable-backpacks")) {
                    sender.sendMessage(ChatColor.GREEN + "Backpacks are already disabled!");
                    return true;
                }
                Configuration.get().set("disable-backpacks", true);
                Configuration.save();
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, Configuration::reload, 40L);
                sender.sendMessage(ChatColor.GREEN + "Backpacks are now disabled!");

            }
            if (args[0].equalsIgnoreCase("enable")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!player.hasPermission(enablePerm != null ? enablePerm : "betterbackpacksfix.enable")) {
                        player.sendMessage(ChatColor.RED + "You don't have permission to do this!");
                        return true;
                    }
                }
                if (!Configuration.get().getBoolean("disable-backpacks")) {
                    sender.sendMessage(ChatColor.RED + "Backpacks are already enabled!");
                    return true;
                }
                Configuration.get().set("disable-backpacks", false);
                Configuration.save();
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, Configuration::reload, 40L);
                sender.sendMessage(ChatColor.RED + "Backpacks are now enabled!");

            }
            return true;
        }

        LocalUtils.send(sender, " ");
        LocalUtils.send(sender, "&2&lBetterBackpacksFix &aby &2&lD3v1s0m &aVersion &6" + this.plugin.getDescription().getVersion());
        if (sender.hasPermission(reloadPerm != null ? reloadPerm : "betterbackpacksfix.reload")) {
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.DARK_GREEN + "/betterbackpacksfix reload " + ChatColor.WHITE + "- " + ChatColor.GREEN + "Reloads the config.");
        }
        if (sender.hasPermission(disablePerm != null ? disablePerm : "betterbackpacksfix.disable")) {
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.DARK_GREEN + "/betterbackpacksfix disable " + ChatColor.WHITE + "- " + ChatColor.GREEN + "Disables the backpack feature.");
        }
        if (sender.hasPermission(enablePerm != null ? enablePerm : "betterbackpacksfix.enable")) {
            sender.sendMessage(" ");
            sender.sendMessage(ChatColor.DARK_GREEN + "/betterbackpacksfix enable " + ChatColor.WHITE + "- " + ChatColor.GREEN + "Enables the backpack feature.");
        }

        LocalUtils.send(sender, " ");
        if (sender instanceof Player) {
            Player target = (Player)sender;
            target.playSound(target.getLocation(), BetterBackpacks.getInstance().sounds.PICKUP, 1.0F, 1.0F);
        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length >= 1) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.hasPermission(reloadPerm != null ? reloadPerm : "betterbackpacksfix.reload")) {
                        completions.add("reload");
                    }
                    if (player.hasPermission(disablePerm != null ? disablePerm : "betterbackpacksfix.disable")) {
                        completions.add("disable");
                    }
                    if (player.hasPermission(enablePerm != null ? enablePerm : "betterbackpacksfix.enable")) {
                        completions.add("enable");
                    }
                }
                return completions;
            }
        }
        return null;
    }
}
