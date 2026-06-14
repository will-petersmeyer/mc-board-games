package me.wilhelm.boardgames.games.uno;

import me.wilhelm.api.algorithms.HashMapHelper;
import me.wilhelm.api.minecraft.Chat;
import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import me.wilhelm.boardgames.games.InvalidPlayerCountException;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Uno implements Game {
    // Reference labels for consistency
    public final static String label = "UNO";
    public final static int maxPlayers = 8;
    public final static int minPlayers = 1;

    // Game information
    private final int GAMEID;
    private final Player HOST;

    // References containing information about the players
    private final HashMap<Player, Boolean> playersMap = new HashMap<>();
    private final Player[] players;

    // Basic data
    private boolean running = false;
    private int turn = 0;

    // Working game Data


    /**
     * <h2>Uno</h2>
     *
     * <P>
     * UNO follows the path of a loop of 2 phases-- execution and switch--, with 2 other phases-- initialization and pause-- at certain times.
     * They execute in the following specified order:
     * <br><code>Initialization -> Execution -> Switch -> Execution</code><br>
     * This should continue until the game is over.
     * Unless in the case that the game is paused.
     * In which case the following order will occur.
     * <br><code>Execution -> Pause -> Switch -> Execution</code><br>
     * If the game is executed at any time, it will wait to pause until the execution phase has finished, so that the game may be resumed during the Switch phase.
     * </P>
     * */

    public Uno(Player[] playerList, Player host) throws InvalidPlayerCountException {
        if (playerList.length < minPlayers || playerList.length > maxPlayers)
            throw new InvalidPlayerCountException(label + " instances must have a player count between " + minPlayers + " and " + maxPlayers);

        GAMEID = BoardGames.getInstance().addGame(this);
        players = playerList;
        HOST = host;

        for (Player player : playerList) {
            if (player.isOnline())
                playersMap.put(player, true);
            else
                playersMap.put(player, false);
        }

        announce(Chat.getInfoMessage("You have been added to a game of UNO. Please wait for the host to start it. &8&o[UNO]"));
    }

    /**
     * <h3>Initialization</h3>
     * <p>
     * Once an object is created it must be initialized, or started.
     * During this phase, multiple checks will be preformed-- and in the case of their success, every player will be given their 'cards' and the game will move to the Execution phase.
     * </p>
     */

    @Override
    public boolean initialize() {
        if (running) {
            BoardGames.log("[Uno] Game has already been initialized!");
            return false;
        }

        if (playersMap.size() < minPlayers || playersMap.size() > maxPlayers) {
            BoardGames.log("[Uno] Player list size is invalid!");
            return false;
        }

        if (playersMap.size() - HashMapHelper.countValues(playersMap, false) <= playersMap.size() / 2) { // Stops a game from being initialized if <= half the players are online.
            BoardGames.log("[Uno] Too many players are offline!");
            return false;
        }

        running = true;

        executeTurn();
        return true;
    }

    /**
     * <h3>Execution</h3>
     * <p>
     * This phase is the real content of the game.
     * Any action preformed by a player will be within this phase.
     * This phase, once a player's turn has concluded, will move to the Switch phase.
     * </p>
     */

    public void executeTurn() {



        switchTurn();
    }

    /**
     * <h3>Switch</h3>
     * <p>
     * This phase is by far the most simple.
     * It exists for the sole reason of switching the turn so that the next player in the sequence may execute it.
     * It also serves as the beginning point for a game after it has been resumed.
     * </p>
     */

    public void switchTurn() {
        if (!running)
            return;

        if (turn == playersMap.size()-1)
            turn = 0;
        else
            turn++;

        if (!getCurrentPlayer().isOnline()) {
            switchTurn();
            return;
        }

        executeTurn();
    }

    @Override
    public boolean pause() {
        running = false;

        return true;
    }

    @Override
    public boolean resume() {
        running = true;

        switchTurn();
        return true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean stop() {

        announce("&7[&6&l!&7] &fYour game with id &f&n" + this.getGameID() + " &r&fhas been forcefully stopped. &8&o[UNO]");
        BoardGames.getInstance().removeGame(this.getGameID());
        return true;
    }

    @Override
    public boolean stop(Player winner) {
        return true;
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

    public int getOfflinePlayerCount() {
        return HashMapHelper.countValues(playersMap, false);
    }

    public Player getHost() { return HOST; }
}
