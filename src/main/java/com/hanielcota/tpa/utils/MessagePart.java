package com.hanielcota.tpa.utils;


import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;

@Getter
public class MessagePart {

    private Component component;

    public MessagePart(Component component) {
        this.component = component;
    }

    public MessagePart(String text) {
        this(Component.text(text));
    }

    public MessagePart() {
        this(Component.empty());
    }

    public void text(String text) {
        component = Component.text(text);
    }

    public void hover(String message) {
        component = component.hoverEvent(HoverEvent.showText(Component.text(message)));
    }

    public void click(ClickEvent.Action action, String value) {
        component = component.clickEvent(ClickEvent.clickEvent(action, value));
    }

    public void command(String value) {
        click(ClickEvent.Action.RUN_COMMAND, value);
    }

    public void extra(Component component) {
        this.component = this.component.append(component);
    }
}