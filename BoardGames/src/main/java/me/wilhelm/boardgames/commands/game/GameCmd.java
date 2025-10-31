package me.wilhelm.boardgames.commands.game;

import me.wilhelm.api.minecraft.Chat;
import me.wilhelm.boardgames.BoardGames;
import me.wilhelm.boardgames.games.uno.Uno;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class GameCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("game"))
            return false;

        if (!commandSender.hasPermission("boardgames.game"))
            return false;

        if (args.length == 0)
            info(commandSender);

        if (args.length >= 2) {
            switch (args[0]) {
                case "start":
                    startGame(commandSender, args[2], Arrays.copyOfRange(args, 3, args.length));
                    return true;
                default:
                    commandSender.sendMessage(Chat.color("&7[&4&l!&7] &cInvalid arguments. &fUse &n/game help &rfor information on how to use this command."));
                    return true;

            }
        }


        return false;
    }

    private void info(CommandSender commandSender) {
        commandSender.sendMessage(Chat.color("&ko") + Chat.color("&3 Wilhelm's Board Games ") + Chat.color("&ko"));
        commandSender.sendMessage(Chat.color("&7---"));

        commandSender.sendMessage(Chat.color("&fActive Games: &7") + BoardGames.getInstance().getGameIDs().size());
        commandSender.sendMessage(Chat.color("&fUse &n/game help &rfor information on how to use this command."));

        commandSender.sendMessage(Chat.color("&7---"));
    }

    private void startGame(CommandSender sender, String game, String[] players) {
        Player[] playerArr = new Player[players.length];

        for (int i = 0; i < players.length; i++) {
            Player player = Bukkit.getPlayer(players[i]);
            if (player == null) {
                sender.sendMessage(Chat.color("&7[&4&l!&7] &cFailed to create game &o&n" + game + "&r&c. Player &o&n" + players[i] + " &r&ccould not be found."));
                return;
            }

            playerArr[i] = Bukkit.getPlayer(players[i]);
        }

        switch (game.toLowerCase()) {
            case "uno":
                Uno newGame = new Uno(playerArr, (Player)sender);
                sender.sendMessage(Chat.color("&f[&6&l!&f] &fSuccessfully created game &o&n" + game + "&r&f. Please use &o&n/uno &r&fto control this game. &8&o[GAME_ID " + newGame.getGameID() + "]"));
                return;
            default:
                sender.sendMessage(Chat.color("&7[&4&l!&7] &cFailed to create game &o&n" + game + "&r&c. This is not a valid game type!"));
        }

    }
}
