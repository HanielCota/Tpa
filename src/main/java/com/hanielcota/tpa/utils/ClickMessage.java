package com.hanielcota.tpa.utils;


import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ClickMessage {

    private final List<MessagePart> parts = new ArrayList<>();

    public ClickMessage(String text) {
        parts.add(new MessagePart(text));
    }

    public ClickMessage command(String command) {
        latest().command(command);
        return this;
    }

    public ClickMessage click(ClickEvent.Action action, String value) {
        latest().click(action, value);
        return this;
    }

    public ClickMessage tooltip(String hover) {
        latest().hover(hover);
        return this;
    }

    private MessagePart latest() {
        if (!parts.isEmpty()) {
            return parts.get(parts.size() - 1);
        }

        MessagePart messagePart = new MessagePart();
        parts.add(messagePart);
        return messagePart;
    }

    private MessagePart first() {
        return parts.get(0);
    }

    private ClickMessage then(MessagePart messagePart) {
        parts.add(messagePart);
        return this;
    }

    public ClickMessage then(String text) {
        return then(new MessagePart(text));
    }

    public ClickMessage then() {
        return then(new MessagePart());
    }

    public void send(Player player) {
        int size = parts.size();
        MessagePart first = first();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                first.extra(parts.get(i).getComponent());
            }
        }

        player.sendMessage(first.getComponent());
    }
}