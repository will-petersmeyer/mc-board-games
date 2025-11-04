package me.wilhelm.boardgames.games.uno;

import me.wilhelm.api.algorithms.HashMapHelper;
import me.wilhelm.api.minecraft.Chat;
import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Uno implements Game {
    public static String label = "UNO";

    private boolean running = false;

    private HashMap<Player, Boolean> playersMap = new HashMap<>();
    private final Player[] players;

    private final int GAMEID;
    private final Player HOST;

    private int turn = 0;

    public Uno(Player[] playerList, Player host) {
        GAMEID = BoardGames.getInstance().addGame(this);
        players = playerList;
        HOST = host;

        for (Player player : playerList) {
            if (player.isOnline())
                playersMap.put(player, true);
            else
                playersMap.put(player, false);
        }

        announce("&f[&6&l!&f] &fYou have been added to a game of UNO. Please wait for the host to start it. &8&o[UNO]");
    }

    @Override
    public boolean initialize() {
        if (running) {
            BoardGames.log("[Uno] Game has already been initialized!");
            return false;
        }

        if (playersMap.size() < 3 || playersMap.size() > 8) {
            BoardGames.log("[Uno] Player list size is invalid!");
            return false;
        }

        if (playersMap.size() - HashMapHelper.countValues(playersMap, false) <= playersMap.size() / 2) {
            BoardGames.log("[Uno] Too many players are offline!");
            return false;
        }

        running = true;

        for (Player player : playersMap.keySet()) {

        }

        executeTurn();
        return true;
    }

    public void switchTurn() {
        if (!running)
            return;

        if (turn == playersMap.size()-1)
            turn = 0;
        else
            turn++;

        if (getCurrentPlayer().isOnline())
            switchTurn();

        executeTurn();
    }

    public void executeTurn() {


        switchTurn();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean pause() {
        running = false;

        return false;
    }

    @Override
    public boolean resume() {
        running = true;

        switchTurn();
        return false;
    }

    @Override
    public boolean stop() {

        announce("&7[&6&l!&7] &fYour game with id &f&n" + this.getGameID() + " &r&fhas been forcefully stopped. &8&o[UNO]");
        BoardGames.getInstance().removeGame(this.getGameID());
        return true;
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
    public boolean hasPlayer(Player player) { return playersMap.containsKey(player); }

    @Override
    public void setPlayerActive(Player player) { playersMap.put(player, true); }

    @Override
    public void setPlayerInactive(Player player) { playersMap.put(player, false); }

    public Player getCurrentPlayer() {
        return players[turn];
    }

    @Override
    public void announce(String message) {
        for (Player player : playersMap.keySet()) {
            if (player.isOnline()) {
                player.sendMessage(Chat.color(message));
            }
        }
    }

    public Player getHost() { return HOST; }
}
