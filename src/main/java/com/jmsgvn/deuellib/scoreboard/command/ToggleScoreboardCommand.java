package com.jmsgvn.deuellib.scoreboard.command;

import com.jmsgvn.deuellib.scoreboard.ScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ToggleScoreboardCommand implements CommandExecutor {
    @Override public boolean onCommand(CommandSender commandSender, Command command, String s,
        String[] strings) {
        if (!commandSender.hasPermission("deuellib.admin")) {
            commandSender.sendMessage(
                ChatColor.RED + "You do not have permission to run this command.");
            return true;
        }

        ScoreboardManager.setInitiated(!ScoreboardManager.isInitiated());
        commandSender.sendMessage(
            ChatColor.YELLOW + "The scoreboard has been " + (ScoreboardManager.isInitiated() ?
                ChatColor.GREEN + "enabled" :
                ChatColor.RED + "disabled") + ChatColor.YELLOW + ".");

        return true;
    }
}
