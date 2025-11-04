package me.wilhelm.api.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Chat {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendErrorMessage(Player player, String message) {
        player.sendMessage(color("&c&lERROR &r&c" + message));
    }

    public static String getErrorMessage(String message) { return color("&c&lERROR &r&c" + message); }

    public static void sendInfoMessage(Player player, String message) {
        player.sendMessage(color("&f&lINFO &r&f" + message));
    }

    public static String getInfoMessage(String message) { return color("&f&lINFO &r&f" + message); }
}
