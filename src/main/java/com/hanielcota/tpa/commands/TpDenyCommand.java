package com.hanielcota.tpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.hanielcota.tpa.TpaPlugin;
import com.hanielcota.tpa.manager.TpaRequest;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpadeny")
@AllArgsConstructor
public class TpDenyCommand extends BaseCommand {

    private final TpaPlugin plugin;

    @Default
    @CommandCompletion("@players")
    public void onTpDeny(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage("§cUso correto: /tpadeny <jogador>");
            return;
        }

        String senderName = args[0];
        TpaRequest request = plugin.getTpaManager().getAndRemoveRequest(player.getName());
        if (request == null || !request.getSender().equals(senderName)) {
            player.sendMessage("§cNão há pedido de TPA de " + senderName + " para negar.");
            return;
        }

        Player senderPlayer = Bukkit.getPlayerExact(senderName);
        if (senderPlayer == null) {
            player.sendMessage("§cVocê negou o pedido de TPA de " + senderName + ", mas " + senderName + " não está mais online.");
            return;
        }

        senderPlayer.sendMessage("§cSeu pedido de TPA para " + player.getName() + " foi negado.");
        player.sendMessage("§cVocê negou o pedido de TPA de " + senderName + ".");
    }
}
