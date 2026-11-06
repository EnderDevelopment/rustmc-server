package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ThirstManager thirstManager;

    @Override
    public void onEnable() {
        thirstManager = new ThirstManager(this);
        Bukkit.getPluginManager().registerEvents(new SurvivalListener(thirstManager), this);

        RustCommand rustCommand = new RustCommand(thirstManager);
        if (getCommand("rustmc") != null) {
            getCommand("rustmc").setExecutor(rustCommand);
            getCommand("rustmc").setTabCompleter(rustCommand);
        }
        if (getCommand("thirst") != null) {
            getCommand("thirst").setExecutor(rustCommand);
        }

        thirstManager.start();
        getLogger().info("RustMC survival mode enabled for Minecraft 1.21.11.");
    }

    @Override
    public void onDisable() {
        if (thirstManager != null) {
            thirstManager.stop();
        }
        getLogger().info("RustMC survival mode disabled.");
    }
}
