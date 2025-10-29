package me.wilhelm.boardgames.games.uno;

import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.entity.Player;

public class Uno implements Game {
    private boolean running = false;
    private Player[] playerList;
    private int[] inactiveIDs;
    private final int GAMEID;

    public Uno(Player[] playerList) {
        this.playerList = playerList;
        GAMEID = BoardGames.getInstance().addGame(this);
        inactiveIDs = new int[playerList.length];
    }

    @Override
    public boolean initialize() {
        return false;
    }

    @Override
    public boolean isRunning() {
        return false;
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
            return playerList[index+1];
        } catch (ArrayIndexOutOfBoundsException e) {
            BoardGames.logError("Player index out of bounds!");
        }
        return null;
    }

    @Override
    public int getGameID() {
        return GAMEID;
    }
}
