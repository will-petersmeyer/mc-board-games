package me.wilhelm.api.minecraft;

import org.bukkit.ChatColor;

public class Chat {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
