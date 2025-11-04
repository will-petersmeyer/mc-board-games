package me.wilhelm.boardgames.commands.bgames;

import me.wilhelm.api.minecraft.Chat;
import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.uno.Uno;
import me.wilhelm.boardgames.guis.MainMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BGamesCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /*
        * /BoardGames
        *   - Do nothing, invalid usage.
        *
        * /BoardGames help
        *   - Goes without saying
        *
        * /BoardGames info
        *   - Produce general info about the plugin & its current activity
        *   - Plugin version, author, current # of active games, & current # of active players
        *
        * /BoardGames reload
        *   - Reloads configs and terminates all games
        *
        * /BoardGames menu
        *   - Opens a menu for managing current games on the server.
        *   - See boardgames/guis/MainMenu.java
        *
        * /BoardGames create {game-name} {player-1} {player-2} ...
        *   - Opens a menu for creating a game with the desired players
        *
        * */

        // Ignore console use of command
        if (!(sender instanceof Player))
            return false;

        // Ignore commands which are not boardgames or its aliases
        if (!cmd.getName().equalsIgnoreCase("boardgames")) return false;

        // Deny usage without permission
        if (!sender.hasPermission("boardgames.boardgames"))
        {Chat.sendErrorMessage((Player)sender, "You do not have permission to use this command."); return false;}

        // Invalid Usage
        if (args.length == 0)
        {BGamesHelper.sendUseHelpMessage((Player)sender); return true;}

        // Cases for the first argument
        if (args.length == 1)
        {
            switch (args[0].toLowerCase()) {
                case "help" -> BGamesHelper.sendHelpMessage((Player) sender);
                case "info" -> BGamesHelper.sendInfoMessage((Player) sender);
                case "reload" -> {
                    BoardGames.getInstance().reload();
                    Chat.sendInfoMessage((Player) sender, "All configs have been reloaded and all games have been terminated.");
                }
                case "menu" -> MainMenu.MainMenu((Player) sender);
                default -> BGamesHelper.sendUseHelpMessage((Player) sender);
            }
            return true;
        }

        // The only valid argument
        if (!args[0].equalsIgnoreCase("create") || args.length < 3)
        {BGamesHelper.sendUseHelpMessage((Player)sender); return true;}

        BGamesHelper.createGame(sender, args[1], Arrays.copyOfRange(args, 2, args.length)); return true;
    }

    static class BGamesHelper {

        public static void sendUseHelpMessage(Player player) {
            player.sendMessage(Chat.color("&f&lINCORRECT USAGE &r&fPlease use /bg help for information on how to use this command."));
        }

        public static void sendHelpMessage(Player player) {
            player.sendMessage(Chat.color(
                    ""));
        }

        public static void sendInfoMessage(Player player) {
            player.sendMessage(Chat.color("&ko") + Chat.color("&3 Wilhelm's Board Games ") + Chat.color("&r&f&ko"));
            player.sendMessage(Chat.color("&7---"));

            player.sendMessage(Chat.color("&fActive Games: &7") + BoardGames.getInstance().getGameIDs().size());
            player.sendMessage(Chat.color("&fUse &n/game help&r for information on how to use this command."));

            player.sendMessage(Chat.color("&7---"));
        }

        public static void createGame(CommandSender sender, String game, String[] players) {
            Player[] playerArr = new Player[players.length];

            for (int i = 0; i < players.length; i++) {
                Player player = Bukkit.getPlayer(players[i]);
                if (player == null) {
                    Chat.sendErrorMessage((Player)sender, "Failed to create game &o&n" + game + "&r&c. Player &o&n" + players[i] + " &r&ccould not be found.");
                    return;
                }

                playerArr[i] = Bukkit.getPlayer(players[i]);
            }

            switch (game.toLowerCase()) {
                case "uno":
                    try {
                        Uno newGame = new Uno(playerArr, (Player)sender);
                        sender.sendMessage(Chat.color("&f[&6&l!&f] &fSuccessfully created game &o&n" + game + "&r&f. Please use &o&n/uno&r&f to control this game. &8&o[GAME_ID " + newGame.getGameID() + "]"));
                    } catch (Exception e) {
                        sender.sendMessage(Chat.color("&7[&4&l!&7] &cFailed to create game. Please see the console for more information."));
                        e.printStackTrace();
                    }
                    return;
                default:
                    sender.sendMessage(Chat.color("&7[&4&l!&7] &cFailed to create game &o&n" + game + "&r&c. This is not a valid game type!"));
            }

        }

    }

}
