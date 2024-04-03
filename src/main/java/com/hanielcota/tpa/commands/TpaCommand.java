package com.hanielcota.tpa.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.Default;
import com.hanielcota.tpa.TpaPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("tpa")
@AllArgsConstructor
public class TpaCommand extends BaseCommand {

    private TpaPlugin plugin;

    @Default
    @CommandCompletion("@players")
    public void onTpa(Player sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUso correto: /tpa <jogador>");
            return;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);
        if (target == null) {
            sender.sendMessage("§cO jogador especificado não está online ou não existe.");
            return;
        }

        if (sender.equals(target)) {
            sender.sendMessage("§cVocê não pode enviar um pedido de TPA para si mesmo.");
            return;
        }

        if (plugin.getTpaManager().hasRequest(target.getName())) {
            sender.sendMessage("§cJá existe um pedido de TPA pendente para " + targetName + ".");
            return;
        }

        if (plugin.getTpaManager().isInCooldown(sender.getName())) {
            long cooldownTimeLeft = plugin.getTpaManager().getCooldownTimeLeft(sender.getName());
            sender.sendMessage(String.format("§cVocê está em cooldown. Por favor, aguarde mais %d segundos.", cooldownTimeLeft));
            return;
        }

        plugin.getTpaManager().sendTpaRequest(sender, target);
        sender.sendMessage("§aPedido de TPA enviado para " + targetName + ".");
    }
}
