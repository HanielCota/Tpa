package com.hanielcota.tpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.hanielcota.tpa.TpaPlugin;
import com.hanielcota.tpa.manager.TpaRequest;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("tpaaceitar")
@AllArgsConstructor
public class TpAcceptCommand extends BaseCommand {

    private final TpaPlugin plugin;

    @Default
    @CommandCompletion("@players")
    public void onTpAccept(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage("§cUso correto: /tpaaceitar <jogador>");
            return;
        }

        String senderName = args[0];
        TpaRequest request = plugin.getTpaManager().getAndRemoveRequest(player.getName());

        if (request == null || !request.getSender().equals(senderName)) {
            player.sendMessage("§cNão há pedido de TPA de " + senderName + " para aceitar.");
            return;
        }

        Player senderPlayer = Bukkit.getPlayerExact(senderName);
        if (senderPlayer == null) {
            player.sendMessage("§c" + senderName + " não está mais online.");
            return;
        }

        senderPlayer.teleport(player);

        player.sendMessage("§aVocê aceitou o pedido de TPA e " + senderName + " foi teleportado até você.");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);

        senderPlayer.sendMessage("§aSeu pedido de TPA foi aceito por " + player.getName() + ".");
        senderPlayer.playSound(senderPlayer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 10);
    }
}
