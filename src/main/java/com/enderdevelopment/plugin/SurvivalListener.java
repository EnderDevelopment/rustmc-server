package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public final class SurvivalListener implements Listener {
    private final ThirstManager thirstManager;

    public SurvivalListener(ThirstManager thirstManager) {
        this.thirstManager = thirstManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        thirstManager.getThirst(event.getPlayer());
        event.getPlayer().sendMessage(Component.text("Welcome to RustMC survival.", NamedTextColor.GOLD));
        event.getPlayer().sendMessage(Component.text("Food and thirst matter. Drink water bottles to stay hydrated.", NamedTextColor.GRAY));
        event.getPlayer().sendMessage(Component.text("Use /rustmc for mode information and /thirst for your status.", NamedTextColor.AQUA));
        thirstManager.sendStatus(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.getPlayer().getServer().getScheduler().runTaskLater(
                event.getPlayer().getServer().getPluginManager().getPlugin("RustMC"),
                () -> thirstManager.sendStatus(event.getPlayer()), 1L);
    }

    @EventHandler
    public void onWaterDrink(PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() != Material.POTION || !(item.getItemMeta() instanceof PotionMeta meta)) {
            return;
        }
        if (meta.getBasePotionType() == PotionType.WATER) {
            thirstManager.restore(event.getPlayer(), 35);
        }
    }
}
