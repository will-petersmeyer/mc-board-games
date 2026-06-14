package me.wilhelm.boardgames;

import me.wilhelm.boardgames.commands.bgames.BGamesCmd;
import me.wilhelm.boardgames.commands.bgames.BGamesTC;
import me.wilhelm.boardgames.games.Game;
import me.wilhelm.boardgames.games.uno.UnoListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

import static me.wilhelm.api.minecraft.Command.registerCommand;
import static me.wilhelm.api.minecraft.Event.registerEvent;

public final class BoardGames extends JavaPlugin {

    private static BoardGames instance;
    public static BoardGames getInstance() { return instance; }


    private HashMap<Integer, Game> gameList = new HashMap<>();
    /**
     * Adds a game to global HashMap containing all active games.
     *
     * @param game The game object to be added.
     **/
    public int addGame(Game game) {

        int maxID = gameList.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 1; i < maxID; i++) {
            if (!gameList.containsKey(i)) { gameList.put(i, game); return i; }
        }

        gameList.put(maxID+1, game);
        return maxID+1;
    }

    /**
     * Finds a game by its game-id.
     *
     * @param id The id game to be added.
     **/
    public Game getGame(int id) { return gameList.get(id); }

    /**
     * Returns a set containing all game-ids.
     *
     **/
    public Set<Integer> getGameIDs() { return gameList.keySet(); }

    /**
     * Removes a game from global HashMap containing all active games.
     *
     * @param id The game to be removed.
     **/
    public void removeGame(int id) { gameList.remove(id); }

    public void reload() {
    }

    @Override
    public void onEnable() {
        instance = this;

        registerEvents();
        registerCommands();

        log("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
    * Logs a message in the console.
    *
    * @param message A message to be printed in the console (Preceded by a plugin tag)
    **/
    public static void log(String message) {
        instance.getLogger().info("[Board Games] " + message);
    }

    /**
     * Logs an error message in the console.
     *
     * @param message An error message to be printed in the console (Preceded by a plugin tag)
     **/
    public static void logError(String message) {
        instance.getLogger().info("[Board Games] |ERROR| " + message);
    }

    private void registerCommands() { registerCommand(getCommand("BoardGames"), new BGamesCmd(), new BGamesTC()); }

    private void registerEvents() { registerEvent(instance, new UnoListener()); }
}
