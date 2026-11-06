package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ThirstManager {
    public static final int MAX_THIRST = 100;
    private static final int PASSIVE_LOSS = 5;
    private static final long TICK_INTERVAL = 1200L;
    private final JavaPlugin plugin;
    private final NamespacedKey thirstKey;
    private BukkitTask task;

    public ThirstManager(JavaPlugin plugin) {
        this.plugin = plugin;
        thirstKey = new NamespacedKey(plugin, "thirst");
    }

    public void start() {
        task = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, TICK_INTERVAL, TICK_INTERVAL);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public int getThirst(Player player) {
        Integer value = player.getPersistentDataContainer().get(thirstKey, PersistentDataType.INTEGER);
        if (value == null) {
            setThirst(player, MAX_THIRST);
            return MAX_THIRST;
        }
        return Math.max(0, Math.min(MAX_THIRST, value));
    }

    public void setThirst(Player player, int value) {
        int clamped = Math.max(0, Math.min(MAX_THIRST, value));
        player.getPersistentDataContainer().set(thirstKey, PersistentDataType.INTEGER, clamped);
    }

    public void restore(Player player, int amount) {
        int before = getThirst(player);
        setThirst(player, before + amount);
        int after = getThirst(player);
        player.sendActionBar(Component.text("Thirst restored: " + after + "/" + MAX_THIRST, NamedTextColor.AQUA));
    }

    public void sendStatus(Player player) {
        int thirst = getThirst(player);
        NamedTextColor color = thirst <= 20 ? NamedTextColor.RED : thirst <= 45 ? NamedTextColor.YELLOW : NamedTextColor.AQUA;
        player.sendActionBar(Component.text("Thirst: " + thirst + "/" + MAX_THIRST + "  |  Food: " + player.getFoodLevel() + "/20", color));
    }

    private void tick() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            int current = getThirst(player);
            int next = Math.max(0, current - PASSIVE_LOSS);
            setThirst(player, next);
            sendStatus(player);
            if (next <= 20) {
                player.sendMessage(Component.text("Your thirst is critical. Drink a water bottle to recover.", NamedTextColor.RED));
            }
            if (next == 0) {
                player.damage(1.0);
                player.sendMessage(Component.text("You are dehydrated and losing health.", NamedTextColor.DARK_RED));
            }
        }
    }
}
