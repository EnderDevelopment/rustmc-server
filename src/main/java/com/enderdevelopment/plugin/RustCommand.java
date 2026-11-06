package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public final class RustCommand implements CommandExecutor, TabCompleter {
    private final ThirstManager thirstManager;

    public RustCommand(ThirstManager thirstManager) {
        this.thirstManager = thirstManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("thirst")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(Component.text("This command can only be used by a player.", NamedTextColor.RED));
                return true;
            }
            int thirst = thirstManager.getThirst(player);
            NamedTextColor color = thirst <= 20 ? NamedTextColor.RED : thirst <= 45 ? NamedTextColor.YELLOW : NamedTextColor.AQUA;
            player.sendMessage(Component.text("RustMC thirst: " + thirst + "/100", color));
            player.sendMessage(Component.text("Drink a water bottle to restore 35 thirst.", NamedTextColor.GRAY));
            return true;
        }

        sender.sendMessage(Component.text("=== RustMC Survival ===", NamedTextColor.GOLD));
        sender.sendMessage(Component.text("Server: RustMC", NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("Minecraft: 1.21.11", NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("Mode: Rust-inspired survival", NamedTextColor.GREEN));
        sender.sendMessage(Component.text("Survive with food and water. Thirst decreases over time.", NamedTextColor.GRAY));
        sender.sendMessage(Component.text("/thirst - check your thirst and recovery guide", NamedTextColor.AQUA));
        sender.sendMessage(Component.text("Drink a water bottle to restore thirst.", NamedTextColor.AQUA));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
