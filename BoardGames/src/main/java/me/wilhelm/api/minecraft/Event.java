package me.wilhelm.api.minecraft;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Event {

    public static void registerEvent(JavaPlugin plugin, Listener event) {
        plugin.getServer().getPluginManager().registerEvents(event, plugin);
    }

}
