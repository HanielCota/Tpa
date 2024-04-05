package com.hanielcota.tpa.menu;

import com.hanielcota.tpa.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFactory {

    private static final String ACCEPT_TEXTURE_URL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTU3ZjEwODIzN2RlNGJmZTRkZjBkYmJhMjBkOGRkOTQ3YTgyNTNmYWRjOThkYzhlNDVlM2JlZWFiMmJhYWUifX19";
    private static final String DENY_TEXTURE_URL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2E1MGJiYzU0MTMwMzVkNDg3ZjNkNjYyM2E1OTE5MzBlODQyYzU2MjQxZjc2NzM4ZDhiZTFmZWEyZGMyY2QyZiJ9fX0=";
    private static final String UNKNOWN_TEXTURE_URL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWI5YzQ1ZDZjN2NkMDExNjQzNmMzMWVkNGQ4ZGM4MjVkZTAzZTgwNmVkYjY0ZTlhNjdmNTQwYjhhYWFlODUifX19";

    public ItemStack createAcceptItem() {
        return new ItemBuilder(ACCEPT_TEXTURE_URL)
                .setName("§aAceitar")
                .build();
    }

    public ItemStack createDenyItem() {
        return new ItemBuilder(DENY_TEXTURE_URL)
                .setName("§cNegar")
                .build();
    }

    public ItemStack createInfoHead(Player player) {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName("§aInformações do TPA")
                .setLore(
                        "§7Aqui você fica por dentro de quantos TPA.",
                        "§7você recebeu e quantos você enviou além dos expirados.",
                        "",
                        "§7Recebeu: §a0",
                        "§7Enviou: §a0",
                        "§7Expirados §a0",
                        "",
                        "§7Sua reputação: §e★★★★★",
                        "§7Você é um jogador confiável."
                )
                .setSkullOwner(player.getName())
                .build();
    }

    public ItemStack createUnknownItem() {
        return new ItemBuilder(UNKNOWN_TEXTURE_URL)
                .setName("§eEscolha sua Preferência")
                .setLore(
                        "§7Alterar suas configurações de TPA.",
                        "§7Clique para ativar ou desativar o recebimento de TPA.")
                .build();
    }

    public ItemStack createVIPDelay() {
        return new ItemBuilder(Material.CLOCK)
                .setName("§aTempo de Espera")
                .setLore(
                        "§7Clique para alterar o tempo de espera",
                        "§7para receber um pedido de TPA.")
                .build();

    }

    public ItemStack createBackItem() {
        return new ItemBuilder(Material.ARROW)
                .setName("§cVoltar")
                .setLore("§7Clique para retornar ao menu anterior.")
                .build();
    }
}