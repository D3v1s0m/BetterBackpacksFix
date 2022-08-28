package me.d3v1s0m.betterbackpacksfix.listeners;

import com.alonsoaliaga.betterbackpacks.others.NbtTag;
import de.tr7zw.nbtapi.NBTItem;
import me.d3v1s0m.betterbackpacksfix.BetterBackpacksFix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class InventoryList implements Listener {

    private final BetterBackpacksFix plugin;

    public InventoryList(BetterBackpacksFix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.SHULKER_BOX)) return;
        if (checkSlot(event.getRawSlots())) return;
        ItemStack item = event.getOldCursor();
        if (item.getType().equals(Material.AIR)) return;
        if (!isBackpack(item)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (!event.getInventory().getType().equals(InventoryType.SHULKER_BOX)) return;
        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                ItemStack item = event.getCurrentItem();
                if (item == null || item.getType().equals(Material.AIR)) return;
                if (!isBackpack(item)) return;
                event.setCancelled(true);;
            }
        }
        if (event.getClickedInventory().getType().equals(InventoryType.SHULKER_BOX)) {
            ItemStack item = event.getCursor();
            if (item == null || item.getType().equals(Material.AIR)) return;
            if (!isBackpack(item)) return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClickEvent2(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        Inventory destInv = event.getView().getTopInventory();
        if (destInv.getType() != InventoryType.SHULKER_BOX) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack item = null;
        if (((InventoryAction.HOTBAR_SWAP.equals(event.getAction()) ||
                InventoryAction.HOTBAR_MOVE_AND_READD.equals(event.getAction()))) &&
                event.getHotbarButton() >= 0 && event.getHotbarButton() <= 8) {
            item = player.getInventory().getItem(event.getHotbarButton());
        } else if (InventoryAction.MOVE_TO_OTHER_INVENTORY.equals(event.getAction()) && event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            item = player.getInventory().getItem(event.getRawSlot());
        }
        if (item == null || item.getType().equals(Material.AIR)) return;
        if (event.getRawSlot() > 26) return;
        if (!isBackpack(item)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
        if (isBackpack(event.getItem()) && event.getDestination().getType().equals(InventoryType.SHULKER_BOX)) {
            ItemStack itemStack = event.getItem();
            event.setCancelled(true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (event.getSource().getLocation() != null) {
                    Block block = event.getSource().getLocation().getBlock();
                    block.breakNaturally();
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[BBPF]" + ChatColor.WHITE + " Broke hopper at world: " + block.getWorld().getName() + " x: " + block.getX() + " y: " + block.getY() + " z: " + block.getZ());
                }
            }, 5L);
        }
    }

    public boolean isBackpack(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getKeys().contains(NbtTag.BACKPACK);
    }

    public boolean checkSlot(Set<Integer> slots) {
        int min = 62;
        for (int slot : slots) {
            if (slot < min) min = slot;
        }
        return min > 26;
    }

}
