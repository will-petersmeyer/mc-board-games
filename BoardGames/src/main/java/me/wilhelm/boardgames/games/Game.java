package me.wilhelm.boardgames.games;

import org.bukkit.entity.Player;

public interface Game {

    /**
     * Used to begin a specific <code>Game</code> object.
     * Any implementation should add the <code>Game</code> to the Active Games map.
     *
     * @return True: <code>Game</code> object has successfully been initialized. <br>False: <code>Game</code> object failed to be initialized--check error log.
     */
    boolean initialize();

    /**
     * Used to check if the <code>Game</code> object is currently active.
     *
     * @return the value of <code>Game</code> object's 'running' variable.
     * */
    boolean isRunning();

    /**
     * Used to pause a <code>Game</code> object if it is currently running.
     *
     * @return True: <code>Game</code> object has successfully been paused. <br>False: <code>Game</code> failed to be paused.
     * */
    boolean pause();

    /**
     * Used to resume a <code>Game</code> object if it is not currently running.
     *
     * @return True: <code>Game</code> object has successfully been resumed. <br>False: <code>Game</code> failed to be resumed.
     * */
    boolean resume();

    /**
     * Used to fully stop a <code>Game</code> object if it is currently running.
     *
     * @return True: <code>Game</code> object has successfully been stopped and terminated. <br>False: <code>Game</code> failed to be stopped.
     * */
    boolean stop();

    /**
     * Used to fully stop a <code>Game</code> object if it is currently running.
     *
     * @param winner If specified, this player will be declared the winner of the game.
     * @return True: <code>Game</code> object has successfully been stopped and terminated. <br>False: <code>Game</code> failed to be stopped.
     * */
    boolean stop(Player winner);

    /**
     * Used to fetch a player by their player ID--or their index +1 within the <code>playerList</code> array.
     *
     * @param index the index +1 of the player within the array.
     * @return the player specified by the given index.
     */
    Player getPlayer(int index);

    /**
     * Used to fetch the unique game ID of the object.
     *
     * @return the value of the <code>GAMEID</code> variable.
     */
    int getGameID();

}
