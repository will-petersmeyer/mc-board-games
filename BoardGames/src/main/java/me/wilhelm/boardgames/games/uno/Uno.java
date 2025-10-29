package me.wilhelm.boardgames.games.uno;

import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.entity.Player;

public class Uno implements Game {
    private boolean running = false;
    private final Player[] PLAYERS;
    private int[] inactiveIDs;
    private final int GAMEID;

    public Uno(Player[] playerList) {
        this.PLAYERS = playerList;
        GAMEID = BoardGames.getInstance().addGame(this);
        inactiveIDs = new int[playerList.length];
    }

    @Override
    public boolean initialize() {
        if (running) {
            BoardGames.log("[Uno] Game has already been initialized!");
            return false;
        }

        if (PLAYERS.length < 3 || PLAYERS.length > 8) {
            BoardGames.log("[Uno] Player list size is invalid!");
            return false;
        }

        if (PLAYERS.length - inactiveIDs.length > 2) {
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
    public Player getPlayer(int index) {
        try {
            return PLAYERS[index+1];
        } catch (ArrayIndexOutOfBoundsException e) {
            BoardGames.logError("Player index out of bounds!");
        }
        return null;
    }

    @Override
    public int getGameID() {
        return GAMEID;
    }

    public int[] getInactiveIDs() {return inactiveIDs;}
}
