package com.hanielcota.tpa.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class MenuBuilder implements Listener {

    private final Inventory inventory;
    private final Map<Integer, Consumer<Player>> slotActions = new HashMap<>();
    private static final Map<UUID, WeakReference<MenuBuilder>> openInventories = new HashMap<>();

    public MenuBuilder(JavaPlugin plugin, Component title, int size) {
        this.inventory = Bukkit.createInventory(null, size, title);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public MenuBuilder addItem(int slot, ItemStack item, Consumer<Player> action) {
        inventory.setItem(slot, item);
        if (action != null) {
            slotActions.put(slot, action);
        }
        return this;
    }

    public void open(Player player) {
        player.openInventory(inventory);
        openInventories.put(player.getUniqueId(), new WeakReference<>(this));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (event.getClick() == ClickType.DROP || event.getClick() == ClickType.CONTROL_DROP || event.getClickedInventory() == null) {
            event.setCancelled(true);
            return;
        }

        WeakReference<MenuBuilder> ref = openInventories.get(player.getUniqueId());
        MenuBuilder openMenu = ref != null ? ref.get() : null;
        if (openMenu == null || !event.getClickedInventory().equals(openMenu.inventory))
            return;

        event.setCancelled(true);
        Consumer<Player> action = openMenu.slotActions.get(event.getSlot());

        if (action != null) {
            action.accept(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        WeakReference<MenuBuilder> ref = openInventories.get(player.getUniqueId());
        MenuBuilder openMenu = ref != null ? ref.get() : null;
        if (openMenu != null && event.getInventory().equals(openMenu.inventory)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            openInventories.remove(player.getUniqueId());
        }
    }
}
