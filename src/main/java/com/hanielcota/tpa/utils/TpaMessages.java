package com.hanielcota.tpa.utils;

import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class TpaMessages {

    public static final String REQUEST_ALREADY_EXISTS = "§cJá existe um pedido de TPA pendente para esse jogador.";

    public static void sendTpaRequestMessages(Player target, String senderName) {
        Component message = Component.text("Você recebeu uma solicitação de TPA de ", NamedTextColor.YELLOW)
                .append(Component.text(senderName, NamedTextColor.YELLOW))
                .append(Component.text("."))
                .append(Component.newline())
                .append(Component.text("Clique ", NamedTextColor.YELLOW))
                .append(Component.text("AQUI", NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .clickEvent(ClickEvent.runCommand("/tpaaceitar " + senderName))
                        .hoverEvent(HoverEvent.showText(Component.text("Clique para aceitar.", NamedTextColor.GREEN))))
                .append(Component.text(" para aceitar ou ", NamedTextColor.YELLOW))
                .append(Component.text("AQUI", NamedTextColor.RED)
                        .decorate(TextDecoration.BOLD)
                        .clickEvent(ClickEvent.runCommand("/tpadeny " + senderName))
                        .hoverEvent(HoverEvent.showText(Component.text("Clique para negar.", NamedTextColor.RED))))
                .append(Component.text(" para negar.", NamedTextColor.YELLOW));

        target.sendMessage(message);
    }

    public static String cooldownMessage(long timeLeft) {
        return String.format("§cVocê está em cooldown. Por favor, aguarde %d segundos antes de enviar outro pedido.", timeLeft / 1000);
    }
}
