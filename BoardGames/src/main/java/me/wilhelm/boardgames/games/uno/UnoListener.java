package me.wilhelm.boardgames.games.uno;

import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UnoListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        for (int i : BoardGames.getInstance().getGameList().keySet()){
            if (BoardGames.getInstance().getGame(i) instanceof Uno){
                for (((Uno) BoardGames.getInstance().getGame(i)).getInactiveIDs())
            }
        }
    }
}
