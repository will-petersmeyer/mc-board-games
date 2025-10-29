package me.wilhelm.boardgames;

import me.wilhelm.boardgames.games.Game;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class BoardGames extends JavaPlugin {

    private static BoardGames instance;
    public static BoardGames getInstance() { return instance; }

    private HashMap<Integer, Game> gameList = new HashMap<>();
    public int addGame(Game game) {
        int maxID = gameList.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 1; i < maxID; i++) {
            if (!gameList.containsKey(i)) {
                gameList.put(i, game);
                return i;
            }
        }
        gameList.put(maxID+1, game);
        return maxID+1;
    }

    @Override
    public void onEnable() {
        instance = this;

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
}
