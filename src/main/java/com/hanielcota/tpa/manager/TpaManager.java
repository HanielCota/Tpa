package com.hanielcota.tpa.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hanielcota.tpa.TpaPlugin;
import com.hanielcota.tpa.notifier.TpaExpirationNotifier;
import com.hanielcota.tpa.utils.TpaMessages;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
public class TpaManager {

    private final Cache<String, TpaRequest> pendingRequests;
    private final Cache<String, Long> cooldowns;
    private final TpaExpirationNotifier expirationNotifier;
    private final TpaPlugin plugin;

    public TpaManager(TpaPlugin plugin) {
        this.plugin = plugin;
        this.pendingRequests = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
        this.cooldowns = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
        this.expirationNotifier = new TpaExpirationNotifier(plugin, this);
    }

    public void sendTpaRequest(Player sender, Player target) {
        String senderName = sender.getName();

        if (isInCooldown(senderName)) {
            long timeLeft = getCooldownTimeLeft(senderName);
            sender.sendMessage(TpaMessages.cooldownMessage(timeLeft));
            return;
        }

        if (pendingRequests.getIfPresent(target.getName()) != null) {
            sender.sendMessage(TpaMessages.REQUEST_ALREADY_EXISTS);
            return;
        }

        pendingRequests.put(target.getName(), new TpaRequest(senderName));
        cooldowns.put(senderName, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));

        TpaMessages.sendTpaRequestMessages(target, senderName);
        expirationNotifier.scheduleExpirationNotification(target.getName(), senderName);
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
}
