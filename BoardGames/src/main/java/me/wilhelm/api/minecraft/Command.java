package me.wilhelm.api.minecraft;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public class Command {

    public static void registerCommand(PluginCommand command, CommandExecutor executor, TabCompleter tabCompleter) {
        if (command == null) return;

        command.setExecutor(executor);
        command.setTabCompleter(tabCompleter);
    }

}
