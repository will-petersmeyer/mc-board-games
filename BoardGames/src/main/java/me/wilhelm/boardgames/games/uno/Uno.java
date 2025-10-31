package me.wilhelm.boardgames.games.uno;

import me.wilhelm.api.algorithms.HashMapHelper;
import me.wilhelm.api.minecraft.Chat;
import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

public class Uno implements Game {
    public static String label = "UNO";

    private boolean running = false;
    private HashMap<Player, Boolean> players = new HashMap<>();
    private final int GAMEID;
    private final Player HOST;

    public Uno(Player[] playerList, Player host) {
        GAMEID = BoardGames.getInstance().addGame(this);
        HOST = host;

        for (Player player : playerList) {
            if (player.isOnline())
                players.put(player, true);
            else
                players.put(player, false);
        }

        announce("&f[&6&l!&f] &fYou have been added to a game of UNO. Please wait for the host to start it. &8&o[UNO]");
    }

    @Override
    public boolean initialize() {
        if (running) {
            BoardGames.log("[Uno] Game has already been initialized!");
            return false;
        }

        if (players.size() < 3 || players.size() > 8) {
            BoardGames.log("[Uno] Player list size is invalid!");
            return false;
        }

        if (players.size() - HashMapHelper.countValues(players, false) <= players.size() / 2) {
            BoardGames.log("[Uno] Too many players are offline!");
            return false;
        }

        running = true;

        return true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean resume() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }

    @Override
    public boolean stop(Player winner) {
        return false;
    }

    @Override
    public int getGameID() {
        return GAMEID;
    }

    @Override
    public boolean hasPlayer(Player player) { return players.containsKey(player); }

    @Override
    public void setPlayerActive(Player player) { players.put(player, true); }

    @Override
    public void setPlayerInactive(Player player) { players.put(player, false); }

    @Override
    public void announce(String message) {
        for (Player player : players.keySet()) {
            if (player.isOnline()) {
                player.sendMessage(Chat.color(message));
            }
        }
    }

    public Player getHost() { return HOST; }
}
