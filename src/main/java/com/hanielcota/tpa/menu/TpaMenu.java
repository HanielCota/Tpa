package com.hanielcota.tpa.menu;

import com.hanielcota.tpa.utils.MenuBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TpaMenu {

    private static final int ACCEPT_SLOT = 21;
    private static final int DENY_SLOT = 23;
    private static final int UNKNOWN_SLOT = 22;
    private static final int BACK_SLOT = 36;
    private static final int INFO_SLOT = 4;
    private static final int VIP_DELAY_SLOT = 20;
    private final ItemFactory itemFactory = new ItemFactory();

    public void openCustomMenu(Player player, JavaPlugin plugin) {
        ItemStack acceptItem = itemFactory.createAcceptItem();
        ItemStack denyItem = itemFactory.createDenyItem();
        ItemStack unknownItem = itemFactory.createUnknownItem();
        ItemStack backItem = itemFactory.createBackItem();
        ItemStack infoItem = itemFactory.createInfoHead(player);
        ItemStack vipDelayItem = itemFactory.createVIPDelay();


        new MenuBuilder(plugin, Component.text("Configurações do TPA"), 5 * 9)
                .addItem(ACCEPT_SLOT, acceptItem, p -> {
                    p.sendMessage("§aVocê aceitou o pedido de teleporte.");
                    p.closeInventory();
                })
                .addItem(DENY_SLOT, denyItem, p -> {
                    p.sendMessage("§cVocê recusou o pedido de teleporte.");
                    p.closeInventory();
                })
                .addItem(UNKNOWN_SLOT, unknownItem, p -> {
                    p.sendMessage("§eVocê abriu as configurações do TPA.");
                    p.closeInventory();
                })
                .addItem(BACK_SLOT, backItem, p -> {
                    p.sendMessage("§cVocê fechou as configurações do TPA.");
                    p.closeInventory();
                })
                .addItem(INFO_SLOT, infoItem, p -> {
                    p.sendMessage("§aVocê abriu as informações do TPA.");
                    p.closeInventory();
                })
                .addItem(VIP_DELAY_SLOT, vipDelayItem, p -> {
                    p.sendMessage("§aVocê abriu as configurações de tempo de espera VIP.");
                    p.closeInventory();
                })
                .open(player);
    }
}