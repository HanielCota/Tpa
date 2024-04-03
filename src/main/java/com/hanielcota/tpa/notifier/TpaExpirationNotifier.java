package com.hanielcota.tpa.notifier;

import com.hanielcota.tpa.manager.TpaManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class TpaExpirationNotifier {

    private final JavaPlugin plugin;
    private final TpaManager tpaManager;

    public void scheduleExpirationNotification(String targetPlayerName, String senderName) {
        if (targetPlayerName == null || senderName == null) {
            return;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (tpaManager.getPendingRequests().getIfPresent(targetPlayerName) == null) {
                    return;
                }

                tpaManager.getPendingRequests().invalidate(targetPlayerName);
                Player target = Bukkit.getPlayerExact(targetPlayerName);

                if (target == null) {
                    return;
                }

                target.sendMessage(String.format("§eSeu pedido de TPA enviado para %s expirou.", senderName));
            }
        }.runTaskLater(plugin, 20 * 30);
    }
}
