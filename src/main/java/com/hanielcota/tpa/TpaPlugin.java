package com.hanielcota.tpa;

import co.aikar.commands.PaperCommandManager;
import com.hanielcota.tpa.commands.TpAcceptCommand;
import com.hanielcota.tpa.commands.TpDenyCommand;
import com.hanielcota.tpa.commands.TpaCommand;
import com.hanielcota.tpa.manager.TpaManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TpaPlugin extends JavaPlugin {

    private TpaManager tpaManager;

    @Override
    public void onEnable() {
        getLogger().info("TpaPlugin has been enabled");
        initializeManagers();
        registerCommands();
    }

    @Override
    public void onDisable() {
        getLogger().info("TpaPlugin has been disabled");
    }

    private void initializeManagers() {
        tpaManager = new TpaManager(this);
    }

    private void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new TpaCommand(this));
        manager.registerCommand(new TpDenyCommand(this));
        manager.registerCommand(new TpAcceptCommand(this));
    }
}
