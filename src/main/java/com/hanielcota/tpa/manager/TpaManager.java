package com.hanielcota.tpa.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hanielcota.tpa.TpaPlugin;
import com.hanielcota.tpa.notifier.TpaExpirationNotifier;
import com.hanielcota.tpa.utils.ClickMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class TpaManager {

    private final Cache<String, TpaRequest> pendingRequests;
    private final Cache<String, Long> cooldowns;
    private final TpaExpirationNotifier expirationNotifier;
    private final TpaPlugin plugin;

    public TpaManager(TpaPlugin plugin) {
        pendingRequests = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
        cooldowns = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
        this.plugin = plugin;
        expirationNotifier = new TpaExpirationNotifier(plugin, this);
    }

    public void sendTpaRequest(Player sender, Player target) {
        if (isInCooldown(sender.getName())) {
            long timeLeft = getCooldownTimeLeft(sender.getName());
            sender.sendMessage(String.format(
                    "§cVocê está em cooldown. Por favor, aguarde %d segundos antes de enviar outro pedido.",
                    timeLeft / 1000));
            return;
        }

        pendingRequests.put(target.getName(), new TpaRequest(sender.getName()));
        cooldowns.put(sender.getName(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));

        sendTpaMessages(target, sender.getName());
        expirationNotifier.scheduleExpirationNotification(target.getName(), sender.getName());
    }

    public TpaRequest getAndRemoveRequest(String targetName) {
        return pendingRequests.asMap().remove(targetName);
    }

    public boolean hasRequest(String targetName) {
        return pendingRequests.getIfPresent(targetName) != null;
    }

    public boolean isInCooldown(String playerName) {
        Long cooldownEnd = cooldowns.getIfPresent(playerName);
        return cooldownEnd != null && cooldownEnd > System.currentTimeMillis();
    }

    public long getCooldownTimeLeft(String playerName) {
        Long cooldownEnd = cooldowns.getIfPresent(playerName);
        if (cooldownEnd == null) return 0;
        return (cooldownEnd - System.currentTimeMillis()) / 1000;
    }

    private void sendTpaMessages(Player target, String senderName) {
        target.sendMessage("§eVocê recebeu uma solicitação de TPA de " + senderName + ".");

        new ClickMessage("§aClique §lAQUI §apara poder aceitar a solicitação.")
                .click(ClickEvent.Action.RUN_COMMAND, "/tpaaceitar " + senderName)
                .send(target);

        new ClickMessage("§cClique §lAQUI §cpara poder negar a solicitação.")
                .click(ClickEvent.Action.RUN_COMMAND, "/tpadeny " + senderName)
                .send(target);
    }
}
