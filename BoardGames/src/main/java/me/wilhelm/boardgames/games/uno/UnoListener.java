package me.wilhelm.boardgames.games.uno;

import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UnoListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        for (int i : BoardGames.getInstance().getGameIDs()){
            Game game = BoardGames.getInstance().getGame(i);

            if (!(game instanceof Uno))
                continue;

            if(!game.hasPlayer(e.getPlayer()))
                return;

            game.setPlayerActive(e.getPlayer());
            game.announce("&7[&6&l!&7] &f&n" + e.getPlayer().getName() + "&f has reconnected! &8&o[UNO]");

        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        for (int i : BoardGames.getInstance().getGameIDs()){
            Game game = BoardGames.getInstance().getGame(i);

            if (!(game instanceof Uno))
                continue;

            if (game.hasPlayer(e.getPlayer()))
                continue;

            game.setPlayerInactive(e.getPlayer());
            game.announce("&7[&4&l!&7] &c&n" + e.getPlayer().getName() + "&c has disconnected! &8&o[UNO]");
        }
    }
}
